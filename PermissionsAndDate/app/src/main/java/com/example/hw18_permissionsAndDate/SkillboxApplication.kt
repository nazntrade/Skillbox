package com.example.hw18_permissionsAndDate

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

//implementation 'com.jakewharton.threetenabp:threetenabp:1.2.4' - это в build.gradle

//нужно сделать инициализацию бэк порта при запуске приложения до первого момента работы с датой и временем
class SkillboxApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) //передаем сюда нашу подключенную библиотеку
    }
    //после нужно сказать в манифесте чтоб андройд использовал этот класс при запуске приложения

//        <application
//
//        android:name="com.skillbox.lessons18_PermissionsAndDate.SkillboxApplication"
}