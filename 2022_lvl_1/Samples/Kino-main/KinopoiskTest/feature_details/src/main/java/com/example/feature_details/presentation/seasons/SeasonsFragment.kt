package com.example.feature_details.presentation.seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.adapter.adapters.NestedAdapterBase
import com.example.feature_details.databinding.FragmentRecyclerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeasonsFragment: BaseFragment<FragmentRecyclerBinding>() {
    override fun initBinding(inflater: LayoutInflater) =
        FragmentRecyclerBinding.inflate(inflater)

    private val viewModel by viewModel<SeasonsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filmsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        binding.filmsRecyclerView.adapter = NestedAdapterBase({},{}).apply {
            items = viewModel.getListInfo()
        }

    }

}