package com.example.adapterdelegate.data

import com.example.core.tools.base_model.Staff

class StaffFromFilms(
    override val description: String?,
    override val nameRu: String,
    override val posterUrl: String,
    override val professionText: String,
    override val staffId: Int
): Staff(description, nameRu, posterUrl, professionText, staffId)