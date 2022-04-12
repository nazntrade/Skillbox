package com.skillbox.github.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
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
    ) : RecyclerView.ViewHolder(binding.root) {

        private val repoNameView = binding.repoNameTextView
        private val iconForList = binding.iconForList

        private var itemRepo: Repositories? = null

        init {
            binding.root.setOnClickListener {
                itemRepo?.let { onItemClick(it) }
            }
        }

        fun bindRepo(item: Repositories) {
            itemRepo = item
            "Repo:  ${item.name}".also { repoNameView.text = it }
            iconForList.load(item.owner.avatar_url) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.loading)
            }
        }
    }
}