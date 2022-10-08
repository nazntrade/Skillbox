package com.skillbox.lessons_ViewModelAndNavigation_From_List13.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.lessons_ViewModelAndNavigation_From_List13.Person

class PersonAdapter(
    private val onItemClick: (id: Long) -> Unit
) : AsyncListDifferDelegationAdapter<Person>(PersonDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate(onItemClick))
            .addDelegate(DeveloperAdapterDelegate(onItemClick))
    }

    class PersonDiffUtilCallback : DiffUtil.ItemCallback<Person>() {
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