package com.sample.android.tmdb.ui.paging.main.movie

import com.sample.android.tmdb.domain.model.SortType
import javax.inject.Inject

class DiscoverMoviesFragment @Inject
constructor() : MovieFragment() {

    override val sortType = SortType.DISCOVER
}