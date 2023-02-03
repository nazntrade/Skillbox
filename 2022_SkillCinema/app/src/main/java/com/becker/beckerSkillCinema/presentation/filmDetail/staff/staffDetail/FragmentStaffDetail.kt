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
import com.becker.beckerSkillCinema.utils.autoCleared
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
    private var filmAdapter: FilmAdapter by autoCleared()
    private val args: FragmentStaffDetailArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsAdapter()                       // set adapter
        setLoadingStateAndDetails()             // Loading state
        getStaffInfo()                          // get info about person
        setListeners()                          // set listeners
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
        viewModel.getStaffDetail(args.staffId)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentStaff.collect { staff ->
                if (staff != null) {
                    binding.apply {
                        staffDetailPoster.apply {
                            loadImage(staff.posterUrl)
                            setListeners(link = staff.posterUrl)
                        }

                        staffDetailName.text = staff.nameRu ?: staff.nameEn ?: "Unknown name"

                        if (staff.profession != null) staffDetailProfession.text =
                            staff.profession
                        else staffDetailProfession.isVisible = false

                        if (staff.birthday != null)
                            "Год рождения: ${staff.birthday.substring(0, 4)}".also {
                                staffDetailBirthday.text = it
                            }
                        else staffDetailBirthday.isVisible = false

                        if (staff.birthPlace != null)
                            "Место рождения: ${staff.birthPlace}".also {
                                staffDetailPlaceOfBirthday.text = it
                            }
                        else staffDetailPlaceOfBirthday.isVisible = false

                        if (staff.films != null) {
                            staffDetailFilmsCount.text =
                                resources.getQuantityString(
                                    R.plurals.staff_details_film_count,
                                    staff.films.size,
                                    staff.films.size
                                )

                            val list: MutableList<HomeItem> = staff.films.toMutableList()
                            list.removeAll { it.rating == null }
                            val sortedListBest = list.sortedBy { it.rating?.toDouble() }.reversed()
                            val resultBest = mutableListOf<HomeItem>()
                            if (sortedListBest.size > 10) {
                                repeat(10) { resultBest.add(sortedListBest[it]) }
                            } else resultBest.addAll(sortedListBest)
                            resultBest.sortedBy { it.rating }
                            // response from server has no poster!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            filmAdapter.submitList(resultBest)
                        }

                        if (staff.facts == null || staff.facts.isEmpty()) {
                            factsTitle.isVisible = false
                            factsFieldText.isVisible = false
                        } else {
                            factsTitle.isVisible = true
                            factsFieldText.isVisible = true
                            factsFieldText.text = staff.facts.joinToString(" ")
                        }
                    }
                }
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
        viewModel.putFilmId(filmId)
        val action =
            FragmentStaffDetailDirections.actionFragmentStaffDetailToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    private fun getAllFilmsByCurrentStaff() {
        findNavController().navigate(R.id.action_fragmentStaffDetail_to_fragmentFilmography)
    }

    private fun setListeners(link: String = "") {
        binding.apply {
            staffDetailFilmographyGroup.setOnClickListener { getAllFilmsByCurrentStaff() }
            staffDetailBestTitle.setOnClickListener { getAllFilmsByCurrentStaff() }
            staffDetailPoster.setOnClickListener {
                val action = FragmentStaffDetailDirections
                    .actionFragmentStaffDetailToFragmentBigImage(link)
                findNavController().navigate(action)
            }
        }
    }
}