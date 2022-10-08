package com.example.clientContprovider.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clientContprovider.contacts.data.Course
import com.example.clientContprovider.databinding.FragmentItemContactBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class CourseAdapterDelegate(
    private val onContactClick: (course: Course) -> Unit
) : AbsListItemAdapterDelegate<Course, Course, CourseAdapterDelegate.Holder>() {

    override fun isForViewType(item: Course, items: MutableList<Course>, position: Int): Boolean {
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

    override fun onBindViewHolder(item: Course, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemContactBinding,
        onContactClick: (course: Course) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val courseTitleView = binding.contactNameTextView

        private var currentCourse: Course? = null

        init {
            binding.root.setOnClickListener {
                currentCourse?.let(onContactClick)
            }
        }

        fun bind(course: Course) {
            currentCourse = course
            courseTitleView.text = course.title
        }
    }
}