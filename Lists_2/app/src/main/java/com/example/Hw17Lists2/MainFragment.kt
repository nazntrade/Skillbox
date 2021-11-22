package com.example.Hw17Lists2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.Hw17Lists2.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.articleButton.setOnClickListener {

        }

        binding.listButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, PetShopListFragment())
                ?.addToBackStack("PetShopListFragment")
                ?.commit()
        }
    }
}