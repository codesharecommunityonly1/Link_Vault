package com.example.linkvault.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URL

object LinkScraper {
    
    data class Metadata(
        val title: String,
        val description: String?,
        val imageUrl: String?,
        val url: String,
        val readingTime: Int? = null,
        val isVideo: Boolean = false,
        val content: String? = null
    )

    suspend fun scrape(url: String): Metadata = withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .timeout(10000)
                .followRedirects(true)
                .get()

            val title = doc.select("meta[property=og:title]").attr("content").takeIf { it.isNotBlank() }
                ?: doc.title().takeIf { it.isNotBlank() }
                ?: getFallbackTitle(url)

            val description = doc.select("meta[property=og:description]").attr("content").takeIf { it.isNotBlank() }
                ?: doc.select("meta[name=description]").attr("content").takeIf { it.isNotBlank() }

            val imageUrl = doc.select("meta[property=og:image]").attr("content").takeIf { it.isNotBlank() }
                ?: doc.select("meta[name=twitter:image]").attr("content").takeIf { it.isNotBlank() }
                ?: doc.select("img").firstOrNull()?.absUrl("src")?.takeIf { it.isNotBlank() }

            // Extract main content for offline reading
            val mainContent = extractMainContent(doc)

            // New: Detection and Estimation
            val isVideo = url.contains("youtube.com") || url.contains("youtu.be") || 
                         doc.select("meta[property=og:type]").attr("content").contains("video")
            
            val readingTime = if (!isVideo) {
                val bodyText = mainContent ?: doc.body()?.text() ?: ""
                val wordCount = bodyText.split("\\s+".toRegex()).size
                (wordCount / 200).coerceAtLeast(1)
            } else null

            Metadata(title, description, imageUrl, url, readingTime, isVideo, mainContent)
        } catch (e: Exception) {
            Metadata(getFallbackTitle(url), null, null, url)
        }
    }

    private fun extractMainContent(doc: org.jsoup.nodes.Document): String? {
        // Simple heuristic to find main article content
        val article = doc.select("article").firstOrNull() 
            ?: doc.select("main").firstOrNull()
            ?: doc.select("[role=main]").firstOrNull()
            ?: doc.body()

        // Clean up common noise
        article?.select("nav, footer, header, script, style, .sidebar, .ads, .comments")?.remove()
        
        return article?.text()?.takeIf { it.isNotBlank() }
    }

    private fun getFallbackTitle(url: String): String {
        return try {
            URL(url).host.removePrefix("www.")
        } catch (e: Exception) {
            url
        }
    }
}
