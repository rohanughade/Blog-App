package com.rohan.assignment.di

import android.content.Context
import androidx.room.Room
import com.rohan.assignment.util.Asset.Companion.BASE_URL
import com.rohan.assignment.data.BlogDatabase
import com.rohan.assignment.network.ApiService
import com.rohan.assignment.repositery.ARepositery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): BlogDatabase{
        return Room.databaseBuilder(
            context,
            BlogDatabase::class.java,
            "blog.db"
        ).build()

    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun getRepositery(apiService: ApiService,blogDatabase: BlogDatabase): ARepositery{
        return ARepositery(blogDatabase,apiService)
    }
}