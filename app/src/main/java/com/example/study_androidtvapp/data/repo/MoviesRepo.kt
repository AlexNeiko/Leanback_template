package com.example.study_androidtvapp.data.repo

import com.example.study_androidtvapp.data.remote.ApiInterface
import javax.inject.Inject

class MoviesRepo @Inject constructor(
    private val apiInterface: ApiInterface
) : ApiInterface by apiInterface