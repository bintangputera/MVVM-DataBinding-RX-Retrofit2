package com.el.jetpack.data.source.post

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.el.jetpack.data.entity.posts.Posts
import com.el.jetpack.webservice.post.PostsService
import io.reactivex.disposables.CompositeDisposable

class PostsDataSourceFactory(
    private val apiService: PostsService,
    private val compositeDisposable: CompositeDisposable
): DataSource.Factory<Int, Posts>(){

    val postsLiveDataSource = MutableLiveData<PostsDataSource>()

    override fun create(): DataSource<Int, Posts> {
        val postsDataSource = PostsDataSource(apiService, compositeDisposable)

        postsLiveDataSource.postValue(postsDataSource)
        return postsDataSource
    }

}