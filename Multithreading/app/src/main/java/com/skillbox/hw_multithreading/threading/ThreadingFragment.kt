package com.skillbox.hw_multithreading.threading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skillbox.hw_multithreading.R
import com.skillbox.hw_multithreading.adapter.MovieAdapter
import com.skillbox.hw_multithreading.databinding.FragmentThreadingBinding

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    lateinit var binding: FragmentThreadingBinding
    var movieAdapter: MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThreadingBinding.bind(view)
        initList()

    }

    private fun initList() {
        movieAdapter = MovieAdapter()
        with(binding.movieList) {
            adapter = movieAdapter
        }
    }
}