package com.alvarosct.demo.reddit.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.alvarosct.demo.reddit.data.source.local.adapters.PostEntityAdapter
import com.alvarosct.demo.reddit.data.source.local.dao.PostDao
import com.alvarosct.demo.reddit.models.PostModel


interface PostLocalSource {

    suspend fun savePost(model: PostModel)
    suspend fun savePosts(modelList: List<PostModel>)
    fun observePosts(): LiveData<List<PostModel>>
    suspend fun getPosts(): List<PostModel>

}

class PostLocalSourceImpl(
    private val postDao: PostDao
) : PostLocalSource {

    override suspend fun savePost(model: PostModel) {
        postDao.insert(PostEntityAdapter.fromModel(model))
    }

    override suspend fun savePosts(modelList: List<PostModel>) {
        postDao.insertAll(modelList.map(PostEntityAdapter::fromModel))
    }

    override fun observePosts(): LiveData<List<PostModel>> {
        return postDao.observePosts().map { list ->
            list.map(PostEntityAdapter::toModel)
        }
    }

    override suspend fun getPosts(): List<PostModel> {
        return postDao.getPosts().map(PostEntityAdapter::toModel)
    }
}