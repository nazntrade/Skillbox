package com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffDetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentStaffDetailBinding
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.home.homeAdapters.filmAdapter.FilmAdapter
import com.becker.beckerSkillCinema.utils.loadImage

class FragmentStaffDetail :
    ViewBindingFragment<FragmentStaffDetailBinding>(FragmentStaffDetailBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val viewModel: StaffDetailViewModel by activityViewModels()
    private lateinit var filmAdapter: FilmAdapter
    private val args: FragmentStaffDetailArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStaffDetail(args.staffId)

        setFilmsAdapter()                       // set adapter

        setLoadingStateAndDetails()             // Loading state
        getStaffInfo()                          // get info about person

        setButtonsListeners()                   // set listeners
    }

    private fun setFilmsAdapter() {
        filmAdapter = FilmAdapter(20, {}, { onClickFilm(it) })
        binding.staffDetailBestList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.staffDetailBestList.adapter = filmAdapter
    }

    private fun setLoadingStateAndDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadCurrentStaff.collect { state ->
                when (state) {
                    is StateLoading.Loading -> {
                        binding.apply {
                            progressGroupContainer.progressGroup.isVisible = true
                            progressGroupContainer.loadingRefreshBtn.isVisible = false
                            staffDetailMainGroup.isVisible = false
                            staffDetailBestGroup.isVisible = false
                            staffDetailFilmographyGroup.isVisible = false
                            progressGroupContainer.noAnswerText.isVisible = false
                        }
                    }
                    is StateLoading.Success -> {
                        binding.apply {
                            progressGroupContainer.progressGroup.isVisible = false
                            progressGroupContainer.loadingRefreshBtn.isVisible = false
                            staffDetailMainGroup.isVisible = true
                            staffDetailBestGroup.isVisible = true
                            staffDetailFilmographyGroup.isVisible = true
                        }
                    }
                    else -> {
                        binding.apply {
                            progressGroupContainer.progressGroup.isVisible = false
                            progressGroupContainer.loadingRefreshBtn.isVisible = true
                            staffDetailMainGroup.isVisible = false
                            staffDetailBestGroup.isVisible = false
                            staffDetailFilmographyGroup.isVisible = false
                            progressGroupContainer.noAnswerText.isVisible = true
                        }
                    }
                }
            }
        }
    }

    private fun getStaffInfo() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentStaff.collect { staff ->
                if (staff != null) {
                    binding.apply {
                        staffDetailPoster.loadImage(staff.posterUrl)
                        staffDetailName.text = staff.nameRu ?: staff.nameEn ?: "Unknown name"
                        if (staff.profession != null) staffDetailProfession.text =
                            staff.profession
                        else staffDetailProfession.isVisible = false

                        if (staff.films != null) staffDetailFilmsCount.text =
                            resources.getQuantityString(
                                R.plurals.staff_details_film_count,
                                staff.films.size,
                                staff.films.size
                            )
                        if (staff.films != null) {
                            val list: MutableList<HomeItem> = staff.films.toMutableList()
                            list.removeAll { it.rating == null }
                            val sortedList = list.sortedBy { it.rating?.toDouble() }.reversed()
                            val result = mutableListOf<HomeItem>()

                            if (sortedList.size > 10) {
                                repeat(10) { result.add(sortedList[it]) }
                            } else result.addAll(sortedList)

                            result.sortedBy { it.rating }
                            filmAdapter.submitList(result)
                        }
                        if (staff.facts != null) {
                            factsFieldText.text = staff.facts.toString()
                        }
                    }
                }
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
        val action =
            FragmentStaffDetailDirections.actionFragmentStaffDetailToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    private fun getAllFilmsByStaff() {
        findNavController().navigate(R.id.action_fragmentStaffDetail_to_fragmentFilmography)
    }

    private fun setButtonsListeners() {
        binding.staffDetailShowAllFilmsBtn.setOnClickListener { getAllFilmsByStaff() }
        binding.staffDetailShowAllFilmsTv.setOnClickListener { getAllFilmsByStaff() }
        binding.staffDetailShowAllBestBtn.setOnClickListener { getAllFilmsByStaff() }
        binding.staffDetailShowAllBestTv.setOnClickListener { getAllFilmsByStaff() }
    }
}