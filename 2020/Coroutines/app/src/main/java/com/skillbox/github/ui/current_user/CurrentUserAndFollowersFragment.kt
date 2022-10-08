package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.skillbox.github.R
import com.skillbox.github.databinding.FragmentCurrentUserBinding
import com.skillbox.github.ui.adapters.FollowingAdapter
import com.skillbox.github.utils.autoCleared

class CurrentUserAndFollowersFragment : Fragment(R.layout.fragment_current_user) {

    private lateinit var binding: FragmentCurrentUserBinding
    private val viewModel: CurrentUserAndFollowersViewModel by viewModels()
    private var followingAdapter: FollowingAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentUserBinding.bind(view)

        initList()
        bindViewModel()
    }

    private fun initList() {
        followingAdapter = FollowingAdapter()
        with(binding.followersListView) {
            adapter = followingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }
    }

    private fun bindViewModel() {
        viewModel.getInfoAsyncViewModel()

        val avatarUrlView = binding.currentUserAvatarImageView
        val loginView = binding.currentUserLoginTextView
        val idView = binding.currentUserIdTextView
        val nameView = binding.currentUserNameTextView
        val locationView = binding.currentUserLocationTextView
        val followingTextView = binding.imageViewFollowing

        viewModel.currentUser.observe(viewLifecycleOwner) { it ->
            avatarUrlView.load(it.avatar_url) {
                placeholder(R.drawable.loading)
            }
            loginView.text = it.login
            "id: ${it.id}".also { idView.text = it }
            "Name: ${it.name}".also { nameView.text = it }
            "Location: ${it.location}".also { locationView.text = it }
        }
        viewModel.followingList.observe(viewLifecycleOwner) {
            followingAdapter.items = it
            followingTextView.isVisible = true
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::doWhileLoad)
    }

    private fun doWhileLoad(isLoading: Boolean) {
        binding.progressBar.isGone = !isLoading
    }
}