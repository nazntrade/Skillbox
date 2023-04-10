package com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.ItemStaffDetailFilmBinding
import com.becker.beckerSkillCinema.utils.loadImage
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId

class StaffAdapter(
    private val clickActor: (filmId: ResponseStaffByFilmId) -> Unit
) : ListAdapter<ResponseStaffByFilmId, StaffAdapter.StaffViewHolder>(DiffStaff()) {

    class DiffStaff : DiffUtil.ItemCallback<ResponseStaffByFilmId>() {
        override fun areItemsTheSame(
            oldItem: ResponseStaffByFilmId,
            newItem: ResponseStaffByFilmId
        ) = oldItem.staffId == newItem.staffId
                && oldItem.description == newItem.description
                && oldItem.professionText == newItem.professionText

        override fun areContentsTheSame(
            oldItem: ResponseStaffByFilmId,
            newItem: ResponseStaffByFilmId
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StaffViewHolder(
        ItemStaffDetailFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        holder.bindItem(getItem(position)) { clickActor(getItem(position)) }
    }

    class StaffViewHolder(
        private val binding: ItemStaffDetailFilmBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(
            item: ResponseStaffByFilmId,
            clickActor: (actor: ResponseStaffByFilmId) -> Unit
        ) {
            binding.apply {
                actorAvatarFilmDetail.loadImage(item.posterUrl)
                actorNameFilmDetail.text =
                    item.nameRu ?: item.nameEn ?: root.resources.getString(R.string.not_specified)
                actorRoleFilmDetail.text =
                    item.description ?: item.professionText
                            ?: root.resources.getString(R.string.not_specified)
            }
            binding.root.setOnClickListener { clickActor(item) }
        }
    }
}