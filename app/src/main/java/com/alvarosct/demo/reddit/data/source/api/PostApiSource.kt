package com.alvarosct.demo.reddit.data.source.api

import com.alvarosct.demo.reddit.data.source.api.adapters.RedditPostAdapter
import com.alvarosct.demo.reddit.models.PostModel


interface PostApiSource {

    suspend fun getTopPosts(after: String?, limit: Int): Pair<String, List<PostModel>>

}

class PostApiSourceImpl(
    private val redditApiService: RedditApiService
) : PostApiSource {

    override suspend fun getTopPosts(after: String?, limit: Int): Pair<String, List<PostModel>> {
        val response = redditApiService.getTopPosts(after = after, limit = limit)
        val data = response.body()?.data
        return data?.after.orEmpty() to data?.children?.map { RedditPostAdapter.toModel(it.data) }.orEmpty()
    }

}