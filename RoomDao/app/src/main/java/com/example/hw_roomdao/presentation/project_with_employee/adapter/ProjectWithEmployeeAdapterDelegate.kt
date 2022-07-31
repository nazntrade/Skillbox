package com.example.hw_roomdao.presentation.project_with_employee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_roomdao.data.db.models.ProjectWithEmployee
import com.example.hw_roomdao.data.db.models.relations.ProjectWithEmployee
import com.example.hw_roomdao.databinding.FragmentItemEmployeeBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ProjectWithEmployeeAdapterDelegate(
    private val onEmployeeClick: (ProjectWithEmployee) -> Unit,
    private val onDeleteEmployee: (ProjectWithEmployee) -> Unit
) : AbsListItemAdapterDelegate<ProjectWithEmployee, ProjectWithEmployee, ProjectWithEmployeeAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: ProjectWithEmployee,
        items: MutableList<ProjectWithEmployee>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            FragmentItemEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onEmployeeClick, onDeleteEmployee
        )
    }

    override fun onBindViewHolder(item: ProjectWithEmployee, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemEmployeeBinding,
        onEmployeeClick: (ProjectWithEmployee) -> Unit,
        onDeleteEmployee: (ProjectWithEmployee) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val nameTextView = binding.nameTextView
        private val emailTextView = binding.emailTextView

        private var currentProjectWithEmployee: ProjectWithEmployee? = null

        init {
            binding.root.setOnClickListener { currentProjectWithEmployee?.let(onEmployeeClick) }
            binding.removeButton.setOnClickListener { currentProjectWithEmployee?.let(onDeleteEmployee) }
        }

        fun bind(projectWithEmployee: ProjectWithEmployee) {
            currentProjectWithEmployee = projectWithEmployee
            nameTextView.text = "${projectWithEmployee.employees.} ${projectWithEmployee.lastName}"
            emailTextView.text = projectWithEmployee.email
        }
    }
}
