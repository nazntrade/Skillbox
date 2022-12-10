package com.example.homepage.presentation.homepage.data.films.dto

import com.example.core.tools.base_model.films.BaseEntityFilm
import com.example.core.tools.general.Genre

data class PopularDTO(
    override val filmId: Int,
    override val nameRu: String,
    override val genres: List<Genre>,
    override val posterUrlPreview: String,
    override val rating: String? = null,
) : BaseEntityFilm()