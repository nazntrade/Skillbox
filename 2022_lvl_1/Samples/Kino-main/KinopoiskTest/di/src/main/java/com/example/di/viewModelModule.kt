package com.example.di

import com.example.feature_details.presentation.filmography.viewModel.FilmographyListViewModel
import com.example.feature_details.presentation.filmography.viewModel.FilmographyViewModel
import com.example.feature_details.presentation.films_details.bottom_sheet.BottomSheetViewModel
import com.example.feature_details.presentation.gallery.allgallery.ListGalleryViewModel
import com.example.homepage.presentation.homepage.presentation.home_fragment.HomeViewModel
import com.example.homepage.presentation.homepage.presentation.category_fragment.CategoryPageViewModel
import com.example.feature_details.presentation.films_details.details.FilmInfoViewModel
import com.example.feature_details.presentation.gallery.gallery_in_category.GalleryViewModel
import com.example.feature_details.presentation.staff_details.viewModel.StaffInfoViewModel
import com.example.feature_details.presentation.seasons.SeasonsListViewModel
import com.example.feature_details.presentation.seasons.SeasonsViewModel
import com.example.presentation_profile.ProfileViewModel
import com.example.screen_listpage.presentation.setting.SettingsSearchViewModel
import com.example.screen_listpage.presentation.search.SearchStartViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {

    viewModel {
        HomeViewModel(get(), get(), get(), get())
    }

    viewModel {
        CategoryPageViewModel(get(), get())
    }

    viewModel {
        FilmInfoViewModel(get(), get(), get(), get())
    }

    viewModel {
        StaffInfoViewModel(get(), get(), get())
    }

    viewModel {
        ListGalleryViewModel(get(), get())
    }

    viewModel {
        GalleryViewModel(get(), get())
    }

    viewModel {
        FilmographyListViewModel(get())
    }

    viewModel {
        FilmographyViewModel(get(), get())
    }

    viewModel {
        SearchStartViewModel(get(), get(),get())
    }

    viewModel {
        BottomSheetViewModel(get(), get())
    }
    viewModel {
        ProfileViewModel(get(),get())
    }
    viewModel {
        SettingsSearchViewModel(get())
    }

    viewModel {
        SeasonsViewModel(get())
    }
    viewModel {
        SeasonsListViewModel(get())
    }
}
