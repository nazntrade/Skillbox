package com.skillbox.github.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.databinding.FragmentItemFollowersBinding
import com.skillbox.github.ui.current_user.UserFollowing

class FollowingAdapterDelegate :
    AbsListItemAdapterDelegate<UserFollowing, UserFollowing, FollowingAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: UserFollowing,
        items: MutableList<UserFollowing>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            FragmentItemFollowersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(item: UserFollowing, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemFollowersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val avatarImageView = binding.followerAvatarImageView
        private val loginTextView = binding.followerLoginTextView

        fun bind(item: UserFollowing) {
            avatarImageView.load(item.avatar_url) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.loading)
            }
            loginTextView.text = item.login
        }
    }
}