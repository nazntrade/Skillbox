package com.example.hw_networking

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.hw_networking.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        initDropDownMenu()

    }


    private fun initDropDownMenu() {
        val itemDropDownMenu = context?.resources?.getStringArray(R.array.items_dropDown_menu)
        val adapterDropDownMenu = ArrayAdapter(
            requireContext(),
            R.layout.fragment_drop_down_menu_item,
            itemDropDownMenu.orEmpty()
        )
        binding.autoCompleteTextView?.setAdapter(adapterDropDownMenu)
    }

}