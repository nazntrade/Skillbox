package ru.zhdanon.skillcinema.ui.allfilmsbycategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import ru.zhdanon.skillcinema.data.CategoriesFilms
import ru.zhdanon.skillcinema.databinding.FragmentAllFilmsBinding
import ru.zhdanon.skillcinema.ui.CinemaViewModel
import ru.zhdanon.skillcinema.ui.allfilmsbycategory.allfilmadapter.AllFilmAdapter

class FragmentAllFilms : Fragment() {
    private var _binding: FragmentAllFilmsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFilmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allFilmsCategoryTv.text = viewModel.getCurrentCategory().text
        binding.allFilmsToHomeBtn.setOnClickListener { requireActivity().onBackPressed() }
        binding.loadingRefreshBtn.setOnClickListener { viewModel.getAllFilmAdapter().retry() }

        setAdapter()                // Установка адаптера
        setFilmList()               // Установка списка фильмов
    }

    private fun setAdapter() {
        viewModel.setAllFilmAdapter(AllFilmAdapter { onClickFilm(it) })

        binding.allFilmsList.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.allFilmsList.adapter = viewModel.getAllFilmAdapter()

        viewModel.getAllFilmAdapter().addLoadStateListener { state ->
            val currentState = state.refresh
            binding.allFilmsList.isVisible = currentState != LoadState.Loading
            binding.progressGroup.isVisible = currentState == LoadState.Loading
            binding.loadingRefreshBtn.isVisible = currentState != LoadState.Loading

            when (currentState) {
                is LoadState.Loading -> {
                    binding.allFilmsList.isVisible = false
                    binding.progressGroup.isVisible = true
                    binding.loadingRefreshBtn.isVisible = false
                }
                is LoadState.NotLoading -> {
                    binding.allFilmsList.isVisible = true
                    binding.progressGroup.isVisible = false
                    binding.loadingRefreshBtn.isVisible = true
                }
                else -> {
                    binding.allFilmsList.isVisible = false
                    binding.loadingProgress.isVisible = false
                    binding.loadingRefreshBtn.isVisible = true
                }
            }
        }
    }

    private fun setFilmList() {
        if (viewModel.getCurrentCategory() == CategoriesFilms.TV_SERIES) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.allSeries.collect {
                    viewModel.getAllFilmAdapter().submitData(it)
                }
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.allFilmsByCategory.collect {
                    viewModel.getAllFilmAdapter().submitData(it)
                }
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
        val action = FragmentAllFilmsDirections.actionFragmentAllFilmsToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}