package com.example.mocomarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PostItem(
    val aIndex: Int,
    val aIcon: Int,
    val aName: String,
    val aIntro:String,
    val aUserName: String,
    val aArea: String,
    val aPrice: Int,
    var aLike: Int,
    val aChat: Int,
    var press: Boolean
) : Parcelable
