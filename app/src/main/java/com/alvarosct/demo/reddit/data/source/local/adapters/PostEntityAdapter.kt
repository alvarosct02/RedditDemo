package com.alvarosct.demo.reddit.data.source.local.adapters;


import com.alvarosct.demo.reddit.data.source.local.entities.PostEntity
import com.alvarosct.demo.reddit.models.PostModel


object PostEntityAdapter {

    fun fromModel(model: PostModel) = PostEntity(
        id = model.id,
        title = model.title,
        subredditName = model.subredditName,
        thumbnail = model.thumbnailUrl,
        source = model.sourceUrl,
        upVotes = model.upVotes,
        comments = model.comments,
        createdUtc = model.createdUtc,
    )

    fun toModel(model: PostEntity) = PostModel(
        id = model.id,
        title = model.title,
        subredditName = model.subredditName,
        thumbnailUrl = model.thumbnail,
        sourceUrl = model.source,
        upVotes = model.upVotes,
        comments = model.comments,
        createdUtc = model.createdUtc,
    )
}