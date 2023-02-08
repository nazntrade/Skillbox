package com.becker.beckerSkillCinema.utils

import android.content.Context
import android.graphics.RectF
import android.widget.ImageView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.data.similarFilm.SimilarItem
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.presentation.search.datepicker.Date
import com.becker.beckerSkillCinema.presentation.search.datepicker.Draw
import com.becker.beckerSkillCinema.presentation.search.datepicker.SettingsPicker
import com.becker.beckerSkillCinema.presentation.search.datepicker.StateArrow
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
    if (this.size < size) {
        repeat(this.size) {
            resultList.add(this[it])
        }
    } else {
        repeat(size) {
            resultList.add(this[it])
        }
    }
    return resultList.toList()
}

fun List<SimilarItem>.toLimitSimilarFilm(size: Int): List<SimilarItem> {
    val resultList = mutableListOf<SimilarItem>()
    if (this.size < size) {
        repeat(this.size) {
            resultList.add(this[it])
        }
    } else {
        repeat(size) {
            resultList.add(this[it])
        }
    }
    return resultList.toList()
}

fun Context.dpToPx(dp: Int) = dp.toFloat() * resources.displayMetrics.density

fun Context.dpToSp(dp: Int) = dpToPx(dp) / resources.displayMetrics.scaledDensity

fun RectF.setOnClickListener(x: Float, y: Float, cellSizeW: Int): StateArrow {
    var position = StateArrow.NOT_NAVIGATE
    if (y > this.top && y < this.top + cellSizeW / 2)
        if (x > this.right - cellSizeW && x < this.right - cellSizeW * 0.3) position =
            StateArrow.BACK
        else if (x > right - cellSizeW * 0.3 && x < this.right) position = StateArrow.NEXT
    return position
}

fun Array<Array<Date>>.setOnClickListener(x: Float, y: Float): Pair<Int, Int> {
    var position = Pair(0, 0)
    this.forEachIndexed { row, dates ->
        dates.forEachIndexed { col, date ->
            val shapeX = date.rect.left
            val shapeX1 = date.rect.right
            val shapeY = date.rect.top
            val shapeY1 = date.rect.bottom
            if (x > shapeX && x < shapeX1 && y > shapeY && y < shapeY1)
                position = Pair(row, col)
        }
    }
    return position
}

fun Array<Array<Date>>.newDate(navigate: StateArrow, setting: SettingsPicker, block: () -> Unit) {
    when (navigate) {
        StateArrow.NEXT -> {
            setting.startDate = this.last().last().date + 1
            Draw.createMatrix(setting)
            block()
        }
        StateArrow.BACK -> {
            setting.startDate =
                this[1].first().date - ((setting.rows - 1) * setting.columns)
            Draw.createMatrix(setting)
            block()
        }
        StateArrow.NOT_NAVIGATE -> {}
    }
}
