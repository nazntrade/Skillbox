package com.becker.beckerSkillCinema.presentation.filmDetail.series.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.Months
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
                item.nameRu ?: item.nameEn ?: binding.root.resources.getString(R.string.NoData)
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
                        Months.JANUARY.number -> binding.root.resources.getString(R.string.ofJanuary)
                        Months.FEBRUARY.number -> binding.root.resources.getString(R.string.ofFebruary)
                        Months.MARCH.number -> binding.root.resources.getString(R.string.ofMarch)
                        Months.APRIL.number -> binding.root.resources.getString(R.string.ofApril)
                        Months.MAY.number -> binding.root.resources.getString(R.string.ofMay)
                        Months.JUNE.number -> binding.root.resources.getString(R.string.ofJune)
                        Months.JULY.number -> binding.root.resources.getString(R.string.ofJuly)
                        Months.AUGUST.number -> binding.root.resources.getString(R.string.ofAugust)
                        Months.SEPTEMBER.number -> binding.root.resources.getString(R.string.ofSeptember)
                        Months.OCTOBER.number -> binding.root.resources.getString(R.string.ofOctober)
                        Months.NOVEMBER.number -> binding.root.resources.getString(R.string.ofNovember)
                        Months.DECEMBER.number -> binding.root.resources.getString(R.string.ofDecember)
                        else -> ""
                    }
                return tempData.joinToString(" ")
            } else return ""
        }
    }
}