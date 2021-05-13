package com.alvarosct.demo.reddit.data.source.local.adapters;


import com.alvarosct.demo.reddit.data.source.local.entities.PostEntity
import com.alvarosct.demo.reddit.models.PostModel


object PostEntityAdapter {

    fun fromModel(model: PostModel) = PostEntity(
        id = model.id,
        title = model.title,
        subredditName = model.subredditName,
        thumbnail = model.thumbnailUrl,
        isVideo = model.isVideo,
        upVotes = model.upVotes,
        createdUtc = model.createdUtc,
    )

    fun toModel(model: PostEntity) = PostModel(
        id = model.id,
        title = model.title,
        subredditName = model.subredditName,
        thumbnailUrl = model.thumbnail,
        isVideo = model.isVideo,
        upVotes = model.upVotes,
        createdUtc = model.createdUtc,
    )
}