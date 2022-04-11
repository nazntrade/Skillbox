package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.R
import com.skillbox.github.databinding.FragmentRepositoryListBinding
import com.skillbox.github.ui.adapter.GithubRepAdapter
import com.skillbox.github.utils.autoCleared

class GithubRepMainFragment : Fragment(R.layout.fragment_repository_list) {

    private lateinit var binding: FragmentRepositoryListBinding
    private var githubRepAdapter: GithubRepAdapter by autoCleared()  // ?????autoCleared
    private val viewModel: GithubRepViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryListBinding.bind(view)

        initList()
        getRepoList()
        bindViewModel()
    }

    private fun getRepoList() {
        viewModel.getRepoListFromViewModel()
    }

    private fun initList() {
        githubRepAdapter = GithubRepAdapter { itemRepo -> navigate(itemRepo) }
        with(binding.githubRepoListView) {
            adapter = githubRepAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun bindViewModel() {
        viewModel.repoList.observe(viewLifecycleOwner) { githubRepAdapter.items = it }
        viewModel.isLoading.observe(viewLifecycleOwner, ::doWhileLoad)
    }

    private fun doWhileLoad(isLoading: Boolean) {
        binding.progressBar.isGone = !isLoading
    }

    private fun navigate(itemRepo: Repositories) {
        val action =
            GithubRepMainFragmentDirections.actionRepositoryListFragmentToDetailFragment(itemRepo)
        findNavController().navigate(action)
    }
}