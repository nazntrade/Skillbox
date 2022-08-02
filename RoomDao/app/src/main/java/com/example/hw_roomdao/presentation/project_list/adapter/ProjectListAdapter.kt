package com.example.hw_roomdao.presentation.project_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_roomdao.data.db.models.Project
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProjectListAdapter(
    onProjectClick: (Project) -> Unit,
    onDeleteProject: (Project) -> Unit
) : AsyncListDifferDelegationAdapter<Project>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ProjectsAdapterDelegate(onProjectClick, onDeleteProject))
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.projectId == newItem.projectId
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }
    }

}
