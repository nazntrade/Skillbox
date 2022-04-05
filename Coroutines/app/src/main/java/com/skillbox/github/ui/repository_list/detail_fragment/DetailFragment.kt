package com.skillbox.github.ui.repository_list.detail_fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.skillbox.github.R
import com.skillbox.github.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailFragmentViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private var haveStar = false
    private var error = ""
    private var onFailure = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentDetailBinding.bind(requireView())
        val loginOwner = args.itemRepo.owner.login
        val repoName = args.itemRepo.name

        showInfoCurrentRepo()
        checkStar(loginOwner, repoName)
        giveOrTakeAwayStar(loginOwner, repoName)
    }

    private fun giveOrTakeAwayStar(owner: String, repo: String) {
        binding.starImageView.setOnClickListener {
            if (!haveStar) {
                viewModel.giveStarViewModel(owner, repo)
                observeHaveStar()
                binding.starImageView.load(R.drawable.star_full)
                observeOnFailure()
            } else {
                viewModel.takeAwayStarViewModel(owner, repo)
                observeHaveStar()
                binding.starImageView.load(R.drawable.star_empty)
                observeOnFailure()
            }
        }
    }

    private fun observeHaveStar() {
        viewModel.haveStarFromViewModel.observe(viewLifecycleOwner) {
            haveStar = it
        }
    }

    private fun observeOnFailure() {
        viewModel.onFailureFromViewModel.observe(viewLifecycleOwner) { it ->
            onFailure = it
            if (onFailure) {
                viewModel.errorFromViewModel.observe(viewLifecycleOwner) {
                    error = it
                }
                toastErrors(error)
            }
        }
    }

    private fun checkStar(owner: String, repo: String) {
        viewModel.checkStarViewModel(owner, repo)
        viewModel.haveStarFromViewModel.observe(viewLifecycleOwner) {
            haveStar = it
            if (haveStar) {
                binding.starImageView.load(R.drawable.star_full)
            } else {
                binding.starImageView.load(R.drawable.star_empty)
            }
        }
        observeOnFailure()
    }

    private fun showInfoCurrentRepo() {
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

    private fun toastErrors(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}