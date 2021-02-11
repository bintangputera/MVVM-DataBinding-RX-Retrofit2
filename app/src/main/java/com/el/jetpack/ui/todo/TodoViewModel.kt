package com.el.jetpack.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.el.jetpack.data.entity.todo.Todo
import com.el.jetpack.data.repository.TodosRepository
import com.el.jetpack.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable

class TodoViewModel(
    private val todosRepository: TodosRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    fun getListDataTodo(): LiveData<PagedList<Todo>> {
        return todosRepository.requestListDataTodo(compositeDisposable)
    }

    fun listIsEmpty(): Boolean {
        return getListDataTodo().value?.isEmpty() ?: true
    }

    val networkState: LiveData<NetworkState> by lazy {
        todosRepository.getNetworkState()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}