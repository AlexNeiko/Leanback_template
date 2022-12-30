package com.example.study_androidtvapp.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.study_androidtvapp.R
import com.example.study_androidtvapp.data.local.Movie
import com.example.study_androidtvapp.util.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _args = MutableStateFlow<DetailFragmentArgs?>(null)
    val args = _args.asStateFlow()

    private val _toast = mutableEventFlow<Int>()
    val toast = _toast.asSharedFlow()

    private val _navigateToDetail = mutableEventFlow<DetailFragmentArgs>()
    val navigateToDetail = _navigateToDetail.asSharedFlow()

    init {
        /** receive parameters  from navigation(arguments in action) */
        val parsedArgs = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
        _args.tryEmit(parsedArgs)
    }


    fun onPlayClicked() {
        _toast.tryEmit(R.string.detail_tada)
    }

    fun onMovieClicked(movie: Movie) {
        _navigateToDetail.tryEmit(DetailFragmentArgs(_args.value!!.category, movie))
    }
}