package com.example.mocomarket.data

import android.util.Log
import kotlin.math.log

class DataSource {
    companion object{
        private var INSTANCE: DataSource? = null
        fun getDataSource(): DataSource {
            return  synchronized(DataSource::class) {
                val newInstance =
                    INSTANCE ?: DataSource() //띄어쓰기 조심 null처리는 ?: << 붙여써야함.
                INSTANCE = newInstance
                newInstance
            }
        }
    }
    fun getPostItemList(): MutableList<PostItem>{
        return postItemList()
    }

    fun updatePostItem(postItem: PostItem) {
        val index = postItemList().indexOfFirst { it.aIndex == postItem.aIndex }
        postItemList()[index] = postItem
    }

    fun removePostItem(position: Int) {
        postItemList().removeAt(position)
    }
}