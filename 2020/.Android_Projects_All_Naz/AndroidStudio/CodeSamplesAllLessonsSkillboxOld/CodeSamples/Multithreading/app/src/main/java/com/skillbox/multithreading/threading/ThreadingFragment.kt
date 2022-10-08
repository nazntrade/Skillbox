package com.skillbox.multithreading.threading

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.skillbox.multithreading.R
import kotlinx.android.synthetic.main.fragment_threading.*

class ThreadingFragment: Fragment(R.layout.fragment_threading) {

    private val viewModel: ThreadingViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestMovies.setOnClickListener {
            viewModel.requestMovies()
        }
        viewModel.time.observe(viewLifecycleOwner) {
            Log.d("ThreadTest", "livedata changed on ${Thread.currentThread().name}")
            timeTextView.text = it.toString()
        }
        viewModel.movies.observe(viewLifecycleOwner) { moviesTextView.text = it }
    }

}