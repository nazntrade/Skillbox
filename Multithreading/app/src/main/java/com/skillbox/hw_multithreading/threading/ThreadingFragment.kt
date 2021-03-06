package com.skillbox.hw_multithreading.threading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.skillbox.hw_multithreading.R
import com.skillbox.hw_multithreading.adapter.MovieAdapter
import com.skillbox.hw_multithreading.databinding.FragmentThreadingBinding

class ThreadingFragment : Fragment(R.layout.fragment_threading) {

    private lateinit var binding: FragmentThreadingBinding
    private var movieAdapter: MovieAdapter? = null
    private val viewModel: ThreadingViewModel by viewModels()
    private val handler = Looper.myLooper()?.let { Handler(it) }
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentThreadingBinding.bind(view)

        initList()

        binding.movieButton.setOnClickListener {
            viewModel.fetchMovie()
        }

        mSwipeRefreshLayout = binding.swipeRefreshLayout
        mSwipeRefreshLayout?.setOnRefreshListener {
            viewModel.fetchMovie()
        }

        observe()
    }

    private fun initList() {
        movieAdapter = MovieAdapter()
        with(binding.movieList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observe() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieAdapter?.submitList(movies.shuffled()) {
                binding.movieList.scrollToPosition(0)
            }

            mSwipeRefreshLayout?.isRefreshing = false

            handler?.postDelayed({
                Toast.makeText(context, getString(R.string.list_updated), Toast.LENGTH_SHORT).show()
            }, 1000)
        }
    }
}