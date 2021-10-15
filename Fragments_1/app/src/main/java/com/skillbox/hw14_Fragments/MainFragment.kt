package com.skillbox.hw14_Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        addListFragment()
    }

    private fun addListFragment() {
        val listFragment = ListFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.containerMain, listFragment, "findListFragment")
//            .addToBackStack(null)
            .commit()
    }
}