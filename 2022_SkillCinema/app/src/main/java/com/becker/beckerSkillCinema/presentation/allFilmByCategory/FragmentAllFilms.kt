package com.becker.beckerSkillCinema.presentation.allFilmByCategory

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.FragmentAllFilmsBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.allFilmByCategory.allFilmAdapters.AllFilmAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class FragmentAllFilms :
    ViewBindingFragment<FragmentAllFilmsBinding>(FragmentAllFilmsBinding::inflate) {

    private val viewModel: AllFilmsViewModel by activityViewModels()
    private val incomeArgsCategory: FragmentAllFilmsArgs by navArgs()
    private lateinit var allFilmAdapter: AllFilmAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        setAdapter()
        setFilmList(/*incomeArgsCategory.currentCategory*/)
    }

    private fun setLayout() {
        binding.apply {
            allFilmsCategoryTv.text = incomeArgsCategory.currentCategory.text
            allFilmsToHomeBtn.setOnClickListener { findNavController().popBackStack() }
            progressGroupContainer
                .loadingRefreshBtn.setOnClickListener {////////////////////////////////
                    setFilmList(/*incomeArgsCategory.currentCategory*/)
                }
        }
        binding.allFilmsList.layoutManager =
            GridLayoutManager(
                requireContext(),
                3,
                GridLayoutManager.VERTICAL,
                false
            )
    }

    private fun setAdapter() {

        allFilmAdapter = AllFilmAdapter { onClickFilm(it) }

        allFilmAdapter.addLoadStateListener { state -> // ???????????????????????????????????????????
            val currentState = state.refresh
            binding.apply {
                allFilmsList.isVisible = currentState != LoadState.Loading
                progressGroupContainer.progressGroup.isVisible = currentState == LoadState.Loading
                progressGroupContainer.loadingRefreshBtn.isVisible =
                    currentState != LoadState.Loading
            }

            when (currentState) {
                is LoadState.Loading -> {
                    binding.apply {
                        allFilmsList.isVisible = false
                        progressGroupContainer.progressGroup.isVisible = true
                        progressGroupContainer.loadingRefreshBtn.isVisible = false
                        progressGroupContainer.noAnswerText.isVisible = false
                    }
                }
                is LoadState.NotLoading -> {
                    binding.apply {
                        binding.allFilmsList.isVisible = true
                        progressGroupContainer.progressGroup.isVisible = false
                        progressGroupContainer.loadingRefreshBtn.isVisible = true
                    }
                }
                else -> {
                    binding.apply {
                        binding.allFilmsList.isVisible = false
                        progressGroupContainer.loadingProgressBar.isVisible = false
                        progressGroupContainer.loadingRefreshBtn.isVisible = true
                        progressGroupContainer.noAnswerText.isVisible = true
                        progressGroupContainer
                            .loadingRefreshBtn
                            .setOnClickListener { setFilmList(/*incomeArgsCategory.currentCategory*/) }
                    }
                }
            }
        }

    }

    private fun setFilmList(/*currentCategory: CategoriesFilms*/) {
        if (incomeArgsCategory.currentCategory == CategoriesFilms.TV_SERIES) {
            viewModel.pagedSeries?.onEach {
                allFilmAdapter.submitData(it)
            }?.launchIn(viewLifecycleOwner.lifecycleScope)

        } else {
            viewModel.pagedFilms?.onEach {
                allFilmAdapter.submitData(it)
            }?.launchIn(viewLifecycleOwner.lifecycleScope)
        }
        binding.allFilmsList.adapter = allFilmAdapter

    }

    private fun onClickFilm(filmId: Int) {
        val action = FragmentAllFilmsDirections.actionFragmentAllFilmsToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }
}