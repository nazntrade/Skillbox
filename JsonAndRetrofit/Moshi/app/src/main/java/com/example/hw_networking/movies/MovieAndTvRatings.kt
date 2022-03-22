package com.example.hw_networking.movies

import com.squareup.moshi.Json

enum class MovieAndTvRatings {

    @Json(name = "G")
     General_Audience,

    @Json(name = "PG")
    Parental_Guidance_Suggested,

    @Json(name = "PG-13")
    Parents_Strongly_Cautioned,

    @Json(name = "R")
    Restricted,

    @Json(name = "NC-17")
    No_Children_17_or_Under,

    @Json(name = "TV-Y")
    TV_All_Children,

    @Json(name = "TV-Y7")
    TV_Directed_to_Older_Children,

    @Json(name = "TV-G")
    TV_General_Audience,

    @Json(name = "TV-PG")
    TV_Parental_Guidance_Suggested,

    @Json(name = "TV-14")
    TV_Parents_Strongly_Cautioned,

    @Json(name = "TV-MA")
    TV_Mature_Audience_Only
}