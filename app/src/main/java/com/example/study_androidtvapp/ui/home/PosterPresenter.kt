package com.example.study_androidtvapp.ui.home

import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import coil.load
import coil.size.Scale
import com.example.study_androidtvapp.R
import com.example.study_androidtvapp.data.local.Movie

/** Leanback has presenters (default cardViews). Also You can create custom presenter as Widget. */

class PosterPresenter: Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val imageCardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            cardType = BaseCardView.CARD_TYPE_MAIN_ONLY
            with(mainImageView) {
                val posterWidth = parent.resources.getDimension(R.dimen.poster_width).toInt()
                val posterHeight= parent.resources.getDimension(R.dimen.poster_height).toInt()
                layoutParams = BaseCardView.LayoutParams(posterWidth, posterHeight)
            }
        }
        return ViewHolder(imageCardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val movie = item as Movie
        with(viewHolder.view as ImageCardView) {

            /** DEFAULT UI ITEMS IN LEANBACK PRESENTER */
            mainImageView.load( /** Coil library apply ".load" fun to all ImageViews */
                data = movie.imageUrl,
                builder = {
                    /** Size and Crop image in Coil */
                    scale(Scale.FIT)
                 //   size(300, 400)
                    placeholder(R.drawable.bg_item)
                }
            )
            titleText = movie.name
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            mainImage = null
        }
    }
}
