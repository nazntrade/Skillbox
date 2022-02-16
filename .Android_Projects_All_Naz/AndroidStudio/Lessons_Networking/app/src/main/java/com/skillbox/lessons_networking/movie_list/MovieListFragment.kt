package com.skillbox.lessons_networking.movie_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.lessons_networking.R
import com.skillbox.lessons_networking.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieListFragment : Fragment(R.layout.fragment_movies) {

    private var movieAdapter: MovieListAdapter by autoCleared()

    private val viewModel: MovieListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
    }

    private fun initList() {
        movieAdapter = MovieListAdapter()
        with(moviesList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun bindViewModel() {
        searchButton.setOnClickListener {
            val queryText = searchEditText.text.toString()
            viewModel.search(queryText)
        }

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) { movieAdapter.items = it }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        moviesList.isVisible = isLoading.not()
        progressBar.isVisible = isLoading
        searchButton.isEnabled = isLoading.not()
    }
}