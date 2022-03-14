package com.skillbox.lessons_jsonandretrofit.retrofit.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.lessons_jsonandretrofit.R
import com.skillbox.lessons_jsonandretrofit.retrofit.UserListViewModel
import com.skillbox.lessons_jsonandretrofit.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_user_search.*

class UserSearchFragment : Fragment(R.layout.fragment_user_search) {

    private val viewModel: UserListViewModel by viewModels()

    private var userAdapter: UserAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
    }

    private fun initList() {
        userAdapter = UserAdapter()
        userList.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.userList.observe(viewLifecycleOwner, Observer { userAdapter.items = it })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { enableControls(it.not()) })
        searchButton.setOnClickListener {
            viewModel.search(
                query = searchInput.text.toString()
            )
        }
    }

    private fun enableControls(enable: Boolean) {
        searchInput.isEnabled = enable
        searchButton.isEnabled = enable
    }
}