package com.becker.beckerSkillCinema.presentation.onBoarding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.ListOnBoarding
import com.becker.beckerSkillCinema.databinding.FragmentOnboardingBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.onBoarding.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment : ViewBindingFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val items  = listOf(
                ListOnBoarding("Узнавай\nо премьерах", R.drawable.start_image),
                ListOnBoarding("Создавай\nколлекцию ", R.drawable.two_image),
                ListOnBoarding("Делись\nс друзьями ", R.drawable.three_image),
            )

        binding.viewPager.adapter = PagerAdapter(items)
        binding.skipButton.setOnClickListener { findNavController().popBackStack() }

        TabLayoutMediator(binding.tab,binding.viewPager){ _,_-> }.attach()

    }
}