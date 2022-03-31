package com.skillbox.github.ui.repository_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.databinding.FragmentItemRepositoriesBinding
import com.skillbox.github.ui.repository_list.Repositories

class GithubRepAdapterDelegate(
    private var onItemClick: (itemRepo: Repositories) -> Unit
) :
    AbsListItemAdapterDelegate<Repositories, Repositories, GithubRepAdapterDelegate.GithubRepoHolder>() {

    override fun isForViewType(
        item: Repositories,
        items: MutableList<Repositories>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): GithubRepoHolder {
        return GithubRepoHolder(
            FragmentItemRepositoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(
        item: Repositories,
        holder: GithubRepoHolder,
        payloads: MutableList<Any>
    ) {
        holder.bindRepo(item)
    }

    class GithubRepoHolder(
        binding: FragmentItemRepositoriesBinding,
        onItemClick: (itemRepo: Repositories) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val repoNameView = binding.repoNameTextView

        private var itemRepo: Repositories? = null

        init {
            binding.root.setOnClickListener {
                itemRepo?.let { onItemClick(it) }
            }
        }

        fun bindRepo(itemRepo: Repositories) {
            this.itemRepo = itemRepo
            repoNameView.text = itemRepo.name
        }
    }
}