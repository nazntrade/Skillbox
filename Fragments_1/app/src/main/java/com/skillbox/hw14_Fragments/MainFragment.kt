package com.skillbox.hw14_Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main), ItemSelectListener {

    private lateinit var binding: FragmentMainBinding

    override fun onItemSelect(text: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance(text))
            .commit()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        addListFragment()
    }

    private fun addListFragment() {
        val listFragment = ListFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.containerMain, listFragment, "findListFragment")
//            .addToBackStack(null)
            .commit()
    }
}