package com.skillbox.lessons_networking.movie_list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.lessons_networking.R
import com.skillbox.lessons_networking.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MovieDelegateAdapter: AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, MovieDelegateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_movie))
    }

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean = true

    override fun onBindViewHolder(item: RemoteMovie, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: RemoteMovie) {
            titleTextView.text = item.title
            yearTextView.text = item.year.toString()
        }

    }
}