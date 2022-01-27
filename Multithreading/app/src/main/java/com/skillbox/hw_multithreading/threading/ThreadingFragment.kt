package com.skillbox.hw_multithreading.threading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.hw_multithreading.R
import com.skillbox.hw_multithreading.adapter.MovieAdapter
import com.skillbox.hw_multithreading.databinding.FragmentThreadingBinding
import com.skillbox.hw_multithreading.networking.Network

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    private val movieIds = listOf(
        "tt0268978",
        "tt3783958",
        "tt4513678",
        "tt4154796"
    )

    private lateinit var binding: FragmentThreadingBinding
    private var movieAdapter: MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThreadingBinding.bind(view)

        initList()

        binding.movieButton.setOnClickListener {
            getMovie()
        }

        movieAdapter?.notifyDataSetChanged()
    }

    private fun initList() {
        movieAdapter = MovieAdapter()
        with(binding.movieList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun getMovie() {
        movieIds.mapNotNull { movieId ->
            Network.getMovieById(movieId)
        }
    }
}