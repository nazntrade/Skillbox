package com.example.clientContprovider.contacts.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.clientContprovider.contacts.data.Course
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CourseListAdapter(
    onContactClick: (course: Course) -> Unit
) : AsyncListDifferDelegationAdapter<Course>(ContactDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(CourseAdapterDelegate(onContactClick))
    }

    class ContactDiffUtilCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}