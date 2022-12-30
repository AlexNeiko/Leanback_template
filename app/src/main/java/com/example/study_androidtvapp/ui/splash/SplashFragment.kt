package com.example.study_androidtvapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.study_androidtvapp.R
import com.example.study_androidtvapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint /** 1. Hilt setup */
class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSplashBinding.inflate(inflater, container, false)

        /** start listen Emit state */
        viewModel.shouldGoToHome.asLiveData().observe(viewLifecycleOwner) { shouldGoToHome ->
            if (shouldGoToHome) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

}