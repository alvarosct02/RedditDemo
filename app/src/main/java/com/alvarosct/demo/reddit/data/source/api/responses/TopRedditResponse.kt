package com.alvarosct.demo.reddit.data.source.api.responses

import com.alvarosct.demo.reddit.data.source.api.models.RedditPost


typealias TopRedditResponse = DataWrap<Listing<DataWrap<RedditPost>>>

data class DataWrap<T>(
    val kind: String,
    val data: T
)

data class Listing<T>(
    val modhash: String,
    val dist: Long,
    val children: List<T>?,
    val after: String?,
    val before: String?
)
