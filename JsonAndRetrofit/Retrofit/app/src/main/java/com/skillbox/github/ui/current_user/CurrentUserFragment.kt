package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.load
import com.skillbox.github.R
import com.skillbox.github.data.Networking
import com.skillbox.github.databinding.FragmentCurrentUserBinding
import retrofit2.Call
import retrofit2.Response

class CurrentUserFragment : Fragment(R.layout.fragment_current_user) {

    private lateinit var binding: FragmentCurrentUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentUserBinding.bind(view)

        getCurrentData()
    }

    private fun getCurrentData() {
        val avatarUrlView = binding.currentUserAvatarImageView
        val loginView = binding.currentUserLoginTextView
        val idView = binding.currentUserIdTextView
        val nameView = binding.currentUserNameTextView
        val locationView = binding.currentUserLocationTextView

        Networking.gitHubApi.getDataCurrentUser()
            .enqueue(
                object : retrofit2.Callback<CurrentUser> {
                    override fun onResponse(
                        call: Call<CurrentUser>,
                        response: Response<CurrentUser>
                    ) {
                        if (response.isSuccessful) {
                            response.body()
                            avatarUrlView.load(response.body()?.avatar_url)
                            loginView.text = response.body()?.login
                            "id: ${response.body()?.id.toString()}".also { idView.text = it }
                            "Name: ${response.body()?.name}".also { nameView.text = it }
                            "Location: ${response.body()?.location}".also { locationView.text = it }
                        }
                    }

                    override fun onFailure(call: Call<CurrentUser>, t: Throwable) {
                        "Error. Something went wrong. $t".also { idView.text = it }
                    }
                }
            )
    }
}