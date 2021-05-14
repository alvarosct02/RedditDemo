package com.alvarosct.demo.reddit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alvarosct.demo.reddit.data.source.api.PostApiSource
import com.alvarosct.demo.reddit.data.source.local.PostLocalSource
import com.alvarosct.demo.reddit.models.PostModel
import timber.log.Timber

interface PostRepository {

    fun observePosts(): LiveData<List<PostModel>>

    suspend fun fetchPosts(after: String? = null, limit: Int, isRefresh: Boolean = false): String?
}

class PostRepositoryImpl(
    private val apiSource: PostApiSource,
    private val localSource: PostLocalSource
) : PostRepository {

    private val _postsLiveData = MutableLiveData<List<PostModel>>()
    private val posts: MutableList<PostModel> = mutableListOf()

    override fun observePosts(): LiveData<List<PostModel>> = _postsLiveData

    override suspend fun fetchPosts(after: String?, limit: Int, isRefresh: Boolean): String? {
//        TODO: Handle the api responses cases properly
        return try {
            val (newAfter, newPosts) = apiSource.getTopPosts(after = after, limit = limit)
            if (isRefresh) posts.clear()
            posts.addAll(newPosts)
            _postsLiveData.postValue(posts.toList())
            localSource.savePosts(newPosts)
            newAfter
        } catch (e: Exception) {
            Timber.e("exception: ${e.message}")
            posts.clear()
            _postsLiveData.postValue(localSource.getPosts())
            null
        }
    }
}