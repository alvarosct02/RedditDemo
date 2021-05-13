package com.alvarosct.demo.reddit.features.postDetail

import androidx.lifecycle.*
import com.alvarosct.demo.reddit.data.repository.PostRepository
import com.alvarosct.demo.reddit.features.base.BaseViewModel
import com.alvarosct.demo.reddit.models.PostModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val repository: PostRepository
) : BaseViewModel() {

    private val _post = MutableLiveData<PostModel>()
    val postLiveData: LiveData<PostModel> = _post

    fun setPost(postModel: PostModel) {
        _post.postValue(postModel)
    }

}