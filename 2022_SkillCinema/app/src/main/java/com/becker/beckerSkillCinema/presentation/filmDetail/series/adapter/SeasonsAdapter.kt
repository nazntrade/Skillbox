package com.becker.beckerSkillCinema.presentation.filmDetail.series.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.seasons.Episode
import com.becker.beckerSkillCinema.databinding.ItemSeriesEpisodeBinding

class SeasonsAdapter : ListAdapter<Episode, SeasonsAdapter.SeasonsVewHolder>(SeasonsDiff()) {

    class SeasonsDiff : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem.episodeNumber == newItem.episodeNumber

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SeasonsVewHolder(
        ItemSeriesEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SeasonsVewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    class SeasonsVewHolder(
        private val binding: ItemSeriesEpisodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isClicked = false

        fun bindItem(item: Episode) {
            val text = binding.root.resources.getString(
                R.string.season_episode_name,
                item.episodeNumber,
                item.nameRu ?: item.nameEn ?: "Нет данных"
            )
            binding.episodeNumberName.text = text
            binding.episodeDate.text = formatReleaseDate(item.releaseDate)
            if (item.synopsis != null) {
                binding.episodeBtn.isVisible = true
                binding.episodeDescription.text = item.synopsis
                binding.episodeBtn.setOnClickListener {
                    binding.episodeDescription.isVisible = !isClicked
                    isClicked = !isClicked
                    binding.episodeBtn.setImageResource(
                        if (isClicked) R.drawable.ic_arrow_up
                        else R.drawable.ic_arrow_down
                    )
                }
            }
        }

        private fun formatReleaseDate(date: String?): String {
            if (date != null) {
                val tempData = date.split("-").reversed().toMutableList()
                tempData[1] =
                    when (tempData[1]) {
                        "01" -> "января"
                        "02" -> "февраля"
                        "03" -> "марта"
                        "04" -> "апреля"
                        "05" -> "мая"
                        "06" -> "июня"
                        "07" -> "июля"
                        "08" -> "августа"
                        "09" -> "сентября"
                        "10" -> "октября"
                        "11" -> "ноября"
                        "12" -> "декабря"
                        else -> ""
                    }
                return tempData.joinToString(" ")
            } else return ""
        }
    }
}