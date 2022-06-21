package com.example.hw_roomdao.presentation.contact_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw_roomdao.data.db.models.Contact
import com.example.hw_roomdao.databinding.FragmentItemUserBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ContactAdapterDelegate(
    private val onContactClick: (Contact) -> Unit,
    private val onDeleteContact: (Contact) -> Unit
) : AbsListItemAdapterDelegate<Contact, Contact, ContactAdapterDelegate.Holder>() {

    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            FragmentItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onContactClick, onDeleteContact
        )
    }

    override fun onBindViewHolder(item: Contact, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemUserBinding,
        onContactClick: (Contact) -> Unit,
        onDeleteContact: (Contact) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val nameTextView = binding.nameTextView
        private val emailTextView = binding.emailTextView
        private val avatarImageView = binding.avatarImageVIew

        private var currentContact: Contact? = null

        init {
            binding.root.setOnClickListener { currentContact?.let(onContactClick) }
            binding.removeButton.setOnClickListener { currentContact?.let(onDeleteContact) }
        }

        fun bind(contact: Contact) {
            currentContact = contact
            nameTextView.text = "${contact.firstName} ${contact.lastName}"
            emailTextView.text = contact.email
            avatarImageView.load(contact.avatar)
        }
    }
}
