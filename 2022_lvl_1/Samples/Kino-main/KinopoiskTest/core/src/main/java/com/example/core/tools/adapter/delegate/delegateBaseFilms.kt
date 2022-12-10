package com.example.core.tools.adapter.delegate

import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.core.databinding.ItemFilmographyBinding
import com.example.core.databinding.ItemFilmsBinding
import com.example.core.databinding.ItemNextButtonBinding
import com.example.core.databinding.ItemStunBinding
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.all.Query
import com.example.core.tools.base_model.category.BaseDetailsCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.base_model.films.BaseFilm
import com.example.core.tools.base_model.films.FilmographyMove
import com.example.core.tools.base_model.imes.ItemNext
import com.example.core.tools.base_model.imes.ItemStun
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.createName
import com.example.core.tools.extensions.glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun delegateBaseFilms(
    onClickItem: (pageCategory: PageCategory) -> Unit,
) =
    adapterDelegateViewBinding<BaseFilm, NestedInfoInCategory, ItemFilmsBinding>({ layoutInflater, root ->
        ItemFilmsBinding.inflate(layoutInflater, root, false)
    }) {

        binding.poster.setOnClickListener {
            onClickItem(
                PageCategory(
                    CategoryInfo.SIMILAR, emptyList(),
                    Query(id = item.filmId)
                )
            )
        }

        bind {
            binding.apply {
                filmsName.text = item.nameRu
                genreName.text = item.genres.createName()
                poster.glide(item.posterUrlPreview)
                fgLook.isVisible = item.isLook
                iconLook.isVisible = item.isLook
                if (item.rating != null) {
                    rating.isInvisible = false
                    rating.text = item.rating
                } else rating.isInvisible = true
            }
        }
    }

fun delegateStun() =
    adapterDelegateViewBinding<ItemStun, NestedInfoInCategory, ItemStunBinding>({ layoutInflater, root ->
        ItemStunBinding.inflate(layoutInflater, root, false)
    }) {
    }

fun delegateNext(
    onClickAll: (category: BaseDetailsCategory) -> Unit,
) = adapterDelegateViewBinding<ItemNext, NestedInfoInCategory, ItemNextButtonBinding>({ layoutInflater, root ->
        ItemNextButtonBinding.inflate(layoutInflater, root, false)
    }) {
        binding.nextButton.setOnClickListener {
            onClickAll(PageCategory(CategoryInfo.SIMILAR, emptyList()))
        }
    }

fun filmographyDelegate(
    onclick: (category: PageCategory) -> Unit,
    onClickAll: (category: BaseDetailsCategory) -> Unit

) =
    adapterDelegateViewBinding<FilmographyMove, NestedInfoInCategory, ItemFilmographyBinding>({ layoutInflater, root ->
        ItemFilmographyBinding.inflate(layoutInflater, root, false)
    }) {

        binding.expand.setOnClickListener {
            onclick(PageCategory(
                CategoryInfo.PREMIERS, emptyList(), Query(id = absoluteAdapterPosition)))
            binding.poster.isVisible = !binding.poster.isVisible
        }

        binding.item.setOnClickListener{
            onClickAll(PageCategory(
                CategoryInfo.PREMIERS, emptyList(), Query(id = item.filmId)))
        }

        bind {

            binding.name.text = if (item.nameRu != null) item.nameRu
            else item.nameEn
            binding.rating.text = item.rating
            binding.description.text = item.description
            binding.poster.isVisible = item.isExpand
            if (item.posterURL != null)
                binding.poster.glide(item.posterURL!!)



        }
    }