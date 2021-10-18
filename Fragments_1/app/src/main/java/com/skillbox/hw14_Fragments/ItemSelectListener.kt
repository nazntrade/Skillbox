package com.skillbox.hw14_Fragments


//этот интерфейс связующ.звено, чтобы можно было использовать фрагменты в разных активити
interface ItemSelectListener {
    fun onItemSelect(text: String)
}