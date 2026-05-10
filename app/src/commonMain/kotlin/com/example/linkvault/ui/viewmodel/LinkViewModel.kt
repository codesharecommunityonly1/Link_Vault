package com.example.linkvault.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkvault.data.AppDatabase
import com.example.linkvault.data.LinkEntity
import com.example.linkvault.data.NoteEntity
import com.example.linkvault.util.LinkScraper
import com.example.linkvault.util.CryptoManager
import com.example.linkvault.util.DesktopPrefs
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class LinkViewModel(private val database: AppDatabase) : ViewModel() {

    private val linkDao = database.linkDao()
    private val noteDao = database.noteDao()
    private val cryptoManager = CryptoManager()
    private val httpClient = OkHttpClient()

    // --- Link State ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isVaultUnlocked = MutableStateFlow(false)
    val isVaultUnlocked = _isVaultUnlocked.asStateFlow()

    private val _isVaultSelected = MutableStateFlow(false)
    val isVaultSelected = _isVaultSelected.asStateFlow()

    private val _selectedLinkIds = MutableStateFlow<Set<Long>>(emptySet())
    val selectedLinkIds = _selectedLinkIds.asStateFlow()

    private val _selectedCollection = MutableStateFlow<String?>(null)
    val selectedCollection = _selectedCollection.asStateFlow()

    val collections: StateFlow<List<String>> = linkDao.getAllCollections()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val links: StateFlow<List<LinkEntity>> = combine(
        _searchQuery,
        _selectedCollection,
        _isVaultSelected,
        _isVaultUnlocked
    ) { query, collection, vaultSelected, vaultUnlocked ->
        DataSnapshot(query, collection, vaultSelected, vaultUnlocked)
    }
        .debounce(300)
        .flatMapLatest { snapshot ->
            val baseFlow = when {
                snapshot.query.isNotBlank() -> linkDao.searchLinks("*${snapshot.query}*")
                snapshot.collection != null -> linkDao.getLinksByCollection(snapshot.collection)
                else -> linkDao.getAllLinks()
            }

            baseFlow.map { list ->
                if (snapshot.vaultSelected) {
                    if (snapshot.vaultUnlocked) {
                        list.filter { it.isPrivate }.map { decryptLink(it) }
                    } else {
                        emptyList()
                    }
                } else {
                    list.filter { !it.isPrivate }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Note State ---
    private val _noteSearchQuery = MutableStateFlow("")
    val noteSearchQuery = _noteSearchQuery.asStateFlow()

    val notes: StateFlow<List<NoteEntity>> = _noteSearchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) noteDao.getAllNotes() else noteDao.searchNotes(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Link Actions ---
    fun unlockVault() { _isVaultUnlocked.value = true }
    fun lockVault() { _isVaultUnlocked.value = false }

    fun setVaultSelected(selected: Boolean) {
        _isVaultSelected.value = selected
        if (selected) {
            _selectedCollection.value = null
            _searchQuery.value = ""
        }
    }

    fun toggleSelection(linkId: Long) {
        val currentSelection = _selectedLinkIds.value.toMutableSet()
        if (currentSelection.contains(linkId)) currentSelection.remove(linkId) else currentSelection.add(linkId)
        _selectedLinkIds.value = currentSelection
    }

    fun clearSelection() { _selectedLinkIds.value = emptySet() }

    fun deleteLink(link: LinkEntity) {
        viewModelScope.launch { linkDao.deleteLink(link) }
    }

    fun togglePrivacy(link: LinkEntity) {
        viewModelScope.launch {
            val updatedLink = if (link.isPrivate) {
                link.copy(
                    title = cryptoManager.decrypt(link.title),
                    url = cryptoManager.decrypt(link.url),
                    isPrivate = false
                )
            } else {
                link.copy(
                    title = cryptoManager.encrypt(link.title),
                    url = cryptoManager.encrypt(link.url),
                    isPrivate = true
                )
            }
            linkDao.insertLink(updatedLink)
        }
    }

    fun saveLink(url: String, title: String? = null, collection: String? = null, isPrivate: Boolean = false) {
        viewModelScope.launch {
            val metadata = LinkScraper.scrape(url)
            val suggestedTag = com.example.linkvault.util.TagEngine.suggestTag(url, metadata.title)
            
            val finalTitle = if (isPrivate) cryptoManager.encrypt(title ?: metadata.title) else (title ?: metadata.title)
            val finalUrl = if (isPrivate) cryptoManager.encrypt(url) else url

            linkDao.insertLink(
                LinkEntity(
                    url = finalUrl,
                    title = finalTitle,
                    description = metadata.description,
                    imageUrl = metadata.imageUrl,
                    collectionName = collection ?: suggestedTag,
                    content = metadata.content,
                    readingTime = metadata.readingTime,
                    isVideo = metadata.isVideo,
                    isPrivate = isPrivate
                )
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _selectedCollection.value = null
        _isVaultSelected.value = false
    }

    fun selectCollection(collection: String?) {
        _selectedCollection.value = collection
        _isVaultSelected.value = false
    }

    private fun decryptLink(link: LinkEntity): LinkEntity {
        return try {
            link.copy(
                title = cryptoManager.decrypt(link.title),
                url = cryptoManager.decrypt(link.url)
            )
        } catch (e: Exception) { link }
    }

    // --- Note Actions ---
    fun saveNote(title: String, content: String, isPinned: Boolean = false) {
        viewModelScope.launch {
            noteDao.insertNote(NoteEntity(title = title, content = content, isPinned = isPinned))
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch { noteDao.deleteNote(note) }
    }

    fun toggleNotePin(note: NoteEntity) {
        viewModelScope.launch {
            noteDao.insertNote(note.copy(isPinned = !note.isPinned))
        }
    }

    fun onNoteSearchChange(query: String) {
        _noteSearchQuery.value = query
    }
    
    // --- Feedback ---
    fun submitFeedback(name: String, email: String, message: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val formBody = FormBody.Builder()
                        .add("name", name)
                        .add("email", email)
                        .add("message", message)
                        .build()

                    val request = Request.Builder()
                        .url("https://formspree.io/f/xrejavkj")
                        .post(formBody)
                        .build()

                    httpClient.newCall(request).execute().use { response ->
                        response.isSuccessful
                    }
                } catch (e: Exception) {
                    false
                }
            }
            if (result) {
                onResult(true, "Feedback sent! Thank you.")
            } else {
                onResult(false, "Failed to send feedback. Please check your connection.")
            }
        }
    }

    // --- CSV Export (Recovery Folder) ---
    fun exportBackup() {
        viewModelScope.launch {
            val recoveryPath = DesktopPrefs.getRecoveryPath() ?: return@launch
            val targetDir = File(recoveryPath)
            if (!targetDir.exists()) targetDir.mkdirs()

            val links = linkDao.getAllLinksOnce()
            val file = File(targetDir, "link_vault_recovery_${System.currentTimeMillis()}.csv")
            
            val csv = StringBuilder("ID,Title,URL,Collection,Timestamp\n")
            links.forEach { link ->
                csv.append("${link.id},\"${link.title}\",\"${link.url}\",\"${link.collectionName ?: ""}\",${link.timestamp}\n")
            }
            
            file.writeText(csv.toString())
            println("--- RECOVERY BACKUP SAVED TO: ${file.absolutePath} ---")
        }
    }
}

data class DataSnapshot(
    val query: String,
    val collection: String?,
    val vaultSelected: Boolean,
    val vaultUnlocked: Boolean
)
