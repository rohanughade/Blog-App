package com.rohan.assignment.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BlogEntity (
    @PrimaryKey
    val id: Int,
    val title: String,
    val imageUrl: String,
    val link: String,
    val publishedDate: String
)