package com.example.linkvault.util

import java.io.File
import java.util.Properties

object DesktopPrefs {
    private val prefsFile = File(System.getProperty("user.home"), ".linkvault_prefs")
    private val props = Properties()

    init {
        if (prefsFile.exists()) {
            prefsFile.inputStream().use { props.load(it) }
        }
    }

    fun hasAgreed(): Boolean = props.getProperty("has_agreed", "false").toBoolean()

    fun setAgreed(agreed: Boolean) {
        props.setProperty("has_agreed", agreed.toString())
        save()
    }

    fun getRecoveryPath(): String? = props.getProperty("recovery_path")

    fun setRecoveryPath(path: String) {
        props.setProperty("recovery_path", path)
        save()
    }

    private fun save() {
        prefsFile.outputStream().use { props.store(it, null) }
    }
}
