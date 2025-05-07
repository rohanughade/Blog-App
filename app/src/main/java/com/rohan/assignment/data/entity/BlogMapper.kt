package com.rohan.assignment.data.entity

import com.rohan.assignment.model.Response

fun Response.toBlogEntity(): BlogEntity{
    return BlogEntity(
        id = this.id,
        title = this.title.rendered,
        imageUrl = this.jetpack_featured_media_url,
        link = this.link,
        publishedDate = this.date
    )
}