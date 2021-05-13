package com.alvarosct.demo.reddit.data.source.api

import com.alvarosct.demo.reddit.data.source.api.responses.TopRedditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApiService {

    @GET("top.json")
    suspend fun getTopPosts(
        @Query("after") after: String?,
        @Query("limit") limit: Int,
    ): Response<TopRedditResponse>

}