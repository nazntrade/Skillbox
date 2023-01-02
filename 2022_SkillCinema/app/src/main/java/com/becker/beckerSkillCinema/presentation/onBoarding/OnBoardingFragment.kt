package com.becker.beckerSkillCinema.presentation.onBoarding

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.OnBoardingResources
import com.becker.beckerSkillCinema.databinding.FragmentOnboardingBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.onBoarding.adapter.PagerAdapter
import com.becker.beckerSkillCinema.utils.Constants.SHARED_PREFS_NAME
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnBoardingFragment :
    ViewBindingFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private lateinit var adapter: PagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOnboarding = listOf(
            OnBoardingResources(
                message = resources.getString(R.string.onboarding_description_first),
                imageId = R.drawable.wfh_2
            ),
            OnBoardingResources(
                message = resources.getString(R.string.onboarding_description_second),
                imageId = R.drawable.wfh_4_1
            ), OnBoardingResources(
                message = resources.getString(R.string.onboarding_description_third),
                imageId = R.drawable.wfh_8
            )
        )

//       Creating ViewPager2 with TabLayout
//       https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2
//       https://go.skillbox.ru/education/course/android-dev-1/bf29e0a9-bd4e-4e07-bf34-62f468131ac0/videolesson

        adapter = PagerAdapter(listOnboarding)
        binding.onboardingViewpager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.onboardingViewpager) { _, _ -> }.attach()

        binding.btnOnboardingSkip.setOnClickListener { onBoardingFinished() }

    }

    private fun onBoardingFinished() {
        PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
            putBoolean("FirstRun", true)
            apply()
        }
        findNavController().navigate(R.id.mainFragment)
    }
}
