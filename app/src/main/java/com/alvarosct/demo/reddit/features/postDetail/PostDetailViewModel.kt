package com.alvarosct.demo.reddit.features.postDetail

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alvarosct.demo.reddit.R
import com.alvarosct.demo.reddit.data.repository.PostRepository
import com.alvarosct.demo.reddit.features.base.BaseViewModel
import com.alvarosct.demo.reddit.models.PostModel
import com.alvarosct.demo.reddit.utils.Event
import com.alvarosct.demo.reddit.utils.FileUtils
import com.alvarosct.demo.reddit.utils.ResourceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val repository: PostRepository,
    private val resourceManager: ResourceManager,
    private val fileUtils: FileUtils,
) : BaseViewModel() {

    private val _post = MutableLiveData<PostModel>()
    val postLiveData: LiveData<PostModel> = _post

    private val _showSaveButton = MutableLiveData(true)
    val showSaveButton: LiveData<Boolean> = _showSaveButton

    private val _saveToastEvent = MutableLiveData<Event<String>>()
    val saveToastEvent: LiveData<Event<String>> = _saveToastEvent

    fun setPost(postModel: PostModel) {
        _post.postValue(postModel)
    }

    fun saveBitmap(bitmap: Bitmap) = viewModelScope.launch(Dispatchers.IO) {
        val postId = postLiveData.value?.id.orEmpty()
        val fileName = resourceManager.getString(
            R.string.file_post_image_format,
            resourceManager.getString(R.string.app_name),
            postId
        )
        val fileSaved = fileUtils.saveBitmapToFile(bitmap, fileName)

        if (fileSaved) {
            _showSaveButton.postValue(false)
            _saveToastEvent.postValue(Event(""))
        }

    }
}