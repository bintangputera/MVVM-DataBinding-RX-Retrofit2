package com.el.jetpack.webservice.todo

import com.el.jetpack.data.entity.todo.Todo
import io.reactivex.Observable
import retrofit2.http.GET

interface TodoService {

    @GET("todos")
    fun getTodos(): Observable<List<Todo>>

}