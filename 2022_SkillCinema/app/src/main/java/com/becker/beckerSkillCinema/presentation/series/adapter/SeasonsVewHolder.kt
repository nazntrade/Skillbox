package com.becker.beckerSkillCinema.presentation.series.adapter

import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.seasons.Episode
import com.becker.beckerSkillCinema.databinding.ItemSeriesEpisodeBinding

class SeasonsVewHolder(
    private val binding: ItemSeriesEpisodeBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var isClicked = false

    fun bindItem(item: Episode) {
        val text = binding.root.resources.getString(
            R.string.season_episode_name, item.episodeNumber, item.nameRu ?: item.nameEn
        )
        binding.episodeNumberName.text = text
        binding.episodeDate.text = formatReleaseDate(item.releaseDate)
        if (item.synopsis != null) {
            binding.episodeDescription.text = item.synopsis
            binding.episodeBtn.setOnClickListener {
                binding.episodeDescription.isVisible = !isClicked
                isClicked = !isClicked
                binding.episodeBtn.setImageResource(
                    if (isClicked) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                )
            }
        }
    }

    private fun formatReleaseDate(date: String?): String {
//        Log.d(TAG, "formatReleaseDate: $date")
        if (date != null) {
            val temp1 = date.split("-").reversed().toMutableList()
            temp1[1] = when (temp1[1]) {
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
            return temp1.joinToString(" ")
        } else return ""
    }
}