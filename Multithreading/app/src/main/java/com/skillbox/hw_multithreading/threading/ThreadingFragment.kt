package com.skillbox.hw_multithreading.threading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.hw_multithreading.R
import com.skillbox.hw_multithreading.adapter.MovieAdapter
import com.skillbox.hw_multithreading.databinding.FragmentThreadingBinding
import com.skillbox.hw_multithreading.networking.Network

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    private lateinit var binding: FragmentThreadingBinding
    private var movieAdapter: MovieAdapter? = null
    private val viewModel: ThreadingViewModel by viewModels()

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
            //setHasFixedSize(true)
        }
    }

    private fun getMovie() {
        viewModel.getMovie()
//        movieIds.mapNotNull { movieId ->
//            Network.getMovieById(movieId)
//        }
    }
}