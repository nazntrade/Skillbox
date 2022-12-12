package com.becker.beckerSkillCinema.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.utils.Constants.SHARED_PREFS_NAME

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController

        val sharedPref = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        if (sharedPref.getBoolean("Finished", true)) {
            navController.navigate(R.id.mainFragment)
        } else {
            navController.navigate(R.id.onBoardingFragment)
        }
    }
}

