package com.skillbox.github.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.ui.repository_list.Repositories

class GithubRepAdapter(
    onItemClick: (itemRepo: Repositories) -> Unit
) : AsyncListDifferDelegationAdapter<Repositories>(RepoDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(GithubRepAdapterDelegate(onItemClick))
    }

    class RepoDiffUtilCallback : DiffUtil.ItemCallback<Repositories>() {
        override fun areItemsTheSame(oldItem: Repositories, newItem: Repositories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repositories, newItem: Repositories): Boolean {
            return oldItem == newItem
        }
    }
}