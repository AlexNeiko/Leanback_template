package com.example.study_androidtvapp.ui.home

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.example.study_androidtvapp.data.local.Category
import com.example.study_androidtvapp.data.local.Movie
import com.example.study_androidtvapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint


/**
 * Leanback has default Fragments: BrowseSupportFragment() -> init states and remote data.
 * Inside this fragments exists adapters
 *
 * Leanback has presenters (default cardViews). Also You can create custom presenter as Widget.
 *
 * In manifest at <application> block need add … for large images loading and UI working.
 * android:largeHeap="true"
 * android:hardwareAccelerated="false"
 */

@AndroidEntryPoint /** 1. Hilt setup */
class HomeFragment : BrowseSupportFragment() /** Android TV leanback */ {

    private val viewModel: HomeViewModel by viewModels()
    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            prepareEntranceTransition() /** Leanback needs. Step -> 0 (Init transactions -> Progress Bar) */
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData() /** listen events from VM */

        setDynamicBackground() /** Set dynamic bg color change */

        /** Click to Movie and nav to Detail */
        setOnItemViewClickedListener { _, item, _, _ ->
            val movie = item as Movie
            viewModel.onMovieClicked(movie)
        }

    }


    private fun observeData() {

        viewModel.moviesResponse.asLiveData().observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Idle -> { }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    displayData(resource.data)
                    startEntranceTransition() /** Leanback needs. Step -> 1 (Start data transactions) */
                }
                is Resource.Error -> {
                }
            }
        }

        /** nav to detail listener */
        viewModel.navigateToDetail.asLiveData().observe(viewLifecycleOwner) { navToDetailArgs ->
            val action = HomeFragmentDirections
                .actionHomeToDetail(category = navToDetailArgs.category, movie = navToDetailArgs.movie)
            findNavController().navigate(action)
        }
    }

    /** Load data to UI */
    private fun displayData(categories: List<Category>) {
        val adapter = ArrayObjectAdapter(ListRowPresenter())
        for (category in categories) {
            val headerItem = HeaderItem(category.id, category.genre)
            val rowAdapter = ArrayObjectAdapter(PosterPresenter())
            for (movie in category.movies) {
                rowAdapter.add(movie)
            }
            adapter.add(ListRow(headerItem,rowAdapter))
        }
        this.adapter = adapter

        /** Scrolling to row/column when backstack */
        viewModel.scrollPos?.let { (catPos, moviePos) ->
            rowsSupportFragment.setSelectedPosition(
                catPos,
                true,
                ListRowPresenter.SelectItemViewHolderTask(moviePos)
            )
            viewModel.resetScrollPos()
        }
    }


    /** Color generator lib =  implementation "androidx.palette:palette:1.0.0" */
    /** receive drawable and get similar Color (change when item is Selected */
    private fun setDynamicBackground() {
        setOnItemViewSelectedListener { itemViewHolder, _, _, _ ->
            if (itemViewHolder?.view != null) {
                val bitmapDrawable =
                    (itemViewHolder.view as ImageCardView).mainImageView.drawable as? BitmapDrawable
                if (bitmapDrawable != null) {
                    Palette.from(bitmapDrawable.bitmap).generate { palette ->
                        // Priority for vibrantSwatch, if not available dominantSwatch
                        (palette?.dominantSwatch ?: palette?.vibrantSwatch)?.let { swatch ->
                            backgroundManager.color = swatch.rgb
                        }
                    }
                }
            }
        }
    }
}
