package com.example.linkvault.util

expect object PlatformUtil {
    fun openUrl(url: String)
    fun pickFolder(): String?
}
