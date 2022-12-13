package com.becker.beckerSkillCinema.presentation.onBoarding

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.OnBoardingResources
import com.becker.beckerSkillCinema.databinding.FragmentOnboardingBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.onBoarding.adapter.PagerAdapter
import com.becker.beckerSkillCinema.utils.Constants.SHARED_PREFS_NAME
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment :
    ViewBindingFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private lateinit var adapter: PagerAdapter

    //I intercept the back button and exit the application
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

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
        adapter = PagerAdapter(listOnboarding)
        binding.onboardingViewpager.adapter = adapter
        TabLayoutMediator(binding.tab, binding.onboardingViewpager) { _, _ -> }.attach()

        binding.btnOnboardingSkip.setOnClickListener { onBoardingFinished() }

    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("FirstRun", false)
        editor.apply()
        findNavController().navigate(R.id.action_onBoardingFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findNavController().clearBackStack(R.id.onBoardingFragment)
    }
}
