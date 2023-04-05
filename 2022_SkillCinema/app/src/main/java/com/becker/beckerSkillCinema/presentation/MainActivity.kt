package com.becker.beckerSkillCinema.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.ActivityMainBinding
import com.becker.beckerSkillCinema.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CategoriesFilms.initialize(applicationContext)

//        testFirebaseCrashlytics()

        defineFirstRun()
    }

    private fun defineFirstRun() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return
        val sharedPref = getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val firstRun = sharedPref.getBoolean(FIRST_RUN, true)
        if (firstRun) {
            navController.navigate(R.id.onBoardingFragment)
        } else {
            navController.navigate(R.id.mainFragment)
        }
    }

//    private fun testFirebaseCrashlytics() {
//        val crashButton = Button(this)
//        crashButton.text = "Test Crash"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//
//        addContentView(
//            crashButton, ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        )
//    }

    companion object {
        const val FIRST_RUN = "FirstRun"
    }
}