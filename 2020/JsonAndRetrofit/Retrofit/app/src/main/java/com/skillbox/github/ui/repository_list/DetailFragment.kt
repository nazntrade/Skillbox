package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.skillbox.github.R
import com.skillbox.github.data.Networking
import com.skillbox.github.databinding.FragmentDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private var haveStar: Boolean = false

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
                Networking.gitHubApi.giveStar(owner, repo).enqueue(
                    object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                binding.starImageView.load(R.drawable.star_full)
                                haveStar = true
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            toastErrors("$t")
                        }
                    }
                )
            } else {
                Networking.gitHubApi.takeAwayStar(owner, repo).enqueue(
                    object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                binding.starImageView.load(R.drawable.star_empty)
                                haveStar = false
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            toastErrors("$t")
                        }
                    }
                )
            }
        }
    }

    private fun checkStar(owner: String, repo: String) {
        Networking.gitHubApi.checkStar(owner, repo).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        haveStar = if (response.code() == 204) {
                            binding.starImageView.load(R.drawable.star_full)
                            true
                        } else {
                            binding.starImageView.load(R.drawable.star_empty)
                            false
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    toastErrors("$t")
                }
            }
        )
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