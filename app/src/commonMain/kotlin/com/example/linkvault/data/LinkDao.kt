package com.example.linkvault.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLink(link: LinkEntity): Long

    @Delete
    suspend fun deleteLink(link: LinkEntity)

    @Query("SELECT * FROM links ORDER BY timestamp DESC")
    fun getAllLinks(): Flow<List<LinkEntity>>

    @Query("SELECT * FROM links ORDER BY timestamp DESC")
    suspend fun getAllLinksOnce(): List<LinkEntity>

    @Query("SELECT DISTINCT collection_name FROM links WHERE collection_name IS NOT NULL")
    fun getAllCollections(): Flow<List<String>>

    @Query("SELECT * FROM links WHERE collection_name = :collectionName ORDER BY timestamp DESC")
    fun getLinksByCollection(collectionName: String): Flow<List<LinkEntity>>

    @Query("UPDATE links SET is_dead = :isDead WHERE id = :id")
    suspend fun updateIsDead(id: Long, isDead: Boolean)

    @Query("UPDATE links SET is_duplicate = :isDuplicate WHERE id = :id")
    suspend fun updateIsDuplicate(id: Long, isDuplicate: Boolean)

    @Query("""
        SELECT * FROM links 
        JOIN links_fts ON links.id = links_fts.rowid 
        WHERE links_fts MATCH :query
    """)
    fun searchLinks(query: String): Flow<List<LinkEntity>>

    @Query("DELETE FROM links WHERE id = :linkId")
    suspend fun deleteLinkById(linkId: Long)

    @Query("SELECT * FROM links WHERE id = :linkId")
    suspend fun getLinkById(linkId: Long): LinkEntity?
}
