package com.el.jetpack.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.el.jetpack.databinding.FragmentTodoBinding
import com.el.jetpack.ui.todo.adapter.DataTodoPagedListAdapter
import com.el.jetpack.utils.NetworkState
import com.el.jetpack.utils.ViewModelFactory

class TodoFragment : Fragment() {

    private var _bindingTodoFragment: FragmentTodoBinding? = null
    private val binding get() = _bindingTodoFragment
    private lateinit var todoPagedListAdapter: DataTodoPagedListAdapter

    private val todoViewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(TodoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingTodoFragment = FragmentTodoBinding.inflate(inflater, container, false)
        return _bindingTodoFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoPagedListAdapter = DataTodoPagedListAdapter()

        binding?.shimmerViewContainer?.visibility = View.VISIBLE

        getListDataTodo()
    }

    private fun getListDataTodo() {
        todoViewModel.getListDataTodo().observe(viewLifecycleOwner, { dataTodo ->
            todoPagedListAdapter.submitList(dataTodo)
        })

        todoViewModel.networkState.observe(viewLifecycleOwner, { network ->
            if (todoViewModel.listIsEmpty() && network == NetworkState.LOADING) {
                binding?.shimmerViewContainer?.startShimmer()
            } else {
                binding?.shimmerViewContainer?.stopShimmer()
                binding?.shimmerViewContainer?.visibility = View.GONE
            }
        })
    }

}