package com.becker.beckerSkillCinema.presentation.series.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.becker.beckerSkillCinema.data.seasons.Episode
import com.becker.beckerSkillCinema.databinding.ItemSeriesEpisodeBinding

class SeasonsAdapter : ListAdapter<Episode, SeasonsVewHolder>(SeasonsDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SeasonsVewHolder(
        ItemSeriesEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SeasonsVewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}