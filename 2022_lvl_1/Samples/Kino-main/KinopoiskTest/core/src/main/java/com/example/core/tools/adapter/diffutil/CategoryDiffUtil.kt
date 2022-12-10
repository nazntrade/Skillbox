package com.example.core.tools.adapter.diffutil

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.core.tools.base_model.category.BaseCategory

class CategoryDiffUtil: DiffUtil.ItemCallback<BaseCategory>() {
    override fun areItemsTheSame(oldItem: BaseCategory, newItem: BaseCategory)=
        oldItem.category == newItem.category


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BaseCategory, newItem: BaseCategory)=
        oldItem == newItem
}