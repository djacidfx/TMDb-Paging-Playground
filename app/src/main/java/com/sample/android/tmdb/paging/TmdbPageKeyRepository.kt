package com.sample.android.tmdb.paging

import com.sample.android.tmdb.domain.TmdbItem
import java.util.concurrent.Executor

interface TmdbPageKeyRepository<T : TmdbItem> {
    fun getItems(networkExecutor: Executor): Listing<T>
}