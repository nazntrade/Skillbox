package com.example.core.tools.adapter.delegate

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.ItemCategoryBinding
import com.example.core.tools.GRID_ACTOR_SIZE
import com.example.core.tools.GRID_STAFF_SIZE
import com.example.core.tools.adapter.adapters.NestedAdapterBase
import com.example.core.tools.base_model.category.BaseCategory
import com.example.core.tools.base_model.category.BaseDetailsCategory
import com.example.core.tools.base_model.category.PageCategory
import com.example.core.tools.category.CategoryInfo
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun delegateBasCategory(
onClickItem: (category: PageCategory) -> Unit,
onClickAll: (category: BaseCategory) -> Unit,
) =
adapterDelegateViewBinding<BaseDetailsCategory, BaseCategory, ItemCategoryBinding>({ layoutInflater, root ->
    ItemCategoryBinding.inflate(layoutInflater, root, false)
}) {

    binding.buttonAll.setOnClickListener {
        onClickAll(item)
    }

    bind {
        binding.categoryName.text = item.category.text

        when (item.category) {
            CategoryInfo.ACTOR -> {
                binding.filmsRecyclerView.setGridLayoutManager(4,GridLayoutManager.HORIZONTAL)
                binding.buttonAll.checkVisibility(item.listValue.size,GRID_ACTOR_SIZE)
            }
            CategoryInfo.STAFF -> {
                binding.filmsRecyclerView.setGridLayoutManager(2,GridLayoutManager.HORIZONTAL)
                binding.buttonAll.checkVisibility(item.listValue.size, GRID_STAFF_SIZE)
            }
            CategoryInfo.COLLECTION->{
                binding.filmsRecyclerView.setGridLayoutManager(2,GridLayoutManager.HORIZONTAL)
                binding.buttonAll.isVisible =false
                binding.categoryName.text = "Коллекции"
            }
            else -> binding.filmsRecyclerView.layoutManager=
                LinearLayoutManager( binding.filmsRecyclerView.context,LinearLayoutManager.HORIZONTAL,false)
        }


        binding.filmsRecyclerView.adapter =
            NestedAdapterBase(
                { onClickItem(it) },
                { onClickAll(it) }).apply { items = item.listValue }
    }
}

fun RecyclerView.setGridLayoutManager(spanCount: Int, orientation: Int){
    this.layoutManager = GridLayoutManager(this.context, spanCount,orientation, false)
}

fun TextView.checkVisibility(listSize: Int, layoutSize: Int) {
    this.isVisible = listSize >= layoutSize
}