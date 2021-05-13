package com.alvarosct.demo.reddit.data.source.local.entities;


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey val id: String,
    val isVideo: Boolean,
    val createdUtc: String,
    val title: String,
    val subredditName: String,
    val upVotes: Long,
    val thumbnail: String,
)