package com.alvarosct.demo.reddit.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    val id: String,
    val createdUtc: Long = 0,
    val subredditName: String = "",
    val title: String = "",
    val upVotes: Long = 0,
    val comments: Long = 0,
    val thumbnailUrl: String = "",
    val sourceUrl: String? = null
) : Parcelable {

    val isImage: Boolean
        get() {
            return sourceUrl?.takeIf { it.isNotEmpty() }?.let {
                val isJpg = it.endsWith("jpg", ignoreCase = true)
                val isJpeg = it.endsWith("jpeg", ignoreCase = true)
                val isGif = it.endsWith("gif", ignoreCase = true)
                val isPng = it.endsWith("png", ignoreCase = true)
                isJpg || isJpeg || isGif || isPng
            } ?: false
        }

    val upVoteString: String
        get() = when {
            upVotes >= 1000000 -> String.format("%.1f M", upVotes / 1000000.0)
            upVotes >= 1000 -> String.format("%.1f K", upVotes / 1000.0)
            else -> upVotes.toString()
        }

    val commentString: String
        get() = when {
            comments >= 1000000 -> String.format("%.1f M", comments / 1000000.0)
            comments >= 1000 -> String.format("%.1f K", comments / 1000.0)
            else -> comments.toString()
        }
}