package com.becker.beckerSkillCinema.data

val TOP_TYPES = mapOf(
    CategoriesFilms.BEST to "TOP_250_BEST_FILMS",
    CategoriesFilms.POPULAR to "TOP_100_POPULAR_FILMS",
    CategoriesFilms.AWAIT to "TOP_AWAIT_FILMS",
    CategoriesFilms.TV_SERIES to "TV_SERIES"
)

enum class Month(val count: Int) {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12)
}

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
    "FILM",
    "TV_SHOW",
    "TV_SERIES",
    "MINI_SERIES",
    "ALL"
)