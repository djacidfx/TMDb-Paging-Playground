package com.sample.android.tmdb.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import com.sample.android.tmdb.domain.model.TmdbItem
import com.sample.android.tmdb.ui.base.BaseFragment
import com.sample.android.tmdb.ui.common.ErrorScreen
import com.sample.android.tmdb.ui.common.ProgressScreen
import com.sample.android.tmdb.ui.common.TmdbTheme
import com.sample.android.tmdb.ui.common.composeView
import com.sample.android.tmdb.util.Resource

abstract class FeedFragment<T : TmdbItem> : BaseFragment<Nothing>() {

    protected abstract val viewModel: FeedViewModel<T>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return composeView {
            TmdbTheme {
                when (val resource = viewModel.stateFlow.collectAsState().value) {
                    is Resource.Loading -> ProgressScreen()
                    is Resource.Success -> {
                        FeedScreen(resource.data) { tmdbItem ->
                            startDetailActivity(tmdbItem)
                        }
                    }
                    is Resource.Error -> ErrorScreen(
                        message = resource.message,
                        refresh = viewModel::refresh
                    )
                }
            }
        }
    }
}