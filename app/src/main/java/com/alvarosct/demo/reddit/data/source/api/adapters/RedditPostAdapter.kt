package com.alvarosct.demo.reddit.data.source.api.adapters;


import com.alvarosct.demo.reddit.data.source.api.models.RedditPost
import com.alvarosct.demo.reddit.models.PostModel


object RedditPostAdapter {

    fun toModel(model: RedditPost) = PostModel(
        id = model.id,
        title = model.title.orEmpty(),
        subredditName = model.subredditNamePrefixed.orEmpty(),
        thumbnailUrl = model.thumbnail.orEmpty(),
        sourceUrl = model.url,
        upVotes = model.upVotes,
        createdUtc = model.createdUtc,
    )
}