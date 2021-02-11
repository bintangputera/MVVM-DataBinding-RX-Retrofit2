package com.el.jetpack.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.el.jetpack.data.entity.todo.Todo
import com.el.jetpack.data.source.todo.TodoDataSource
import com.el.jetpack.data.source.todo.TodoDataSourceFactory
import com.el.jetpack.utils.NetworkState
import com.el.jetpack.webservice.RetrofitApp
import com.el.jetpack.webservice.RetrofitApp.Companion.ITEM_PER_PAGE
import io.reactivex.disposables.CompositeDisposable

class TodosRepository {
    private val apiService = RetrofitApp.getTodoService()
    private lateinit var todoDataSourceFactory: TodoDataSourceFactory

    companion object {
        @Volatile
        private var instance: TodosRepository? = null
        fun getInstance(): TodosRepository =
            instance ?: synchronized(this){
                instance ?: TodosRepository()
            }
        }

    fun requestListDataTodo(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Todo>> {
        lateinit var resultDataTodo : LiveData<PagedList<Todo>>

        todoDataSourceFactory = TodoDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ITEM_PER_PAGE)
            .build()

        resultDataTodo = LivePagedListBuilder(todoDataSourceFactory, config).build()
        return resultDataTodo
    }

    fun getNetworkState() : LiveData<NetworkState>{
        return Transformations.switchMap(
            todoDataSourceFactory.todoLiveDataSource,
            TodoDataSource::networkState
        )
    }

    }

