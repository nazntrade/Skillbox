package com.becker.beckerSkillCinema.presentation.filmDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.becker.beckerSkillCinema.databinding.FragmentAllFilmsBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.allFilmByCategory.FragmentAllFilms

class FragmentSimilarFilms :
    ViewBindingFragment<FragmentAllFilmsBinding>(FragmentAllFilmsBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


//    private var _binding: FragmentAllFilmsBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: CinemaViewModel by activityViewModels()
//    private lateinit var adapter: FilmAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentAllFilmsBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.allFilmsCategoryTv.text = resources.getString(R.string.label_film_similar)
//        binding.allFilmsToHomeBtn.setOnClickListener { requireActivity().onBackPressed() }
//
//        setAdapter()                // Установка адаптера
//        setFilmList()               // Установка списка фильмов
//    }
//
//    private fun setAdapter() {
//        adapter = FilmAdapter(20, {}) { onClickFilm(it) }
//        binding.allFilmsList.layoutManager =
//            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
//        binding.allFilmsList.adapter = adapter
//    }
//
//    private fun setFilmList() {
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.currentFilmSimilar.collect {
//                adapter.submitList(it)
//            }
//        }
//    }
//
//    private fun onClickFilm(filmId: Int) {
//        val action =
//            FragmentSimilarFilmsDirections.actionFragmentSimilarFilmsToFragmentFilmDetail(filmId)
//        findNavController().navigate(action)
//    }
//
}