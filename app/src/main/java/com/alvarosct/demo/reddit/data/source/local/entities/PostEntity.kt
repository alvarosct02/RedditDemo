package com.alvarosct.demo.reddit.data.source.local.entities;


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey val id: String,
    val createdUtc: Long,
    val title: String,
    val subredditName: String,
    val upVotes: Long,
    val thumbnail: String,
    val source: String?,
)