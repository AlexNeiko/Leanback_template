package com.example.study_androidtvapp.data.remote

import com.example.study_androidtvapp.data.remote.models.response.MovieResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("top250_min.json")
    suspend fun getMovies(): List<MovieResponse>
}