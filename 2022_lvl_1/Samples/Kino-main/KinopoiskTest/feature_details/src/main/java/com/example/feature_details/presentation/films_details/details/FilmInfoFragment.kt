package com.example.feature_details.presentation.films_details.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.R
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.adapter.adapters.CategoryAdapterBase
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.example.core.tools.extensions.*
import com.example.feature_details.data.ButtonPoster
import com.example.feature_details.data.detailsFilm_page.model.PosterFilm
import com.example.feature_details.databinding.FragmentFilmInfoBinding
import com.example.feature_details.tools.createGalleryDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmInfoFragment : BaseFragment<FragmentFilmInfoBinding>() {

    override fun initBinding(inflater: LayoutInflater) = FragmentFilmInfoBinding.inflate(inflater)

    private val viewModel by viewModel<FilmInfoViewModel>()

    private val adapter by lazy {
        CategoryAdapterBase({ onClickItem(it) }, { onClickAll(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.popBackStack()

        viewModel.getFilmForID()

        observeLoadState(viewModel.loadState, binding.newPoster.loading) {}

        observeFilm()
        onClickStatusButton(binding.newPoster.like, ButtonPoster.LIKE)
        onClickStatusButton(binding.newPoster.favorite, ButtonPoster.FAVORITE)
        onClickStatusButton(binding.newPoster.look, ButtonPoster.LOOK)
        observeFilmStatus()
    }

    private fun observeFilmStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filmStatus.collect {
                binding.newPoster.like.isSelected = it.isLike
                binding.newPoster.favorite.isSelected = it.isFavorite
                binding.newPoster.look.isSelected = it.isLook
            }
        }
    }

    private fun observeFilm() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.film.collect {
                setPoster(it.first)
                adapter.items = it.second
                binding.detailRecyclerView.adapter = adapter
            }
        }
    }

    private fun onClickStatusButton(view: ImageView, statusButton: ButtonPoster) {
        view.setOnClickListener { button ->
            button.isSelected = !button.isSelected
            viewModel.workDatabase(statusButton)
        }
    }

    private fun setPoster(item: PosterFilm) {
        binding.newPoster.apply {
            poster.glide(item.posterUrl)
            info.text =
                item.createInfoText(item.genres.createName(), item.countries.createName())

            binding.categoryTitle.text = item.nameOriginal

            binding.newPoster.menu.setOnClickListener {
                viewModel.navigateToBottomSheet()
                findNavController().navigate(R.id.action_filmInfoFragment_to_bottomSheetMenuFragment)
            }

            share.clickAndShowShareDialog(item.posterUrl)

        }
    }

    private fun onClickItem(categoryInfo: PageCategory) {

        when (categoryInfo.category) {
            CategoryInfo.STAFF -> {
                viewModel.navigateToStaffInfo(categoryInfo)
                findNavController().navigate(R.id.action_filmInfoFragment_to_staffInfoFragment)
            }
            CategoryInfo.ACTOR -> {
                viewModel.navigateToStaffInfo(categoryInfo)
                findNavController().navigate(R.id.action_filmInfoFragment_to_staffInfoFragment)
            }
            CategoryInfo.TV_SERIES->{
                viewModel.navigateToSeasonsList(categoryInfo)
                findNavController().navigate(R.id.action_filmInfoFragment_to_seasonsListFragment)
            }
            CategoryInfo.GALLERY -> createGalleryDialog(categoryInfo)
            CategoryInfo.SIMILAR -> { viewModel.getFilmForID(categoryInfo.query?.id) }

            else -> {}
        }

    }

    private fun onClickAll(categoryInfo: BaseCategory) {

        if (categoryInfo.category == CategoryInfo.GALLERY){
            viewModel.navigateToGalleryList()
            findNavController().navigate(R.id.action_filmInfoFragment_to_galleryFragment)
        }
        else navigateToCategory(categoryInfo)

    }

    private fun navigateToCategory(categoryInfo: BaseCategory) {
        viewModel.navigateToCategory(categoryInfo)
        findNavController().navigate(R.id.action_filmInfoFragment_to_categoryPageFragment)
    }
}