package com.example.hw17Lists2

import com.example.hw17Lists2.petShop.PetShopListFragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hw17Lists2.imagesLists.StaggeredGridImageListFragment
import com.example.hw17Lists2.artikles.ViewPagerFragment
import com.example.hw17Lists2.databinding.FragmentMainBinding
import com.example.hw17Lists2.imagesLists.GridImageListFragment
import com.example.hw17Lists2.imagesLists.HorizontalImageListFragment

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.articleButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, ViewPagerFragment())
                ?.addToBackStack("ViewPagerFragment")
                ?.commit()
        }

        binding.listButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, PetShopListFragment())
                ?.addToBackStack("PetShopListFragment")
                ?.commit()
        }

        binding.buttonHorizontal.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, HorizontalImageListFragment())
                ?.addToBackStack("HorizontalImageListFragment")
                ?.commit()
        }

        binding.buttonStaggeredGrid.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, StaggeredGridImageListFragment())
                ?.addToBackStack("StaggeredGridImageListFragment")
                ?.commit()
        }

        binding.buttonGrid.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, GridImageListFragment())
                ?.addToBackStack("GridImageListFragment")
                ?.commit()

        }
    }
}