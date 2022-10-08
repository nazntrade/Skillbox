package com.skillbox.jsonandretrofit.retrofit.ui

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.jsonandretrofit.retrofit.RemoteUser

class UserAdapter : AsyncListDifferDelegationAdapter<RemoteUser>(
    UserDiffUtilCallback()
) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate())
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<RemoteUser>() {
        override fun areItemsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
            return oldItem == newItem
        }
    }
}