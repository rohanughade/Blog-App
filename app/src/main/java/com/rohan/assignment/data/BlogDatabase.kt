package com.rohan.assignment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rohan.assignment.data.entity.BlogEntity

@Database(entities = [BlogEntity::class], version = 1)
abstract class BlogDatabase: RoomDatabase() {

    abstract val dao: BlogDao

}