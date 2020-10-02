package com.sample.android.tmdb.ui

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.sample.android.tmdb.R
import com.sample.android.tmdb.paging.NetworkState
import com.sample.android.tmdb.domain.TmdbItem
import java.util.*

class TmdbAdapter<T : TmdbItem>(private val retryCallback: () -> Unit,
                                private val tmdbClickCallback: TmdbClickCallback<T>)
    : PagedListAdapter<T, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<T>() {
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            Objects.equals(oldItem, newItem)

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.id == newItem.id
}) {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.tmdb_item -> {
                with((holder as TmdbViewHolder).binding) {
                    tmdbItem = getItem(position)
                    executePendingBindings()
                }
            }
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(networkState, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.tmdb_item -> TmdbViewHolder.create(parent, tmdbClickCallback)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.tmdb_item
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}