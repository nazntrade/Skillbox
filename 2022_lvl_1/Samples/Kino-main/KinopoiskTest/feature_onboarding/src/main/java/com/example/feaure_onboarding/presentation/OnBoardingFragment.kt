package com.example.feaure_onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.core.tools.basefrahment.BaseFragment
import com.example.feaure_onboarding.data.ListOnBoarding
import com.example.feaure_onboarding.presentation.adapter.PagerAdapter
import com.example.feaure_onboarding.R
import com.example.feaure_onboarding.databinding.FragmentOnboardingBinding
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun initBinding(inflater: LayoutInflater) = FragmentOnboardingBinding.inflate(inflater)

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