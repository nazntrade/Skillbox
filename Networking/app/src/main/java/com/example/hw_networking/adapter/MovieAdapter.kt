package com.example.hw_networking.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_networking.movies.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieAdapter: AsyncListDifferDelegationAdapter<RemoteMovie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieDelegateAdapter())
    }

    class MovieDiffUtilCallback: DiffUtil.ItemCallback<RemoteMovie>(){
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            TODO("Not yet implemented")
        }

    }
}