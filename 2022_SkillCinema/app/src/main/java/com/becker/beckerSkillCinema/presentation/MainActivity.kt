package com.becker.beckerSkillCinema.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.ActivityMainBinding
import com.becker.beckerSkillCinema.utils.Constants.SHARED_PREFS_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigation in Activity
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController

//        lifecycleScope.launch(Dispatchers.IO) {
//            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch
//            sharedPref = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
//            try {
//                val firstRun = sharedPref.getBoolean("FirstRun", true)
//
//                if (firstRun) {
//                    navController.navigate(R.id.onBoardingFragment)
//                } else {
//                    navController.navigate(R.id.mainFragment)
//                }
//
//            } catch (t: Throwable) {
//
//            }

            PreferenceManager.getDefaultSharedPreferences(this).apply {
                if (!getBoolean("FirstRun", false)) {
                    navController.navigate(R.id.onBoardingFragment)
                } else {
                    navController.navigate(R.id.mainFragment)
                }
        }
    }
}

