package com.el.jetpack.webservice.post

import com.el.jetpack.data.entity.posts.Posts
import io.reactivex.Observable
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    fun getPosts(): Observable<List<Posts>>

}