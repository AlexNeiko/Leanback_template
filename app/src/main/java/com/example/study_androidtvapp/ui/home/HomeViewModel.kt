package com.example.study_androidtvapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.study_androidtvapp.data.local.Category
import com.example.study_androidtvapp.data.local.Movie
import com.example.study_androidtvapp.data.remote.models.response.MovieResponse
import com.example.study_androidtvapp.data.repo.MoviesRepo
import com.example.study_androidtvapp.ui.detail.DetailFragmentArgs
import com.example.study_androidtvapp.util.Resource
import com.example.study_androidtvapp.util.castToCategorizedMovies
import com.example.study_androidtvapp.util.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel /** 1. Hilt setup */
class HomeViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
): ViewModel() {
    private val _moviesResponse = MutableStateFlow<Resource<List<Category>>>(Resource.Idle())
    val moviesResponse = _moviesResponse.asStateFlow()

    /** mutableEventFlow navigation from VM when -> fun onMovieClicked */
    private val _navigateToDetail = mutableEventFlow<DetailFragmentArgs>()
    val navigateToDetail = _navigateToDetail.asSharedFlow()

    init {
        loadMovies()
    }


    fun loadMovies() {
        viewModelScope.launch {
            with(_moviesResponse){
                tryEmit(Resource.Loading()) /** 0. Init */
                tryEmit(Resource.Success(moviesRepo.getMovies().castToCategorizedMovies()))
            }
        }

    }

    var scrollPos: Pair<Int, Int>? = null
    fun onMovieClicked(item: Movie) {
        if(moviesResponse.value is Resource.Success) {
            val clickedCategory = (moviesResponse.value as Resource.Success<List<Category>>)
                .data.find { it.id == item.categoryId } !!
            _navigateToDetail.tryEmit(
                DetailFragmentArgs(category = clickedCategory, movie = item)
            )

            // Find category position and movie position
            //val catPos = categories.indexOf(clickedCategory)
            val moviePos = clickedCategory.movies.indexOf(item)

            scrollPos = Pair(clickedCategory.id.toInt(), moviePos)
        }

    }

    fun resetScrollPos() {
        scrollPos = null
    }


}