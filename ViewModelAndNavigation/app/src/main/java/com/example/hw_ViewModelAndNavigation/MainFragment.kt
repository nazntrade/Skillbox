package com.example.hw_ViewModelAndNavigation

import com.example.hw_ViewModelAndNavigation.petShop.PetShopListFragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hw_ViewModelAndNavigation.imagesLists.StaggeredGridImageListFragment
import com.example.hw_ViewModelAndNavigation.articles.ViewPagerFragment
import com.example.hw_ViewModelAndNavigation.databinding.FragmentMainBinding
import com.example.hw_ViewModelAndNavigation.imagesLists.GridImageListFragment
import com.example.hw_ViewModelAndNavigation.imagesLists.HorizontalImageListFragment

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