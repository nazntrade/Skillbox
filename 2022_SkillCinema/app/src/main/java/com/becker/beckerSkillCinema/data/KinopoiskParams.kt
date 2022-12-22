package com.becker.beckerSkillCinema.data

val TOP_TYPES = mapOf(
    CategoriesFilms.BIOGRAPHY to "FILM",
    CategoriesFilms.SCIENCE_FICTION to "FILM",
    CategoriesFilms.CARTOONS to "FILM",
    CategoriesFilms.BEST_250 to "TOP_250_BEST_FILMS",
    CategoriesFilms.POPULAR_100 to "TOP_100_POPULAR_FILMS",
    CategoriesFilms.MOST_AWAIT to "TOP_AWAIT_FILMS",
    CategoriesFilms.TV_SERIES to "TV_SERIES"
)

val GENRE_INT_FOR_FILTER = mapOf(
    CategoriesFilms.BIOGRAPHY to 8,
    CategoriesFilms.SCIENCE_FICTION to 6,
    CategoriesFilms.CARTOONS to 18
)

val PROFESSIONS = mapOf(
    "WRITER" to "Сценарист",
    "OPERATOR" to "Оператор",
    "EDITOR" to "Монтаж",
    "COMPOSER" to "Композитор",
    "PRODUCER_USSR" to "Режиссёр СССР",
    "TRANSLATOR" to "Дубляж",
    "DIRECTOR" to "Режиссёр",
    "DESIGN" to "Художник-постановщик",
    "PRODUCER" to "Продюссер",
    "ACTOR" to "Актёр",
    "VOICE_DIRECTOR" to "Звукорежиссёр",
    "UNKNOWN" to "Неизвестно"
)

val GALLERY_TYPES = mapOf(
    "STILL" to "кадры",
    "SHOOTING" to "со съемок",
    "POSTER" to "постеры",
    "FAN_ART" to "фан-арты",
    "PROMO" to "промо",
    "CONCEPT" to "концепт-арты",
    "WALLPAPER" to "обои",
    "COVER" to "обложки",
    "SCREENSHOT" to "скриншоты"
)

val SORTING_PARAMS = listOf(
    "RATING",
    "NUM_VOTE",
    "YEAR"
)

val FILM_TYPE = listOf(
    "TV_SHOW",
    "TV_SERIES",
    "MINI_SERIES",
    "ALL"
)