package com.example.feature_details.data

import com.example.core.tools.base_model.films.FilmographyMove

enum class FilmographyCategory(
    val nameCategory: String,
    var items: MutableList<FilmographyMove> = mutableListOf()
)  {
    WRITER("Писатель"),
    OPERATOR("Оператор"),
    EDITOR("Редактор"),
    COMPOSER("Композитор"),
    PRODUCER_USSR("Продюссер"),
    HIMSELF("Играет свм себя"),
    HERSELF("Играет саму себя"),
    HRONO_TITR_MALE("что?"),
    HRONO_TITR_FEMALE("что?"),
    TRANSLATOR("Переводчик"),
    DIRECTOR("Директор"),
    DESIGN("Дизайнер"),
    PRODUCER("Продюссер"),
    ACTOR("Актер"),
    VOICE_DIRECTOR("Звуко режесёр"),
    UNKNOWN("Остальное"),
    VOICE_FEMALE("Озвучка")
}