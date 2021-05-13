package com.alvarosct.demo.reddit.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    val id: String,
    val createdUtc: String,
    val subredditName: String,
    val title: String,
    val upVotes: Long,
    val thumbnailUrl: String,
    val sourceUrl: String?
) : Parcelable {

    val isImage: Boolean get() {
        return sourceUrl?.takeIf { it.isNotEmpty() }?.let {
            val isJpg = it.endsWith("jpg", ignoreCase = true)
            val isJpeg = it.endsWith("jpeg", ignoreCase = true)
            val isGif = it.endsWith("gif", ignoreCase = true)
            val isPng = it.endsWith("png", ignoreCase = true)
            isJpg || isJpeg || isGif || isPng
        } ?: false
    }
}