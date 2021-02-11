package com.el.jetpack.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.el.jetpack.databinding.FragmentPostsBinding
import com.el.jetpack.ui.posts.adapter.DataPostPagedListAdapter
import com.el.jetpack.utils.NetworkState
import com.el.jetpack.utils.ViewModelFactory

class PostsFragment : Fragment() {

    private lateinit var dataPostPagedListAdapter: DataPostPagedListAdapter

    private val postsViewmodel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(PostsViewModel::class.java)
    }

    private var _bindingPostsFragment: FragmentPostsBinding? = null
    private val binding get() = _bindingPostsFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingPostsFragment =
            FragmentPostsBinding.inflate(inflater, container, false)
        return _bindingPostsFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataPostPagedListAdapter = DataPostPagedListAdapter()

        with(binding?.rvPosts){
            this?.adapter = dataPostPagedListAdapter
            this?.layoutManager = LinearLayoutManager(this?.context, LinearLayoutManager.VERTICAL, false)
        }

        binding?.shimmerViewContainer?.visibility = View.VISIBLE

        showData()

    }

    private fun showData() {
        postsViewmodel.getListDataPost().observe(viewLifecycleOwner, { dataPost ->
            dataPostPagedListAdapter.submitList(dataPost)
        })

        postsViewmodel.networkState.observe(viewLifecycleOwner, { network ->
            if (postsViewmodel.listIsEmpty() && network == NetworkState.LOADING){
                binding?.shimmerViewContainer?.startShimmer()
            } else {
                binding?.shimmerViewContainer?.stopShimmer()
                binding?.shimmerViewContainer?.visibility = View.GONE
            }
        })
    }

}