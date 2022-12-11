package com.becker.beckerSkillCinema.presentation.onBoarding.adapter

import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.ListOnBoarding
import com.becker.beckerSkillCinema.databinding.ItemOnboardingBinding
import com.bumptech.glide.Glide

class OnBoardingViewHolder(private val binding: ItemOnboardingBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListOnBoarding) {
        binding.text.text = item.text
        Glide.with(binding.image.context)
            .load(item.id)
            .into(binding.image)
    }
}