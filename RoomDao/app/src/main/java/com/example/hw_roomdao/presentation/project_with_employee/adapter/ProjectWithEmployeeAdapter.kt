package com.example.hw_roomdao.presentation.project_with_employee.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_roomdao.data.db.models.relations.ProjectWithEmployee
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProjectWithEmployeeAdapter(
    onEmployeeClick: (ProjectWithEmployee) -> Unit,
    onDeleteEmployee: (ProjectWithEmployee) -> Unit
) : AsyncListDifferDelegationAdapter<ProjectWithEmployee>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ProjectWithEmployeeAdapterDelegate(onEmployeeClick, onDeleteEmployee))
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<ProjectWithEmployee>() {
        override fun areItemsTheSame(oldItem: ProjectWithEmployee, newItem: ProjectWithEmployee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProjectWithEmployee, newItem: ProjectWithEmployee): Boolean {
            return oldItem == newItem
        }
    }

}
