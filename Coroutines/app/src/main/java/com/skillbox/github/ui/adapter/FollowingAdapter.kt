package com.skillbox.github.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.ui.current_user.UserFollowing

class FollowingAdapter : AsyncListDifferDelegationAdapter<UserFollowing>(
    FollowersDiffUtilCallback()
) {

    init {
        delegatesManager.addDelegate(FollowingAdapterDelegate())
    }

    class FollowersDiffUtilCallback : DiffUtil.ItemCallback<UserFollowing>() {
        override fun areItemsTheSame(oldItem: UserFollowing, newItem: UserFollowing): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserFollowing, newItem: UserFollowing): Boolean {
            return oldItem == newItem
        }
    }
}