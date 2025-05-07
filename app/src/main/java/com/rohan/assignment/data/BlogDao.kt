package com.rohan.assignment.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.rohan.assignment.data.entity.BlogEntity

@Dao
interface BlogDao {
    @Upsert
    suspend fun upsertAll(blogs: List<BlogEntity>)

    @Query("select * from blogentity")
    fun pagingSource(): PagingSource<Int, BlogEntity>

    @Query("delete from blogentity")
    suspend fun clear()
}