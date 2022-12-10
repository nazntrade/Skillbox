package com.example.feature_details.presentation.filmography.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.adapter.adapters.NestedAdapterBase
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.extensions.observeLoadState
import com.example.feature_details.databinding.FragmentRecyclerBinding
import com.example.feature_details.presentation.filmography.viewModel.FilmographyViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmographyFragment : BaseFragment<FragmentRecyclerBinding>() {

    override fun initBinding(inflater: LayoutInflater) = FragmentRecyclerBinding.inflate(inflater)

    private val adapter by lazy { NestedAdapterBase({onGetPosterClick(it.query?.id)},{onClickItem(it as PageCategory)}) }

    private val viewModel by viewModel<FilmographyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        observeLoadState(viewModel.loadState,binding.progressBar){}

        binding.filmsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter.items = viewModel.startList()
        binding.filmsRecyclerView.adapter = adapter
    }


    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.poster.collect {
                adapter.items = it.first
                binding.filmsRecyclerView.adapter?.notifyItemChanged(it.second)
            }
        }
    }

    private fun onGetPosterClick(position: Int?) {
        viewModel.onClick(position)
    }

    private fun onClickItem(pageCategory: PageCategory){
        viewModel.navigate(pageCategory.query?.id)
        findNavController().navigate(com.example.core.R.id.action_filmography_to_filmInfoFragment)
    }

}