package com.becker.beckerSkillCinema.utils

import androidx.annotation.StringRes
import com.becker.beckerSkillCinema.App

// in order to get strings from resources anywhere
object MyStrings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return App.instance.getString(stringRes, *formatArgs)
    }
}