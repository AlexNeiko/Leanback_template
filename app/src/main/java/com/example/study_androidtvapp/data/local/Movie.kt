package com.example.study_androidtvapp.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize /** auto generate methods for Parcelable class (to receive in bundles) */
data class Movie(
val actors: List<String> = listOf(),
val desc: String = "",
val directors: List<String> = listOf(),
val genre: List<String> = listOf(),
val imageUrl: String = "",
val imdbUrl: String = "",
val name: String = "",
val rating: Double = 0.0,
val thumbUrl: String = "",
val year: Int = 0,
var categoryId: Long = -1
): Parcelable