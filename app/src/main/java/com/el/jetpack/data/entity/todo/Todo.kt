package com.el.jetpack.data.entity.todo

data class Todo(
    var userId: Int,
    var id: Int,
    var title: String,
    var completed: Boolean
)