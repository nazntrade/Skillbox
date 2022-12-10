package com.example.core.tools

object SetSearch {
    var counterID: Int = 1
    var genreId:Int = 1
    var order: Order = Order.YEAR
    var type: Type = Type.ALL
    var ratingFrom: Int = 1
    var ratingTo: Int = 10
    var yearFrom: Int = 1000
    var yearTo: Int = 3000
    var page: Int = 1
    var keyword: String = "матрица"
}

enum class Order(val text:String) {
    RATING("Рейтинг"),
    NUM_VOTE("Популярность"),
    YEAR("Дата")
}

enum class Type(val text:String) {
    TV_SERIES("Сериалы"),
    FILM("Фильмы"),
    ALL("Все"),
}
