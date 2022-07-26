package com.example.hw_roomdao.presentation.project_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_roomdao.data.db.models.Project
import com.example.hw_roomdao.databinding.FragmentItemProjectBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ProjectsAdapterDelegate(
    private val onProjectClick: (Project) -> Unit,
    private val onDeleteProject: (Project) -> Unit
) : AbsListItemAdapterDelegate<Project, Project, ProjectsAdapterDelegate.Holder>() {

    override fun isForViewType(item: Project, items: MutableList<Project>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            FragmentItemProjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onProjectClick, onDeleteProject
        )
    }

    override fun onBindViewHolder(item: Project, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemProjectBinding,
        onProjectClick: (Project) -> Unit,
        onDeleteProject: (Project) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val nameTextView = binding.chatTitleTextView

        private var currentProject: Project? = null

        init {
            binding.root.setOnClickListener { currentProject?.let(onProjectClick) }
            binding.removeButton.setOnClickListener { currentProject?.let(onDeleteProject) }
        }

        fun bind(project: Project) {
            currentProject = project
            nameTextView.text = project.title
        }
    }
}
