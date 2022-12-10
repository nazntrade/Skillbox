package com.example.core.tools.base_model

import com.example.core.tools.all.NestedInfoInCategory

abstract class Staff(
    open val description: String?,
    open val nameRu: String,
    open val posterUrl: String,
    open val professionText: String,
    override val staffId: Int
) : BaseStaff, NestedInfoInCategory