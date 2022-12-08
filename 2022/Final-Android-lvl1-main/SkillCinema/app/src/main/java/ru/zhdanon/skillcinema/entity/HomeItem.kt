package ru.zhdanon.skillcinema.entity

import ru.zhdanon.skillcinema.data.filmbyfilter.Genre

interface HomeItem {
    val filmId: Int
    val posterUrlPreview: String
    val nameRu: String?
    val rating: String?
    val genres: List<Genre>
}