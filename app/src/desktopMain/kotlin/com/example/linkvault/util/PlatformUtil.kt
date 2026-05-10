package com.example.linkvault.util

import java.awt.Desktop
import java.net.URI
import javax.swing.JFileChooser

actual object PlatformUtil {
    actual fun openUrl(url: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(url))
        }
    }

    actual fun pickFolder(): String? {
        val chooser = JFileChooser()
        chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        val result = chooser.showOpenDialog(null)
        return if (result == JFileChooser.APPROVE_OPTION) {
            chooser.selectedFile.absolutePath
        } else {
            null
        }
    }
}
