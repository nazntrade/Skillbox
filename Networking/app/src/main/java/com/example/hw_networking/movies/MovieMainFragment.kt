package com.example.hw_networking.movies

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hw_networking.R
import com.example.hw_networking.adapter.MovieAdapter
import com.example.hw_networking.databinding.FragmentMainSearchMovieBinding

class MovieMainFragment : Fragment(R.layout.fragment_main_search_movie) {

    private lateinit var binding: FragmentMainSearchMovieBinding
    private var movieAdapter: MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainSearchMovieBinding.bind(view)

        initDropDownMenu()
        init()

    }

    private fun init() {
        movieAdapter = MovieAdapter { imdbID -> navigation(imdbID) }

    }

    private fun navigation(imdbID: String) {
        val action = MovieMainFragmentDirections.actionSearchMovieFragmentToDetailScreenFragment(imdbID)
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