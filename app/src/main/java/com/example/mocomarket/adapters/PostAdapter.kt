package com.example.mocomarket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mocomarket.R
import com.example.mocomarket.data.PostItem
import com.example.mocomarket.databinding.ItemRecyclerviewBinding
import com.example.mocomarket.utils.FormatUtils
import java.text.DecimalFormat

class PostAdapter : RecyclerView.Adapter<PostAdapter.Holder>() {
    var postList = mutableListOf<PostItem>()
    fun changePostList(items: MutableList<PostItem>) {
        this.postList = items
        notifyDataSetChanged()
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View,position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it,position)
        }
        holder.itemView.setOnLongClickListener {
            itemClick?.onLongClick(it,position)
            true // false로 설정시 이후 다른 리스너나 기본동작이 올 수 있음. ex) -> 1시도 , 2시도
        }
        val currentItem = postList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class Holder(private val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostItem) {
            binding.apply {
                binding.ivPostIcon.setImageResource(post.aIcon)
                binding.tvItemTitle.text = post.aName
                binding.tvItemArea.text = post.aArea
                binding.tvItemPrice.text = "${FormatUtils.formatNumber(post.aPrice)}원"
                binding.tvLike.text = post.aLike.toString()
                binding.tvMessage.text = post.aChat.toString()
                if(post.press) {
                    binding.ivLike.setImageResource(R.drawable.icon_love_full)
                } else {
                    binding.ivLike.setImageResource(R.drawable.icon_love_empty)
                }
            }
        }
    }
}