package com.example.hw_roomdao.presentation.employee_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_roomdao.data.db.models.Employee
import com.example.hw_roomdao.databinding.FragmentItemEmployeeBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class EmployeeAdapterDelegate(
    private val onEmployeeClick: (Employee) -> Unit,
    private val onDeleteEmployee: (Employee) -> Unit
) : AbsListItemAdapterDelegate<Employee, Employee, EmployeeAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Employee,
        items: MutableList<Employee>,
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

    override fun onBindViewHolder(item: Employee, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        binding: FragmentItemEmployeeBinding,
        onEmployeeClick: (Employee) -> Unit,
        onDeleteEmployee: (Employee) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val nameTextView = binding.nameTextView
        private val emailTextView = binding.emailTextView

        private var currentEmployee: Employee? = null

        init {
            binding.root.setOnClickListener { currentEmployee?.let(onEmployeeClick) }
            binding.removeButton.setOnClickListener { currentEmployee?.let(onDeleteEmployee) }
        }

        fun bind(employee: Employee) {
            currentEmployee = employee
            nameTextView.text = "${employee.firstName} ${employee.lastName}"
            emailTextView.text = employee.email
        }
    }
}
