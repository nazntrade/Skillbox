package com.example.feature_details.presentation.films_details.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.core.tools.adapter.adapters.CollectionAdapter
import com.example.core.tools.base_model.FilmsCollection
import com.example.core.tools.extensions.createAddDialog
import com.example.core.tools.extensions.glide
import com.example.feature_details.databinding.BottomSheetMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetMenuFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<BottomSheetViewModel>()

    private val adapter by lazy {
        CollectionAdapter { collection, isSelect ->
            onClick(collection, isSelect)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCollection()
        viewModel.getCollectionFilms()

        binding.addCategory.setOnClickListener {
            createAddDialog { name -> viewModel.addCollection(name) }
        }

        binding.poster.glide(viewModel.getFilm().posterUrlPreview)
        binding.filmsName.text = viewModel.getFilm().nameRu
        binding.genreName.text = viewModel.getFilm().genres.first().info
    }


    private fun onClick(collection: FilmsCollection, isSelect: Boolean) {
        viewModel.addFilmsInCollection(collection, isSelect)
    }

    private fun observeCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.collections.collect {
                adapter.items = it
                binding.detailRecyclerView.adapter = adapter
            }
        }
    }
}