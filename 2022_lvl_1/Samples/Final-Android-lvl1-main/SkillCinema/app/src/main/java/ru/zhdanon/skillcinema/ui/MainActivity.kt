package ru.zhdanon.skillcinema.ui

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.zhdanon.skillcinema.R
import ru.zhdanon.skillcinema.databinding.ActivityMainBinding
import ru.zhdanon.skillcinema.ui.intro.IntroFragment

const val TAG = "ru.zhdanon.skillcinema.ui"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHost.navController

        PreferenceManager.getDefaultSharedPreferences(this).apply {
            if (!getBoolean(IntroFragment.PREFERENCES_NAME, false)) {
                navController.navigate(R.id.introFragment)
            } else {
                navController.navigate(R.id.mainFragment)
            }
        }
    }
}