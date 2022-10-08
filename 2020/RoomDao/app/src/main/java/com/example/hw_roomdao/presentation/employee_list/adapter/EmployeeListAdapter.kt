package com.example.hw_roomdao.presentation.employee_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_roomdao.data.db.models.Employee
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class EmployeeListAdapter(
    onEmployeeClick: (Employee) -> Unit,
    onDeleteEmployee: (Employee) -> Unit
) : AsyncListDifferDelegationAdapter<Employee>(UserDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(EmployeeAdapterDelegate(onEmployeeClick, onDeleteEmployee))
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.employeeId == newItem.employeeId
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }

}
