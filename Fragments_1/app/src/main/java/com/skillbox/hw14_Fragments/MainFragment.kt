package com.skillbox.hw14_Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main), ItemSelectListener {

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        Toast.makeText(activity, "Attention. You are logged into the pentagon.", Toast.LENGTH_LONG)
            .show()

        addListFragment()
    }

    private fun addListFragment() {
        val listFragment = ListFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.containerMainFragment, listFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onItemSelect(text: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.containerMainFragment, DetailFragment.newInstance(text))
            .addToBackStack(null)
            .commit()
    }

}