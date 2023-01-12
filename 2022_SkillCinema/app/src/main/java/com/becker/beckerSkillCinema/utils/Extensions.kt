package com.becker.beckerSkillCinema.utils

import android.widget.ImageView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.data.similarFilm.SimilarItem
import com.becker.beckerSkillCinema.entity.HomeItem
import com.bumptech.glide.Glide

fun ImageView.loadImage(imageUrl: String) {
    Glide
        .with(this)
        .load(imageUrl)
        .placeholder(R.drawable.no_poster)
        .into(this)
}

fun List<HomeItem>.toLimitTheNumberOfObjects(size: Int): List<HomeItem> {
    val resultList = mutableListOf<HomeItem>()
    repeat(size) {
        resultList.add(this[it])
    }
    return resultList.toList()
}

fun List<ItemImageGallery>.toLimitImages(size: Int): List<ItemImageGallery> {
    val resultList = mutableListOf<ItemImageGallery>()
    repeat(size) {
        resultList.add(this[it])
    }
    return resultList.toList()
}

fun List<SimilarItem>.toLimitSimilarFilm(size: Int): List<SimilarItem> {
    val resultList = mutableListOf<SimilarItem>()
    repeat(size) {
        resultList.add(this[it])
    }
    return resultList.toList()
}
