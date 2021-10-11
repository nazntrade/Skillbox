package com.example.lessons14_fragment


//этот интерфейс связующ.звено, чтобы можно было использовать фрагменты в разных активити
interface ItemSelectListener {
    fun onItemSelect(text: String)
}