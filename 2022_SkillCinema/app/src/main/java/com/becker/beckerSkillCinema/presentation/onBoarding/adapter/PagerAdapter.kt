package com.becker.beckerSkillCinema.presentation.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.OnBoardingResources
import com.becker.beckerSkillCinema.databinding.ItemOnboardingBinding

class PagerAdapter(
    private val items: List<OnBoardingResources>
) : RecyclerView.Adapter<OnBoardingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OnBoardingViewHolder(
        ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.binding.apply {
            introImage.setImageResource(items[position].imageId)
            introMessage.text = items[position].message
        }
    }

    override fun getItemCount() = items.size
}