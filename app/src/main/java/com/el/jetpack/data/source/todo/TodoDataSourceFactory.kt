package com.el.jetpack.data.source.todo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.el.jetpack.data.entity.todo.Todo
import com.el.jetpack.webservice.todo.TodoService
import io.reactivex.disposables.CompositeDisposable

class TodoDataSourceFactory(
    private val apiService: TodoService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Todo>() {

    val todoLiveDataSource = MutableLiveData<TodoDataSource>()

    override fun create(): DataSource<Int, Todo> {
        val todoDataSource = TodoDataSource(apiService, compositeDisposable)

        todoLiveDataSource.postValue(todoDataSource)
        return todoDataSource
    }

}