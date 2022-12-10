package com.example.feature_details.data

import com.example.core.tools.all.NestedInfoInCategory
import com.example.core.tools.base_model.BaseGallery

enum class ImageCategory(
    val nameCategory: String,
    var itemsList: MutableList<BaseGallery> = mutableListOf(),
    var pages: Int = 0,
    var total: Int = 0
):NestedInfoInCategory {
    STILL("кадры"),
    SHOOTING("изображения со съемок"),
    POSTER("постеры"),
    FAN_ART("фан-арты"),
    PROMO("промо"),
    CONCEPT("концепт-арты"),
    WALLPAPER("обои"),
    COVER("обложки"),
    SCREENSHOT("скриншоты"),
}