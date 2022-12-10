package com.example.feature_details.presentation.gallery.allgallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.tools.NAVIGATE__TO_GALLERY_LIST
import com.example.core.tools.all.LoadState
import com.example.core.tools.basefrahment.BaseViewModel
import com.example.feature_details.data.ImageCategory
import com.example.feature_details.domain.repository_ipl.GalleryUseCase
import com.example.feature_details.tools.toImageCategory
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ListGalleryViewModel(
    private val galleryUseCase: GalleryUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _items = MutableSharedFlow<List<ImageCategory>>()
    val items get() = _items.asSharedFlow()

    val id:Int? = savedStateHandle[NAVIGATE__TO_GALLERY_LIST]

    fun getGallery() {
        viewModelScope.launch {
            _loadState.emit(LoadState.LOADING)
            if (id!=null){
                val listGallery = mutableListOf<ImageCategory>()
                ImageCategory.values().forEach { imageCategory ->
                    val category =
                        galleryUseCase.getGallery(id, imageCategory.name, 1).toImageCategory(imageCategory)
                    if (category.itemsList.isNotEmpty()) listGallery.add(category)
                }
                savedStateHandle[Gallery_CATEGORY] = listGallery
                _items.emit(listGallery)
            }
            _loadState.emit(LoadState.SUCCESS)
        }
    }

    fun onClickCategory(position: Int) {
        savedStateHandle[Gallery_CATEGORY_POSITION] = position
        savedStateHandle[GALLERY_ID_FilM_IN] = id
    }

    companion object{
       const val Gallery_CATEGORY = "Gallery_CATEGORY"
       const val Gallery_CATEGORY_POSITION = "Gallery_CATEGORY_POSITION"
        const val GALLERY_ID_FilM_IN= "Gallery_id_Film"
    }

}