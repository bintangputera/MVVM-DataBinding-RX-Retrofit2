package com.el.jetpack.di

import com.el.jetpack.data.repository.PostsRepository
import com.el.jetpack.data.repository.TodosRepository
import io.reactivex.disposables.CompositeDisposable

object Injection {

    fun providePostsRepository() : PostsRepository = PostsRepository.getInstance()
    fun provideTodoRepository() : TodosRepository = TodosRepository.getInstance()

    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}