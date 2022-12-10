package com.example.core.tools.adapter.delegate

import android.transition.TransitionManager
import com.example.core.databinding.ItemDescriptionBinding
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.DescriptionsCategory
import com.example.core.tools.general.InfoFilms
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun delegateDescription() =
    adapterDelegateViewBinding<DescriptionsCategory, BaseCategory, ItemDescriptionBinding>({ layoutInflater, root ->
        ItemDescriptionBinding.inflate(layoutInflater, root, false)
    }) {

        var isCollapsed = false

        bind {
            binding.apply {
                TransitionManager.beginDelayedTransition(binding.animLayout)
                binding.shortDescription.text = item.shortDescription
                binding.description.text = item.description
            }
            binding.description.setOnClickListener {
                if (isCollapsed) {
                    binding.description.maxLines = 3
                } else {
                    TransitionManager.beginDelayedTransition(binding.animLayout)
                    binding.description.maxLines = Int.MAX_VALUE
                }
                isCollapsed = !isCollapsed

            }
        }

    }