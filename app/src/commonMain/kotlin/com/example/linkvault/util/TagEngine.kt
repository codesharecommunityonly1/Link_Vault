package com.example.linkvault.util

object TagEngine {
    private val keywordToTagMap = mapOf(
        "youtube.com" to "Video",
        "youtu.be" to "Video",
        "github.com" to "Dev",
        "gitlab.com" to "Dev",
        "stackoverflow.com" to "Help",
        "stackexchange.com" to "Help",
        "reddit.com" to "Social",
        "twitter.com" to "Social",
        "x.com" to "Social",
        "medium.com" to "Article",
        "wikipedia.org" to "Reference"
    )

    fun suggestTag(url: String, title: String): String? {
        val lowerUrl = url.lowercase()
        val lowerTitle = title.lowercase()

        // Check URL first
        for ((keyword, tag) in keywordToTagMap) {
            if (lowerUrl.contains(keyword)) return tag
        }

        // Check Title
        for ((keyword, tag) in keywordToTagMap) {
            val simpleKeyword = keyword.split(".")[0]
            if (lowerTitle.contains(simpleKeyword)) return tag
        }

        return null
    }
}
