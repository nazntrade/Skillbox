package com.example.hw_roomdao.presentation.contact_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_roomdao.data.db.models.User
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UserListAdapter(
    onUserClick: (User) -> Unit,
    onDeleteUser: (User) -> Unit
): AsyncListDifferDelegationAdapter<User>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate(onUserClick, onDeleteUser))
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}
