package com.zhdanon.rickandmortycompose.entity

interface Episode {
    val airDate: String
    val characters: List<String>
    val created: String
    val episode: String
    val id: Int
    val name: String
    val url: String
}