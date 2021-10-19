package com.skillbox.list13.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.list13.Person
import com.skillbox.list13.R
import com.skillbox.list13.inflate

class PersonAdapter(
    private val onItemClick: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<Person>(PersonDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate(onItemClick))
            .addDelegate(DeveloperAdapterDelegate(onItemClick))
    }

    class PersonDiffUtilCallback: DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return when {
                oldItem is Person.Developer && newItem is Person.Developer -> oldItem.id == newItem.id
                oldItem is Person.User && newItem is Person.User -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}