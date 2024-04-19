package com.example.mocomarket.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mocomarket.data.DataSource
import com.example.mocomarket.data.PostItem
import com.example.mocomarket.data.postItemList

class PostViewModel(val dataSource: DataSource): ViewModel() {
    private val _postItemLiveData = MutableLiveData<MutableList<PostItem>>()
    val postItemLiveData: LiveData<MutableList<PostItem>> get() = _postItemLiveData

    init {
        _postItemLiveData.value = dataSource.getPostItemList()
    }

    fun pushLike(aIndex: Int): Boolean {
        val currentList = postItemLiveData.value ?: mutableListOf()
        val index = currentList.indexOfFirst { it.aIndex == aIndex }

        return if (index != -1) {
            val post = currentList[index]

            if (post.press) {
                post.press = false
                post.aLike--
            } else {
                post.press = true
                post.aLike++
            }

            currentList[index] = post
            _postItemLiveData.value = currentList // LiveData 값을 업데이트
            dataSource.updatePostItem(post)

            true
        } else {
            false
        }
    }

    fun removePost(position: Int) {
        val currentList = postItemLiveData.value ?: mutableListOf()
        currentList.removeAt(position)
        _postItemLiveData.value = currentList // LiveData 값을 업데이트
        dataSource.removePostItem(position)
    }
}

class PostViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(dataSource = DataSource.getDataSource()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
