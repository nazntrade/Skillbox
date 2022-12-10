package ru.zhdanon.skillcinema.ui.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.zhdanon.skillcinema.data.OnBoardingResources
import ru.zhdanon.skillcinema.databinding.IntroItemPageBinding

class IntroPager(
    private val introList: List<OnBoardingResources>
) : RecyclerView.Adapter<IntroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IntroViewHolder(
        IntroItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.binding.apply {
            introImage.setImageResource(introList[position].imageId)
            introMessage.text = introList[position].message
        }
    }

    override fun getItemCount() = introList.size
}