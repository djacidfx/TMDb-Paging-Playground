package com.sample.android.tmdb.ui.detail.tvshow

import com.sample.android.tmdb.ui.detail.DetailActivity
import com.sample.android.tmdb.ui.detail.DetailFragment
import javax.inject.Inject

class DetailTVShowActivity: DetailActivity()  {

    @Inject
    lateinit var tvShowDetailFragment: TVShowDetailFragment

    override val fragment: DetailFragment
        get() = tvShowDetailFragment
}