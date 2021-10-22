package com.skillbox.coroutines.android_coroutines

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.coroutines.data.RemoteUser

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
