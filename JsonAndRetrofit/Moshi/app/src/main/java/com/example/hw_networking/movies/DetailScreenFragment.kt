package com.example.hw_networking.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.hw_networking.R
import com.example.hw_networking.databinding.FragmentDetailScreenBinding
import com.squareup.moshi.Moshi

class DetailScreenFragment : Fragment(R.layout.fragment_detail_screen) {

    private lateinit var binding: FragmentDetailScreenBinding
    private val args: DetailScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailScreenBinding.bind(view)
        binding.posterDetailImageView.load(args.itemMovie.poster) {
            error(R.drawable.ic_404)
            placeholder(R.drawable.loading)
        }
        binding.titleDetailTextView.text = args.itemMovie.title
        binding.yearDetailTextView.text = args.itemMovie.year
        binding.plotTextView.text = args.itemMovie.plot
        binding.typeDetailTextView.text = args.itemMovie.type
        likeIncreasesScore()
    }

    private fun likeIncreasesScore() {
        binding.likeImageButton.setOnClickListener {
            Log.d("movie", "BEFORE like Movie ${args.itemMovie}")
            args.itemMovie.ratings = mapOf("NazzzRating" to "100000")

            movieToJson()

            binding.likeImageButton.isGone = true
            Toast.makeText(
                requireContext(),
                "your rating ${args.itemMovie.ratings} has been sent",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun movieToJson() {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(RemoteMovie::class.java).nonNull()
        val scoredMoviesJson = adapter.toJson(args.itemMovie)
        Log.d("movie", "Json AFTER like Movie $scoredMoviesJson")
    }
}