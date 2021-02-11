package com.el.jetpack.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.el.jetpack.data.entity.posts.Posts
import com.el.jetpack.data.source.post.PostsDataSource
import com.el.jetpack.data.source.post.PostsDataSourceFactory
import com.el.jetpack.utils.NetworkState
import com.el.jetpack.webservice.RetrofitApp
import com.el.jetpack.webservice.RetrofitApp.Companion.ITEM_PER_PAGE
import io.reactivex.disposables.CompositeDisposable

class PostsRepository {
    private val apiService = RetrofitApp.getPostsService()
    private lateinit var postsDataSourceFactory: PostsDataSourceFactory

    companion object {
        @Volatile
        private var instance: PostsRepository? = null
        fun getInstance(): PostsRepository =
            instance ?: synchronized(this) {
                instance ?: PostsRepository()
            }
    }

    fun requestDataListPosts(compositeDisposable: CompositeDisposable): LiveData<PagedList<Posts>> {
        lateinit var resultDataPosts: LiveData<PagedList<Posts>>

        postsDataSourceFactory = PostsDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ITEM_PER_PAGE)
            .build()

        resultDataPosts = LivePagedListBuilder(postsDataSourceFactory, config).build()

        return resultDataPosts
    }

    fun getNetworkState() : LiveData<NetworkState>{
        return Transformations.switchMap(
            postsDataSourceFactory.postsLiveDataSource,
            PostsDataSource::networkState
        )
    }

}