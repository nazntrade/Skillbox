package com.becker.beckerSkillCinema.data.profile

sealed class Collections {
    object Favorites : Collections()
    object ToWatch : Collections()
    object Custom : Collections()
}