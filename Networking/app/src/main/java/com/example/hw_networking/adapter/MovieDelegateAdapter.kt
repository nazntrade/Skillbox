package com.example.hw_networking.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_networking.movies.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MovieDelegateAdapter:
    AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, MovieDelegateAdapter.MovieHolder>() {

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        item: RemoteMovie,
        holder: MovieHolder,
        payloads: MutableList<Any>
    ) {
        TODO("Not yet implemented")
    }

    class MovieHolder(): RecyclerView.ViewHolder(){

    }

}