package com.alvarosct.demo.reddit.models

data class PostModel(
    val id: String,
    val isVideo: Boolean,
    val createdUtc: String,
    val subredditName: String,
    val title: String,
    val upVotes: Long,
    val thumbnailUrl: String
) {
}