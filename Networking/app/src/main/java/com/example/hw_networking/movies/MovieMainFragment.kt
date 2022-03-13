package com.example.hw_networking.movies

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        activity?.window?.setBackgroundDrawableResource(R.drawable.alien_poster)

        initDropDownMenu()
        initList()
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.searchButton.setOnClickListener {
            search()
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter?.items = it
            errorProcessing()
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::doWhileLoadMovies)
    }

    private fun errorProcessing() {
        if (viewModel.getErrorFromServer == "JSONException") {
            showDialog(
                "Nothing found.",
                "Try other search options.",
                "Ok",
                null,
            )
            movieAdapter?.items = emptyList()
        }
        if (viewModel.getErrorFromServer == "IOException") {
            showDialog(
                "Oops, something went wrong.",
                "It looks like you have a connection problem. Reconnect and click RETRY.",
                "Retry",
                listenerPositive = { _, _ ->
                    search()
                }
            )
            movieAdapter?.items = emptyList()
        }
        if (viewModel.getErrorFromServer == "FailureServerResponse") {
            showDialog(
                "Oops, something went wrong.",
                "Seems to be a problem with the server.",
                "Retry",
                listenerPositive = { _, _ ->
                    search()
                }
            )
            movieAdapter?.items = emptyList()
        }
    }

    private fun search() {
        val queryTitleText = binding.titleEditText.text.toString()
        val queryYearText = binding.yearEditText.text.toString()
        val typeText = binding.autoCompleteTextView.text.toString()
        val queryTypeText = if (typeText == "all") {
            ""
        } else typeText
        viewModel.search(queryTitleText, queryYearText, queryTypeText)
    }

    private fun initDropDownMenu() {
        val itemDropDownMenu = resources.getStringArray(R.array.items_dropDown_menu)
        val adapterDropDownMenu = ArrayAdapter(
            requireContext(),
            R.layout.fragment_item_drop_down_menu,
            itemDropDownMenu,
        )
        binding.autoCompleteTextView.setAdapter(adapterDropDownMenu)
    }

    private fun initList() {
        movieAdapter = MovieAdapter { itemMovie -> navigation(itemMovie) }
        with(binding.movieListRecyclerView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun navigation(itemMovie: RemoteMovie) {
        val action =
            MovieMainFragmentDirections.actionSearchMovieFragmentToDetailScreenFragment(itemMovie)
        findNavController().navigate(action)
    }

    private fun doWhileLoadMovies(isLoading: Boolean) {
        binding.titleEditText.isEnabled = isLoading.not()
        binding.yearEditText.isEnabled = isLoading.not()
        binding.menuType.isEnabled = isLoading.not()
        binding.searchButton.isEnabled = isLoading.not()
        binding.movieListRecyclerView.isVisible = isLoading.not()
        binding.progressBar.isGone = isLoading.not()
    }

    private fun showDialog(
        title: String,
        message: String,
        textPositive: String,
        listenerPositive: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(textPositive, listenerPositive)
            .setNegativeButton("Close", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.decorView?.setBackgroundResource(android.R.color.background_light)
    }
}