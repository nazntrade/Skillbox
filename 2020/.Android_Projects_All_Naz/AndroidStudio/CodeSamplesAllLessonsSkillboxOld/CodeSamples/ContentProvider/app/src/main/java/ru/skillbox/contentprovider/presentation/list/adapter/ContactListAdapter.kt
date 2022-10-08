package ru.skillbox.contentprovider.presentation.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.contentprovider.data.Contact

class ContactListAdapter(
    onContactClick: (Contact) -> Unit
): AsyncListDifferDelegationAdapter<Contact>(ContactDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ContactAdapterDelegate(onContactClick))
    }

    class ContactDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

}