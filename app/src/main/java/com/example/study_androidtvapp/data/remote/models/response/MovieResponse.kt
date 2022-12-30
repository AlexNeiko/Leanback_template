package com.example.study_androidtvapp.data.remote.models.response


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel

data class MovieResponse(
    @SerializedName("actors")
    val actors: List<String>? = listOf(),
    @SerializedName("desc")
    val desc: String? = "",
    @SerializedName("directors")
    val directors: List<String>? = listOf(),
    @SerializedName("genre")
    val genre: List<String>? = listOf(),
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("imdb_url")
    val imdbUrl: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("rating")
    val rating: Double? = 0.0,
    @SerializedName("thumb_url")
    val thumbUrl: String? = "",
    @SerializedName("year")
    val year: Int? = 0,
    @IgnoredOnParcel var categoryId: Long? = -1
)