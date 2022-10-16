package ru.skillbox.contentprovider.data

data class Contact(
    val id: Long,
    val name: String,
    val phones: List<String>
)