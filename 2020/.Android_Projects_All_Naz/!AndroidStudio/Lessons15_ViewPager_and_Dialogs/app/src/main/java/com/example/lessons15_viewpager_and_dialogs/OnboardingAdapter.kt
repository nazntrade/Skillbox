package com.example.lessons15_viewpager_and_dialogs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//в конструктор нужно передать родительский фрагмент или активити,кот.будет иметь класс FragmentActivity
//это базавое активити,кот.может работать со всеми фрагментами
class OnboardingAdapter(
    private val screens: List<OnboardingScreen>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    //имплементировали обязательные
    //сообщает сколько эл.может быть
    override fun getItemCount(): Int {
        return screens.size
    }

    //создает фрагм.для пейджера по надобности
    override fun createFragment(position: Int): Fragment {
        //обратимся к скрин и по номеру позиции возьмем нужный элемент
        val screen: OnboardingScreen = screens[position]
        //используем у OnboardingFragment публичную ф-ю newInstance
        return OnboardingFragment.newInstance(
            textRes = screen.textRes,
            bgColorRes = screen.bgColorRes,
            drawableRes = screen.drawableRes
        )
    }

}