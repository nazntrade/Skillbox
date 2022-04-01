package com.skillbox.github.ui.repository_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.skillbox.github.R
import com.skillbox.github.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentDetailBinding.bind(requireView())

        "id: ${args.itemRepo.id}".also { binding.repoDetailIdTextView.text = it }
        "Repository: ${args.itemRepo.name}".also { binding.repoDetailNameTextView.text = it }
        "Owner: ${args.itemRepo.owner.login}".also {
            binding.repoDetailOwnerLoginTextView.text = it
        }
        binding.repoDetailOwnerAvatarImageView.load(args.itemRepo.owner.avatar_url) {
            error(R.drawable.ic_404)
            placeholder(R.drawable.loading)
        }
    }
}