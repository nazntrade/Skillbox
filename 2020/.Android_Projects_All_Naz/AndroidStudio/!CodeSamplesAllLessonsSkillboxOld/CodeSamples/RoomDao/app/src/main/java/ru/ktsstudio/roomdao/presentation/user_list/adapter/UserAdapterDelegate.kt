package ru.ktsstudio.roomdao.presentation.user_list.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*
import ru.ktsstudio.roomdao.R
import ru.ktsstudio.roomdao.data.db.models.User
import ru.ktsstudio.roomdao.utils.inflate

class UserAdapterDelegate(
    private val onUserClick: (User) -> Unit,
    private val onDeleteUser: (User) -> Unit
): AbsListItemAdapterDelegate<User, User, UserAdapterDelegate.Holder>() {

    override fun isForViewType(item: User, items: MutableList<User>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return parent.inflate(R.layout.item_user).let {
            Holder(it, onUserClick, onDeleteUser)
        }
    }

    override fun onBindViewHolder(item: User, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        onUserClick: (User) -> Unit,
        onDeleteUser: (User) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var currentUser: User? = null

        init {
            containerView.setOnClickListener { currentUser?.let(onUserClick) }
            removeButton.setOnClickListener { currentUser?.let(onDeleteUser) }
        }

        fun bind(user: User) {
            currentUser = user
            nameTextView.text = "${user.firstName} ${user.lastName}"
            emailTextView.text = user.email
            Glide.with(itemView)
                .load(user.avatar)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_emoji)
                .into(avatarImageVIew)
        }

    }
}
