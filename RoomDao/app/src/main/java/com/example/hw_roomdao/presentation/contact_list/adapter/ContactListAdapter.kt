package com.example.hw_roomdao.presentation.contact_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_roomdao.data.db.models.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactListAdapter(
    onContactClick: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit
) : AsyncListDifferDelegationAdapter<Contact>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ContactAdapterDelegate(onContactClick, onDeleteContact))
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

}
