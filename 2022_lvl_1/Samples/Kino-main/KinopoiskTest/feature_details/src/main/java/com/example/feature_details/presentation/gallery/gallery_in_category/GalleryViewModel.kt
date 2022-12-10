package com.example.feature_details.presentation.gallery.gallery_in_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.BaseGallery
import com.example.feature_details.data.ImageCategory
import com.example.feature_details.data.detailsFilm_page.dto.GalleryDTO
import com.example.feature_details.domain.repository_ipl.GalleryUseCase
import com.example.feature_details.presentation.gallery.allgallery.ListGalleryViewModel
import com.example.feature_details.presentation.gallery.allgallery.ListGalleryViewModel.Companion.GALLERY_ID_FilM_IN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val useCase: GalleryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var page = 1

    private val categoryList =
        savedStateHandle.get<List<ImageCategory>>(ListGalleryViewModel.Gallery_CATEGORY)
    private val position = savedStateHandle.get<Int>(ListGalleryViewModel.Gallery_CATEGORY_POSITION)
    private val filmId :Int? = savedStateHandle[GALLERY_ID_FilM_IN]

    private val category = position?.let { categoryList?.get(it) }

    private val _galleryList = MutableStateFlow<List<BaseGallery>>(emptyList())
    val galleryList = _galleryList.asStateFlow()

    fun getGallery() {
        page++
        viewModelScope.launch {
            if (category != null && filmId!=null) {
                category.itemsList.removeLastOrNull()
                val response = useCase.getGallery(filmId, category.name, page)
                category.itemsList.addAll(response.items)
                if (response.total < category.itemsList.size)
                    category.itemsList.add(GalleryDTO("Next", ""))
                if (category.itemsList.isNotEmpty())
                    _galleryList.value = category.itemsList
            }
        }

    }

    fun startList() = category!!.itemsList as List<NestedInfoInCategory>
}