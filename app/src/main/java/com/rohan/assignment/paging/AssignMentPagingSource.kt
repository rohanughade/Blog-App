package com.rohan.assignment.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rohan.assignment.model.Response
import com.rohan.assignment.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class AssignMentPagingSource(private val apiService: ApiService): PagingSource<Int, Response>() {
    override fun getRefreshKey(state: PagingState<Int, Response>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Response> {
        val position = params.key?:1
        return try{
            val data = apiService.getPosts(
                page = position,
                perPage = params.loadSize
            )
            val next = if (data.size<params.loadSize)null else position+1
            LoadResult.Page(
                data = data,
                prevKey = if (position==1) null else position-1,
                nextKey = next
            )

        }catch (e: IOException){
            LoadResult.Error(e)

        }catch (e:HttpException){
            LoadResult.Error(e)
        }
    }
}