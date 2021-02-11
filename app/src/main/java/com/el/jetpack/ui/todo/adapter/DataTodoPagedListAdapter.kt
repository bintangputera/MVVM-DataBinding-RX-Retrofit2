package com.el.jetpack.ui.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.el.jetpack.R
import com.el.jetpack.data.entity.todo.Todo
import com.el.jetpack.databinding.TodoItemLayoutBinding

class DataTodoPagedListAdapter :
    PagedListAdapter<Todo, DataTodoPagedListAdapter.DataTodoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataTodoPagedListAdapter.DataTodoViewHolder {
        val binding = TodoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataTodoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DataTodoPagedListAdapter.DataTodoViewHolder,
        position: Int
    ) {
        getItem(position)?.let { todo ->
            holder.bind(todo)
        }
    }

    inner class DataTodoViewHolder(private val binding: TodoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(todo: Todo){
                with(binding){
                    binding.txTitle.text = todo.title
                    if (todo.completed) {
                        binding.txBody.text = binding.root.context.getString(R.string.completed)
                    } else {
                        binding.txBody.text = binding.root.context.getString(R.string.not_completed)
                    }
                }
            }
        }

    companion object {
        private val DIFF_CALLBACK : DiffUtil.ItemCallback<Todo> =
            object : DiffUtil.ItemCallback<Todo>() {
                override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                    TODO("Not yet implemented")
                }

                override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                    TODO("Not yet implemented")
                }

            }
    }

}