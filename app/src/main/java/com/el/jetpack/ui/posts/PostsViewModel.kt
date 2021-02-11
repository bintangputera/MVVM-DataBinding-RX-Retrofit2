package com.el.jetpack.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.el.jetpack.data.entity.posts.Posts
import com.el.jetpack.data.repository.PostsRepository
import com.el.jetpack.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable

class PostsViewModel (
    private val postsRepository: PostsRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel(){

    fun getListDataPost(): LiveData<PagedList<Posts>>{
        return postsRepository.requestDataListPosts(compositeDisposable)
    }

    fun listIsEmpty(): Boolean {
        return getListDataPost().value?.isEmpty() ?: true
    }

    val networkState: LiveData<NetworkState> by lazy {
        postsRepository.getNetworkState()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}