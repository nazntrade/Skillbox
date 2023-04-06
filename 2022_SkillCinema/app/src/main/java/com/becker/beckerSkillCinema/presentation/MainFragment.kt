package com.becker.beckerSkillCinema.presentation

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext())
                    .setIcon(R.drawable.becker_cinema_icon)
                    .setTitle(getString(R.string.exit))
                    .setMessage(getString(R.string.are_you_really_want))
                    .setPositiveButton(getString(R.string.yes)) { _, _ -> requireActivity().finish() }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomNavigation()
    }

    //  BottomNavigationView
    //  https://m2.material.io/components/bottom-navigation/android#using-bottom-navigation
    private fun setBottomNavigation() {
        //to put(navigate) certain fragments in our container
        val navHost =
            childFragmentManager.findFragmentById(R.id.working_container) as NavHostFragment
        navController = navHost.navController

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fragmentVideo
                || destination.id == R.id.fragmentFilmDetail
                || destination.id == R.id.searchSettingsFragment
                || destination.id == R.id.searchFiltersFragment
                || destination.id == R.id.fragmentBigImage
                || destination.id == R.id.fragmentSearchDatePicker
            ) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}