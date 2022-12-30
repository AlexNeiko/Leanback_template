package com.example.study_androidtvapp.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.study_androidtvapp.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint /** 1. Hilt setup */
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}