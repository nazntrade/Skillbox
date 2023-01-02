package com.becker.beckerSkillCinema.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.navigation.fragment.NavHostFragment
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigation in Activity
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController

        PreferenceManager.getDefaultSharedPreferences(this).apply {
            if (!getBoolean("FirstRun", false)) {
                navController.navigate(R.id.onBoardingFragment)
            } else {
                navController.navigate(R.id.mainFragment)
            }
        }
    }
}

