package com.example.hw_networking.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_networking.movies.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieAdapter(
    onItemClick: (imdbID: String) -> Unit
) : AsyncListDifferDelegationAdapter<RemoteMovie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieDelegateAdapter(onItemClick))
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }
    }
}