package com.example.study_androidtvapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.study_androidtvapp.util.Constants.DELAY_SPLASH_SCREEN
import com.example.study_androidtvapp.util.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel /** 1. Hilt setup */
class SplashViewModel @Inject constructor(): ViewModel() {


    /** coroutine Flow state */
    private val _shouldGoToHome = mutableEventFlow<Boolean>()
    val shouldGoToHome = _shouldGoToHome.asSharedFlow()

    /** Delay of Splash screen */
    init {
        viewModelScope.launch {
            delay(DELAY_SPLASH_SCREEN)
            _shouldGoToHome.tryEmit(true) /** Emit data only when observer exist in Fragment */
        }
    }

}