package com.example.feaure_onboarding.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feaure_onboarding.data.ListOnBoarding
import com.example.feaure_onboarding.databinding.ItemOnboardingBinding

class OnBoardingViewHolder(private val binding: ItemOnboardingBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListOnBoarding) {
        binding.text.text = item.text
        Glide.with(binding.image.context)
            .load(item.id)
            .into(binding.image)
    }
}