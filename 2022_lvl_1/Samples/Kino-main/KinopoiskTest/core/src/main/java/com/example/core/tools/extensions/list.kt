package com.example.core.tools.extensions

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.films.BaseEntityFilm
import com.example.core.tools.base_model.films.BaseFilm
import com.example.core.tools.base_model.imes.ItemNext
import com.example.core.tools.base_model.imes.ItemStun


fun List<BaseEntityFilm>.toBaseFilmList(): List<BaseFilm> {
    val list = mutableListOf<BaseFilm>()
    this.forEach { list.add(it.toBaseFilm()) }
    return list
}

fun List<BaseFilm>.createListForView(
    sizeListInView: Int,
): List<NestedInfoInCategory> {

    val size = if (this.lastIndex < sizeListInView) this.size
    else sizeListInView

    val list: MutableList<NestedInfoInCategory> = this.take(size).toMutableList()

    if (list.isEmpty()) list.add(ItemStun())
    else if (this.size > sizeListInView) list.add(ItemNext())
    return list
}


