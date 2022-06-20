package com.example.hw_roomdao.presentation.contact_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw_roomdao.R
import com.example.hw_roomdao.data.db.models.User
import com.example.hw_roomdao.databinding.FragmentItemUserBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class UserAdapterDelegate(
    private val onUserClick: (User) -> Unit,
    private val onDeleteUser: (User) -> Unit
): AbsListItemAdapterDelegate<User, User, UserAdapterDelegate.Holder>() {

    override fun isForViewType(item: User, items: MutableList<User>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            FragmentItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onUserClick, onDeleteUser)
    }

    override fun onBindViewHolder(item: User, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemUserBinding,
        onUserClick: (User) -> Unit,
        onDeleteUser: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val nameTextView = binding.nameTextView
        private val emailTextView = binding.emailTextView
        private val avatarImageView = binding.avatarImageVIew

        private var currentUser: User? = null

        init {
            binding.root.setOnClickListener { currentUser?.let(onUserClick) }
            binding.removeButton.setOnClickListener { currentUser?.let(onDeleteUser) }
        }

        fun bind(user: User) {
            currentUser = user
            nameTextView.text = "${user.firstName} ${user.lastName}"
            emailTextView.text = user.email
            avatarImageView.load(R.drawable.smiling_face_emoji)
        }
    }
}
