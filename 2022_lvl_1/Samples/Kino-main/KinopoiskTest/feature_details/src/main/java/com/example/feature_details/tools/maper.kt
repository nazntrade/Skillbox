package com.example.feature_details.tools

import com.example.core.tools.SIZE_LIST_VIEW
import com.example.core.tools.base_model.films.BaseFilm
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.createListForView
import com.example.feature_database.entity.FilmEntity
import com.example.feature_details.data.ImageCategory
import com.example.feature_details.data.detailsFilm_page.dto.GalleryDTO
import com.example.feature_details.data.detailsFilm_page.dto.InfoFilmDTO
import com.example.adapterdelegate.data.SimilarFilmsDTO
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.BaseGallery
import com.example.core.tools.base_model.FullGalleryImage
import com.example.core.tools.base_model.category.GriDGallery
import com.example.feature_details.data.detailsFilm_page.model.DescriptionFilms
import com.example.feature_details.data.detailsFilm_page.model.PosterFilm
import com.example.feature_details.data.detailsFilm_page.response.ResponseFilmGallery
import com.example.feature_details.data.details_staff.BestFilm
import com.example.feature_details.data.details_staff.Film
import com.example.feature_details.data.details_staff.FilmView
import com.example.feature_details.data.season.ResponseSerialsDTO
import com.example.feature_details.data.season.SeasonsItem
import com.example.feature_details.domain.repository_ipl.FilmUseCase
import com.example.feature_details.presentation.staff_details.viewModel.StaffInfoViewModel


fun InfoFilmDTO.toPosterFilms() =
    PosterFilm(
        kinopoiskId, countries, genres, nameRu, nameOriginal, posterUrl,
        ratingAgeLimits, year, filmLength, ratingKinopoisk, shortDescription, description,
        isFavorite, isLike, isLook
    )

fun InfoFilmDTO.toDescriptions() =
    DescriptionFilms(CategoryInfo.GALLERY, shortDescription, description)

fun InfoFilmDTO.toBestFilms() =
    BestFilm(nameRu, posterUrl, genres, ratingKinopoisk.toString(), kinopoiskId)

fun InfoFilmDTO.toFilmEntity() =
    FilmEntity(nameRu, posterUrl, genres, ratingKinopoisk.toString(), kinopoiskId)

fun ResponseFilmGallery.toImageCategory(imageCategory: ImageCategory): ImageCategory {
    imageCategory.pages = totalPages
    imageCategory.total = total
    imageCategory.itemsList = items.toGridGalleryList()
    if (imageCategory.pages > 1) imageCategory.itemsList.add(GalleryDTO("Next", ""))
    return imageCategory
}

fun List<SimilarFilmsDTO>.toListBaseFilm(): List<BaseFilm> {
    val list = mutableListOf<BaseFilm>()
    this.forEach {
        list.add(BaseFilm(it.nameRu, it.posterUrlPreview, filmId = it.filmId))
    }
    return list.createListForView(SIZE_LIST_VIEW) as List<BaseFilm>

}

fun List<Film>.toListFilmsView(): MutableList<FilmView> {
    val list= mutableListOf<FilmView>()
    this.forEach{
        list.add(it.toFilmView())
    }
    return list
}

fun List<GalleryDTO>.toGridGalleryList(): MutableList<BaseGallery> {
    val list = mutableListOf<BaseGallery>()
    this.forEach {
       list.add( GriDGallery(it.imageUrl,it.previewUrl))
    }
    return list
}

suspend fun List<Film>.getBeastFilms(filmUseCase: FilmUseCase): MutableList<BestFilm> {
    val bestListFilms = if (this.size > StaffInfoViewModel.SIZE_BEST_LIST)
        this.sortedByDescending { it.rating }.slice(0 until StaffInfoViewModel.SIZE_BEST_LIST)
    else this
    val bestFilmsInfo = mutableListOf<BestFilm>()
    if (bestListFilms.isNotEmpty()) bestListFilms.forEach {
        bestFilmsInfo.add(filmUseCase.getFilmByID(it.filmId).toBestFilms())
    }
    return bestFilmsInfo
}

fun List <NestedInfoInCategory>.toFullGalleryImage(): MutableList<NestedInfoInCategory> {
    val list = mutableListOf<NestedInfoInCategory>()
    this.forEach{
        if (it is BaseGallery)
            list.add(FullGalleryImage(it.imageUrl,it.previewUrl))
    }
    return list
}

fun ResponseSerialsDTO.toSeasonsItem()=
    SeasonsItem(CategoryInfo.TV_SERIES,this.items)
