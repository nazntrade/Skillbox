package com.example.core.tools.category

import com.example.core.R
import com.example.core.tools.all.NestedInfoInCategory

class NestedCollection(
    val id: Int? = null,
    val nameCollection: String,
    var size: Int = 0,
    var icon:Int = R.drawable.ic_profile,
    var isDelete:Boolean = true
): NestedInfoInCategory