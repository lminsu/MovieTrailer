package com.example.movietrailer.data

import com.google.gson.annotations.SerializedName

data class Video(
    val id: Long,
    val title: String?,
    val key: String?,
    @SerializedName("release_data")
    val releaseData: String?,
    val overview: String?,
    val popularity: Double?,
)