package ru.ktsstudio.roomdao.presentation.user_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.view_toolbar.*
import ru.ktsstudio.roomdao.R
import ru.ktsstudio.roomdao.data.db.models.User
import ru.ktsstudio.roomdao.di.ViewModelFactory
import ru.ktsstudio.roomdao.presentation.user_list.adapter.UserListAdapter
import ru.ktsstudio.roomdao.utils.autoCleared

class UsersFragment : Fragment(R.layout.fragment_users) {

    private val viewModel: UserListViewModel by viewModels { ViewModelFactory() }
    private var userAdapter: UserListAdapter by autoCleared()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        initList()
        addUserButton.setOnClickListener {
            findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToAddUserFragment())
        }
        bindViewModel()
        viewModel.loadList()
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.user_list_title)
    }

    private fun initList() {
        userAdapter = UserListAdapter(::navigateToUserDetails, viewModel::removeUser)
        with(userList) {
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigateToUserDetails(user: User) {
        val direction = UsersFragmentDirections.actionUsersFragmentToAddUserFragment(user.id)
        findNavController().navigate(direction)
    }

    private fun bindViewModel() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) { userAdapter.items = it }
    }
}