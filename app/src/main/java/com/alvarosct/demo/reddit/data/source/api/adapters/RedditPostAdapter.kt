package com.alvarosct.demo.reddit.data.source.api.adapters;


import com.alvarosct.demo.reddit.data.source.api.models.RedditPost
import com.alvarosct.demo.reddit.models.PostModel


object RedditPostAdapter {

    fun fromModel(model: PostModel) = RedditPost(
        id = model.id,
        title = model.title,
        subredditNamePrefixed = model.subredditName,
        thumbnail = model.thumbnailUrl,
        isVideo = model.isVideo,
        upVotes = model.upVotes,
        createdUtc = model.createdUtc.toLongOrNull() ?: 0,
    )

    fun toModel(model: RedditPost) = PostModel(
        id = model.id,
        title = model.title.orEmpty(),
        subredditName = model.subredditNamePrefixed.orEmpty(),
        thumbnailUrl = model.thumbnail.orEmpty(),
        isVideo = model.isVideo ?: false,
        upVotes = model.upVotes,
        createdUtc = model.createdUtc.toString(),
    )
}