package com.el.jetpack.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.el.jetpack.data.repository.PostsRepository
import com.el.jetpack.di.Injection
import com.el.jetpack.ui.posts.PostsViewModel
import io.reactivex.disposables.CompositeDisposable

class ViewModelFactory(
    private val postsRepository: PostsRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.providePostsRepository(),
                    Injection.provideCompositeDisposable()
                )
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PostsViewModel::class.java) -> {
                PostsViewModel(postsRepository, compositeDisposable) as T
            }
            else -> throw Throwable("Unknown ViewModel Class" + modelClass.name)
        }
    }
}