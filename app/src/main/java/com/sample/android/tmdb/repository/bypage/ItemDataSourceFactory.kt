package com.sample.android.tmdb.repository.bypage

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.sample.android.tmdb.SortType
import com.sample.android.tmdb.repository.MoviesRemoteDataSource
import com.sample.android.tmdb.vo.Movie

abstract class ItemDataSourceFactory<T, E> : DataSource.Factory<Int, T>() {

    val sourceLiveData = MutableLiveData<PageKeyedItemDataSource<T, E>>()

    abstract fun getDataSource(): PageKeyedItemDataSource<T, E>

    override fun create(): DataSource<Int, T> {
        val source = getDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}