package com.el.jetpack.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.el.jetpack.data.entity.posts.Posts
import com.el.jetpack.databinding.PostItemLayoutBinding

class DataPostPagedListAdapter :
    PagedListAdapter<Posts, DataPostPagedListAdapter.DataPostViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataPostPagedListAdapter.DataPostViewHolder {
        val binding = PostItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataPostViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DataPostPagedListAdapter.DataPostViewHolder,
        position: Int
    ) {
        getItem(position)?. let {
            holder.bind(it)
        }
    }

    inner class DataPostViewHolder(private val binding: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Posts) {
            with(binding) {
                binding.txTitle.text = post.title
                binding.txBody.text = post.body
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Posts> =
            object : DiffUtil.ItemCallback<Posts>() {
                override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
                    TODO("Not yet implemented")
                }

                override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
                    TODO("Not yet implemented")
                }

            }
    }

}