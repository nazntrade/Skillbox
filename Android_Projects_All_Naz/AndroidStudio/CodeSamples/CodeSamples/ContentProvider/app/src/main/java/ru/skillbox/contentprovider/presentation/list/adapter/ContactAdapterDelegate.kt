package ru.skillbox.contentprovider.presentation.list.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*
import ru.skillbox.contentprovider.R
import ru.skillbox.contentprovider.data.Contact
import ru.skillbox.contentprovider.utils.inflate

/**
 * @author Maxim Myalkin (MaxMyalkin) on 27.09.2020.
 */
class ContactAdapterDelegate(
    private val onContactClick: (Contact) -> Unit
): AbsListItemAdapterDelegate<Contact, Contact, ContactAdapterDelegate.Holder>() {

    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return parent.inflate(R.layout.item_contact).let {
            Holder(it, onContactClick)
        }
    }

    override fun onBindViewHolder(item: Contact, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        onContactClick: (Contact) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var currentContact: Contact? = null

        init {
            containerView.setOnClickListener { currentContact?.let(onContactClick) }
        }

        fun bind(contact: Contact) {
            currentContact = contact
            contactNameTextView.text = contact.name
            contactPhoneTextView.text = contact.phones.joinToString("\n")
        }

    }
}