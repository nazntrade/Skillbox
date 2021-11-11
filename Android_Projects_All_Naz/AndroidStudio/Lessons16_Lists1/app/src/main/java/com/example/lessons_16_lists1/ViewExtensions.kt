package com.example.lessons_16_lists1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
// сдесь будут храниться экстеншен функции для View. Они будут выполнять рутину по получению инфлэйта(инфлэйтингу вью)
// сделаем экст.ф-ю на классе ВьюГруп, чтоб мы обращались к родит вью и говорили ей заинфлейти дочернюю вью.(:View)-вернуть заинфл.Вьюшку
fun ViewGroup.iflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}
