package com.example.feature_details.presentation.gallery.gallery_in_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.adapter.adapters.NestedAdapterBase
import com.example.core.tools.base_model.category.PageCategory
import com.example.feature_details.databinding.FragmentRecyclerBinding
import com.example.feature_details.tools.createGalleryDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : BaseFragment<FragmentRecyclerBinding>() {
    override fun initBinding(inflater: LayoutInflater) =
        FragmentRecyclerBinding.inflate(inflater)

    private val viewModel by viewModel<GalleryViewModel>()

    private val adapter by lazy { NestedAdapterBase({onClickItem(it)},{})  }
    private var lastPosition = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.filmsRecyclerView.layoutManager = setGridLayoutManager()

        adapter.items = viewModel.startList()
        lastPosition = viewModel.startList().lastIndex

        binding.filmsRecyclerView.adapter = adapter

    }

    private fun setGridLayoutManager() = GridLayoutManager(requireContext(), 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = if (position % 3 == 0) 2 else 1
        }
    }

    private fun onClickItem(pageCategory: PageCategory){
        if (pageCategory.query?.id ==-1) viewModel.getGallery()
        else createGalleryDialog(pageCategory)
    }


    private fun observe() =
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.galleryList.collect {
                adapter.items = it
                binding.filmsRecyclerView
                    .adapter?.notifyItemRangeChanged(lastPosition, it.lastIndex)
                lastPosition = it.lastIndex
            }
        }
}