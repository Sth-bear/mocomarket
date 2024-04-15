package com.example.mocomarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mocomarket.databinding.ItemRecyclerviewBinding
import java.text.DecimalFormat

class PostAdapter (val mItems: MutableList<PostItem>) : RecyclerView.Adapter<PostAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
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
        holder.postIcon.setImageResource(mItems[position].aIcon)
        holder.postTitle.text = mItems[position].aName
        holder.postArea.text = mItems[position].aArea
        val decimal = DecimalFormat("#,###")
        val price = mItems[position].aPrice
        holder.postPrice.text = "${decimal.format(price)}원"
        holder.postLike.text = mItems[position].aLike.toString()
        holder.postMessage.text = mItems[position].aChat.toString()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
        val postIcon = binding.ivPostIcon
        val postTitle = binding.tvItemTitle
        val postArea = binding.tvItemArea
        val postPrice = binding.tvItemPrice
        val postLike = binding.tvLike
        val postMessage = binding.tvMessage
    }
}