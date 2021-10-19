package com.skillbox.list13.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.list13.Person
import com.skillbox.list13.R
import com.skillbox.list13.inflate

class UserAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
): AbsListItemAdapterDelegate<Person.User, Person, UserAdapterDelegate.UserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): UserHolder {
        return UserHolder(
            parent.inflate(R.layout.item_user),
            onItemClick
        )
    }

    override fun isForViewType(item: Person, items: MutableList<Person>, position: Int): Boolean {
        return item is Person.User
    }

    override fun onBindViewHolder(
        item: Person.User,
        holder: UserHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class UserHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ): BasePersonHolder(view, onItemClick) {
        init {
            view.findViewById<TextView>(R.id.developerTextView).isVisible = false
        }

        fun bind(person: Person.User) {
            bindMainInfo(person.name, person.avatarLink, person.age)
        }
    }
}