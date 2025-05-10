package com.rohan.assignment.repositery

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rohan.assignment.network.ApiService
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rohan.assignment.data.BlogDatabase
import com.rohan.assignment.data.entity.BlogEntity
import com.rohan.assignment.model.Response
import com.rohan.assignment.paging.AssignMentPagingSource
//import com.rohan.assignment.paging.AssignMentPagingSource
import com.rohan.assignment.paging.BlogRemotemediator
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)

class ARepositery @Inject constructor(val blogdb: BlogDatabase, val apiService: ApiService) {
    fun getPostStream(): Flow<PagingData<BlogEntity>>{
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 10,

            ),
            remoteMediator = BlogRemotemediator(blogdb,apiService),
            pagingSourceFactory = {
              //  AssignMentPagingSource(apiService)
                blogdb.dao.pagingSource()
            }
        ).flow
    }


}