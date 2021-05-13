package com.alvarosct.demo.reddit.features.postList

import androidx.lifecycle.*
import com.alvarosct.demo.reddit.data.repository.PostRepository
import com.alvarosct.demo.reddit.models.PostModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostListViewModel(
    private val repository: PostRepository
) : ViewModel() {

    companion object {
        const val POST_PAGE_SIZE = 20
    }

    private val _postsLiveData = repository.observePosts()
    val postsLiveData: LiveData<List<PostModel>> = _postsLiveData

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _isLoadingMore = MutableLiveData(false)
    val isLoadingMore: LiveData<Boolean> = _isLoadingMore

    private var lastPost: String? = null

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)
            lastPost = repository.fetchPosts(limit = POST_PAGE_SIZE)
            _isLoading.postValue(false)
        }
    }

    fun refreshPosts() = viewModelScope.launch {
        lastPost = null
        _isRefreshing.postValue(true)
        lastPost = repository.fetchPosts(lastPost, limit = POST_PAGE_SIZE, isRefresh = true)
        delay(200)
        _isRefreshing.postValue(false)
    }

    fun requestMorePosts() = viewModelScope.launch {
        _isLoadingMore.postValue(true)
        lastPost = repository.fetchPosts(lastPost, limit = POST_PAGE_SIZE)
        _isLoadingMore.postValue(false)
    }

}