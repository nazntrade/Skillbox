package com.example.screen_listpage.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.adapter.adapters.NestedAdapterBase
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.extensions.observeLoadState
import com.example.screen_listpage.R
import com.example.screen_listpage.databinding.FragmentSearchStartBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchStartFragment : BaseFragment<FragmentSearchStartBinding>() {
    override fun initBinding(inflater: LayoutInflater) =
        FragmentSearchStartBinding.inflate(inflater)

    private val viewModel by viewModel<SearchStartViewModel>()

    private val adapter = NestedAdapterBase({onClickItem(it)}, {})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeInput()

        observeLoadState(viewModel.loadState,binding.progressBar){}

        super.onViewCreated(view, savedInstanceState)
        binding.settings.setEndIconOnClickListener {
            findNavController().navigate(com.example.core.R.id.action_searchFragment_to_settingsSearchFragment)
        }
        binding.editText.doOnTextChanged { text, _, _, after ->
            viewModel.search(text.toString(), after)
        }

        binding.searchRecyclerView.adapter = adapter

    }

    private fun onClickItem(pageCategory: PageCategory){
        viewModel.navigate(pageCategory)
        findNavController().navigate(com.example.core.R.id.action_searchStartFragment_to_filmInfoFragment)
    }

    private fun observeInput() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.films.collect {
                adapter.items = it
                binding.errorSearch.isVisible = it.isEmpty() && binding.editText.text?.length != 0
            }
        }
    }
}