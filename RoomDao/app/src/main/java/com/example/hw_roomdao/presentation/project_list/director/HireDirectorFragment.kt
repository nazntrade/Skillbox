package com.example.hw_roomdao.presentation.project_list.director

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hw_roomdao.R
import com.example.hw_roomdao.databinding.FragmentAddDirectorBinding

class HireDirectorFragment: Fragment(R.layout.fragment_add_director) {

    private lateinit var binding: FragmentAddDirectorBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddDirectorBinding.bind(view)

        initToolBar()
        saveDirector()
    }

    private fun initToolBar() {
        binding.appBar.toolBar.title = "Hire director"
    }

    private fun saveDirector(){
        binding.saveButton.setOnClickListener{
            
        }
    }
}