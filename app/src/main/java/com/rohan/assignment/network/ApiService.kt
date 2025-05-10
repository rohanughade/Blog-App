package com.rohan.assignment.network

import androidx.paging.PagingSource
import com.rohan.assignment.data.entity.BlogEntity
import com.rohan.assignment.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getPosts(
        @Query("per_page")perPage: Int,
        @Query("page")page: Int
    ): List<Response>
}