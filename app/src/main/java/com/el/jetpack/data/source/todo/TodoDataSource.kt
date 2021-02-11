package com.el.jetpack.data.source.todo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.el.jetpack.data.entity.todo.Todo
import com.el.jetpack.utils.NetworkState
import com.el.jetpack.utils.NetworkState.Companion.ERROR
import com.el.jetpack.utils.NetworkState.Companion.LOADED
import com.el.jetpack.utils.NetworkState.Companion.LOADING
import com.el.jetpack.webservice.RetrofitApp.Companion.FIRST_PAGE
import com.el.jetpack.webservice.RetrofitApp.Companion.ITEM_PER_PAGE
import com.el.jetpack.webservice.todo.TodoService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class TodoDataSource(
    private val apiService: TodoService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Todo>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Todo>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTodos().subscribeOn(Schedulers.io()).subscribe(
                {
                    if (it.size < ITEM_PER_PAGE) {
                        callback.onResult(it, null, null)
                        Timber.d("Check Data : $it")
                        networkState.postValue(LOADED)
                    } else {
                        callback.onResult(it, null, FIRST_PAGE + 1)
                    }
                }, {
                    Timber.e("Error : $it")
                    networkState.postValue(ERROR)
                }
            )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Todo>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Todo>) {
        networkState.postValue(LOADING)
        compositeDisposable.add(
            apiService.getTodos().subscribeOn(Schedulers.io()).subscribe(
                {
                    if (it.size < ITEM_PER_PAGE) {
                        callback.onResult(it, null)
                        Timber.d("Chech data : $it")
                        networkState.postValue(LOADED)
                    } else {
                        val emptyList = Collections.emptyList<Todo>()
                        callback.onResult(emptyList, params.key +1)
                        networkState.postValue(LOADED)
                    }
                }, {
                    Timber.e("Error : $it")
                    networkState.postValue(ERROR)
                }
            )
        )
    }

}