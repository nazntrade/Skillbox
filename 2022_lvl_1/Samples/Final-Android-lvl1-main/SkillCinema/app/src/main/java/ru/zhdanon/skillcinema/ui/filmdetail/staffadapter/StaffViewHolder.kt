package ru.zhdanon.skillcinema.ui.filmdetail.staffadapter

import androidx.recyclerview.widget.RecyclerView
import ru.zhdanon.skillcinema.app.loadImage
import ru.zhdanon.skillcinema.data.staffbyfilmid.ResponseStaffByFilmId
import ru.zhdanon.skillcinema.databinding.ItemStaffDetailFilmBinding

class StaffViewHolder(
    private val binding: ItemStaffDetailFilmBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: ResponseStaffByFilmId, clickActor: (actor: ResponseStaffByFilmId) -> Unit) {
        binding.apply {
            actorAvatarFilmDetail.loadImage(item.posterUrl)
            actorNameFilmDetail.text = item.nameRu ?: item.nameEn ?: "Не указан"
            actorRoleFilmDetail.text = item.description ?: "Не указан"
        }
        binding.root.setOnClickListener { clickActor(item) }
    }
}