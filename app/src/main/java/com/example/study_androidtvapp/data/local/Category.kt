package com.example.study_androidtvapp.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize /** auto generate methods for Parcelable class (to receive in bundles) */
data class Category(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
): Parcelable
