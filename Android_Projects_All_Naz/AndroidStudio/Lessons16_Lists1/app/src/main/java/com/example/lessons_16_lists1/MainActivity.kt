package com.example.lessons_16_lists1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val users = listOf(
        User(
            name = "Иван Петров",
            avatarLink = "https://cdn.pixabay.com/photo/2014/04/03/10/32/businessman-310819_1280.png",
            age = 25,
            isDeveloper = true
        ),
        User(
            name = "Петр Иванов",
            avatarLink = "https://png.pngtree.com/png-clipart/20190922/original/pngtree-business-male-user-avatar-vector-png-image_4774078.jpg",
            age = 30,
            isDeveloper = false
        ),
        User(
            name = "Елена Сергеевна",
            avatarLink = "https://png.pngtree.com/png-clipart/20190924/original/pngtree-female-user-avatars-flat-style-women-profession-vector-png-image_4822944.jpg",
            age = 18,
            isDeveloper = true
        ),
        User(
            name = "Анна Александрова",
            avatarLink = "https://cdn.pixabay.com/photo/2014/04/03/10/32/user-310807_1280.png",
            age = 30,
            isDeveloper = false
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}