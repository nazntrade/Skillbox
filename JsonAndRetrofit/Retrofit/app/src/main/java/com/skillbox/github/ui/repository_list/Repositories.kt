package com.skillbox.github.ui.repository_list

data class Repositories(
    val id: Long,
    val name: String,       //name repo
    val login: String,      //owner's //wrapped on owner
    val avatar_url: String  //owner's //wrapped on owner
)
