package com.example.feature_details.presentation.staff_details.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.extensions.glide
import com.example.core.tools.extensions.popBackStack
import com.example.feature_details.databinding.FragmentStaffInfoBinding
import com.example.feature_details.presentation.staff_details.viewModel.StaffInfoViewModel
import kotlinx.coroutines.launch
import com.example.core.R
import com.example.core.tools.adapter.adapters.NestedAdapterBase
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.extensions.observeLoadState
import org.koin.androidx.viewmodel.ext.android.viewModel

class StaffInfoFragment : BaseFragment<FragmentStaffInfoBinding>() {

    override fun initBinding(inflater: LayoutInflater) = FragmentStaffInfoBinding.inflate(inflater)

    private val viewModel by viewModel<StaffInfoViewModel>()

    private val adapter by lazy { NestedAdapterBase({ onClickItem(it) }, {}) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStaff()

        observe()

        observeLoadState(viewModel.loadState,binding.progressBar){}

        binding.bestFilms.adapter = adapter

        binding.backArrow.popBackStack()

        binding.nextButton.setOnClickListener {
            viewModel.navigateToFilmography()
            findNavController().navigate(R.id.action_filmInfoFragment_to_filmographyFragment)
        }
    }

    private fun onClickItem(pageCategory: PageCategory) {
        viewModel.navigateToFilInfo(pageCategory.query?.id)
        findNavController().navigate(R.id.action_staffInfoFragment_to_filmInfoFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun observe() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.staff.collect {
                binding.avatar.glide(it.first.posterUrl)
                binding.name.text = it.first.nameRu
                binding.description.text = it.first.profession

                binding.counter.text = resources.getQuantityString(R.plurals.films,it.first.films.size,it.first.films.size)/*it.first.films.size.toString() + " фильма"*/
                adapter.items = it.second
            }
        }
    }

}