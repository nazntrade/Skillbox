package ru.ktsstudio.roomdao.presentation.user_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.ktsstudio.roomdao.data.db.models.User

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
