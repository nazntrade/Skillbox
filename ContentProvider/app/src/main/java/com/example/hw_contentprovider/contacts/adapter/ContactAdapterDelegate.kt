package com.example.hw_contentprovider.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.contacts.data.Contact
import com.example.hw_contentprovider.databinding.FragmentItemContactBinding
import com.example.hw_contentprovider.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ContactAdapterDelegate(
    private val onContactClick: (contact: Contact) -> Unit
) : AbsListItemAdapterDelegate<Contact, Contact, ContactAdapterDelegate.Holder>() {

    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            FragmentItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onContactClick
        )
    }

    override fun onBindViewHolder(item: Contact, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemContactBinding,
        onContactClick: (contact: Contact) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val contactName = binding.contactNameTextView

        private var currentContact: Contact? = null

        init {
            binding.root.setOnClickListener {
                currentContact?.let(onContactClick)
            }
        }

        fun bind(contact: Contact) {
            currentContact = contact
            contactName.text = contact.firstName
        }
    }

}