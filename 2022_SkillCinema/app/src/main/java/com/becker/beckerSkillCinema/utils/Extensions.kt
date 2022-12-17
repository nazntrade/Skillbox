package com.becker.beckerSkillCinema.utils

import android.widget.ImageView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.Month
import com.becker.beckerSkillCinema.entity.HomeItem
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String) {
    Glide
        .with(this)
        .load(imageUrl)
        .placeholder(R.drawable.no_poster)
        .into(this)
}

fun Int.converterInMonth(): String {
    var textMonth = ""
    if (this <= 0 || this > 12)
        textMonth = Month.AUGUST.name
    else
        Month.values().forEach { month ->
            if (this == month.count) textMonth = month.name
        }
    return textMonth
}

fun List<HomeItem>.prepareToShow(size: Int): List<HomeItem> {
    val resultList = mutableListOf<HomeItem>()
    repeat(size) {
        resultList.add(this[it])
    }
    return resultList.toList()
}