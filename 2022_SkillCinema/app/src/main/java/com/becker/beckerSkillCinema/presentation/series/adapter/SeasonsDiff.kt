package com.becker.beckerSkillCinema.presentation.series.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.becker.beckerSkillCinema.data.seasons.Episode

class SeasonsDiff : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
        oldItem.episodeNumber == newItem.episodeNumber

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Episode,
        newItem: Episode
    ) = oldItem == newItem
}