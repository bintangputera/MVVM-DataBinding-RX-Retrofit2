package com.el.jetpack.data.source.post

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.el.jetpack.data.entity.posts.Posts
import com.el.jetpack.utils.NetworkState
import com.el.jetpack.webservice.RetrofitApp.Companion.FIRST_PAGE
import com.el.jetpack.webservice.RetrofitApp.Companion.ITEM_PER_PAGE
import com.el.jetpack.webservice.post.PostsService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class PostsDataSource(
    private val apiService: PostsService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Posts>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Posts>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPosts().subscribeOn(Schedulers.io()).subscribe(
                {
                    if (it.size < ITEM_PER_PAGE){
                        callback.onResult(it, null, null)
                        Timber.d("Check data : $it")
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        callback.onResult(it, null, FIRST_PAGE + 1)
                        networkState.postValue(NetworkState.LOADED)
                    }
                } , {
                    Timber.e("Error $it")
                    networkState.postValue(NetworkState.ERROR)
                }
            )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Posts>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Posts>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPosts().subscribeOn(Schedulers.io()).subscribe(
                {
                    if (it.size < ITEM_PER_PAGE){
                        callback.onResult(it, null)
                        Timber.d("Check data : $it")
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        val emptyList = Collections.emptyList<Posts>()
                        callback.onResult(emptyList, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    }
                } , {
                    Timber.d("Error $it")
                    networkState.postValue(NetworkState.ERROR)
                }
            )
        )
    }
}