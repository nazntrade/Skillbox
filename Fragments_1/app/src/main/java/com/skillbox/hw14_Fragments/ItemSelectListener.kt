package com.skillbox.hw14_Fragments

//этот интерфейс связующ.звено, чтобы можно было использовать фрагменты в разных активити(другиъ фрагментах)
interface ItemSelectListener {
    fun onItemSelect(text: String)
}