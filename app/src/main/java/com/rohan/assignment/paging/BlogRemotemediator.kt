@file:OptIn(ExperimentalPagingApi::class)

package com.rohan.assignment.paging

import retrofit2.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rohan.assignment.data.BlogDatabase
import com.rohan.assignment.data.entity.BlogEntity
import com.rohan.assignment.data.entity.toBlogEntity
import com.rohan.assignment.model.Response
import com.rohan.assignment.network.ApiService
import java.io.IOException

class BlogRemotemediator(
    private val blogdb: BlogDatabase,
    private val apiService: ApiService
): RemoteMediator<Int, BlogEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BlogEntity>
    ): MediatorResult {
        return try{
            val loadkey = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem==null){
                        1
                    }else{
                        (lastItem.id/state.config.pageSize)+1
                    }
                }
            }
            val blogs = apiService.getPosts(
                perPage = state.config.pageSize,
                page = loadkey
            )

            blogdb.withTransaction {
                if (loadType == LoadType.REFRESH){
                    blogdb.dao.clear()
                }
                val blogentity = blogs.map {
                    it.toBlogEntity()
                }
                blogdb.dao.upsertAll(blogentity)
            }
            MediatorResult.Success(endOfPaginationReached = blogs.isEmpty())
        }catch (e: IOException){
            MediatorResult.Error(e)
        }catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }
}