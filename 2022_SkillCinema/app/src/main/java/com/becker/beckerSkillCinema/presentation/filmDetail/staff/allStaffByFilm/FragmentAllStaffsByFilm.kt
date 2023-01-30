package com.becker.beckerSkillCinema.presentation.filmDetail.staff.allStaffByFilm

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import com.becker.beckerSkillCinema.databinding.FragmentStaffAllByFilmBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffAdapter.StaffAdapter
import com.becker.beckerSkillCinema.utils.autoCleared

class FragmentAllStaffsByFilm :
    ViewBindingFragment<FragmentStaffAllByFilmBinding>(FragmentStaffAllByFilmBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val viewModel: FilmDetailViewModel by activityViewModels()
    private var adapter: StaffAdapter by autoCleared()
    private val args: FragmentAllStaffsByFilmArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStaffList()
    }

    private fun setStaffList() {
        adapter = StaffAdapter { onItemClick(it) }

        binding.allStaffsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.allStaffsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            when (args.professionKey) {
                "ACTOR" -> {
                    binding.allStaffsCategoryTv.text =
                        resources.getString(R.string.label_film_actors)
                    viewModel.currentFilmActors.collect { actorList ->
                        adapter.submitList(actorList)
                    }
                }
                else -> {
                    binding.allStaffsCategoryTv.text =
                        resources.getString(R.string.label_film_makers)
                    viewModel.currentFilmMakers.collect { makersList ->
                        adapter.submitList(makersList)
                    }
                }
            }
        }
    }

    private fun onItemClick(staff: ResponseStaffByFilmId) {
        val action = FragmentAllStaffsByFilmDirections
            .actionFragmentAllStaffsByFilmToFragmentStaffDetail(staff.staffId)
        findNavController().navigate(action)
    }
}