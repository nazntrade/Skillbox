package com.example.hw_networking.movies

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_networking.R
import com.example.hw_networking.adapter.MovieAdapter
import com.example.hw_networking.databinding.FragmentMainSearchMovieBinding

class MovieMainFragment : Fragment(R.layout.fragment_main_search_movie) {

    private lateinit var binding: FragmentMainSearchMovieBinding
    private var movieAdapter: MovieAdapter? = null
    private val viewModel: MoviesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainSearchMovieBinding.bind(view)

        initDropDownMenu()
        init()
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.searchButton.setOnClickListener {
            val queryTitleText = binding.titleEditText.text.toString()

            viewModel.search(queryTitleText)
        }

        viewModel.movies.observe(viewLifecycleOwner) { movieAdapter?.items = it }
        viewModel.isLoading.observe(viewLifecycleOwner, ::doWhenLoad)
    }

    private fun doWhenLoad(isLoading: Boolean) {
        binding.titleEditText.isEnabled = isLoading.not()
        binding.yearEditText.isEnabled = isLoading.not()
        binding.menuType.isEnabled = isLoading.not()
        binding.searchButton.isEnabled = isLoading.not()
        binding.movieListRecyclerView.isVisible = isLoading.not()
        if (isLoading) {
            binding.progressBar.isGone = false
        } else binding.progressBar.isGone
    }

    private fun init() {
        movieAdapter = MovieAdapter { itemMovie -> navigation(itemMovie) }
        with(binding.movieListRecyclerView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
        }

    }

    private fun navigation(itemMovie: RemoteMovie) {
        val action =
            MovieMainFragmentDirections.actionSearchMovieFragmentToDetailScreenFragment(itemMovie)
        findNavController().navigate(action)
    }

    private fun initDropDownMenu() {
        val itemDropDownMenu = resources.getStringArray(R.array.items_dropDown_menu)
        val adapterDropDownMenu = ArrayAdapter(
            requireContext(),
            R.layout.fragment_item_drop_down_menu,
            itemDropDownMenu
        )
        binding.autoCompleteTextView.setAdapter(adapterDropDownMenu)
    }

}