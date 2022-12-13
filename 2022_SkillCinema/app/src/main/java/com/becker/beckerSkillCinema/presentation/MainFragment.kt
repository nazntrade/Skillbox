package com.becker.beckerSkillCinema.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentMainBinding

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    // I intercept the back button and exit the application
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost =
            childFragmentManager.findFragmentById(R.id.working_container) as NavHostFragment
        navController = navHost.navController

        setBottomNavigation()

    }

    private fun setBottomNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.fragmentHome)
                    true
                }
                R.id.search -> {
                    navController.navigate(R.id.fragmentSearch)
                    true
                }
                else -> {
                    navController.navigate(R.id.fragmentProfile)
                    true
                }
            }
        }
    }


}