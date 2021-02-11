package com.el.jetpack.webservice

import com.el.jetpack.webservice.post.PostsService
import com.el.jetpack.webservice.todo.TodoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApp {

    companion object {
        const val FIRST_PAGE = 1
        const val ITEM_PER_PAGE = 10
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        private val interceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        fun getPostsService(): PostsService {
            return retrofit.create(PostsService::class.java)
        }

        fun getTodoService(): TodoService {
            return retrofit.create(TodoService::class.java)
        }

    }
}