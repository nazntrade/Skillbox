package com.example.hw_networking.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.hw_networking.R
import com.example.hw_networking.databinding.FragmentDetailScreenBinding

class DetailScreenFragment : Fragment(R.layout.fragment_detail_screen) {
    private val args = DetailScreenFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailScreenBinding.bind(view)
        binding.posterDetailImageView.load(args.itemMovie.poster) {
            error(R.drawable.ic_404)
            placeholder(R.drawable.loading)
        }
        binding.titleDetailTextView.text = args.itemMovie.title
        binding.plotDetailTextView.text = args.itemMovie.plot
    }

}