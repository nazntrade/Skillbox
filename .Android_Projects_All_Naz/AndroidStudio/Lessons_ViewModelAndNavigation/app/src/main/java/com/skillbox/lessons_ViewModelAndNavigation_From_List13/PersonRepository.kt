package com.skillbox.lessons_ViewModelAndNavigation_From_List13

import kotlin.random.Random

class PersonRepository {

    fun generatePersons(count: Int): List<Person> {
        val avatars = listOf(
            "https://cdn.pixabay.com/photo/2014/04/03/10/32/businessman-310819_1280.png",
            "https://png.pngtree.com/png-clipart/20190922/original/pngtree-business-male-user-avatar-vector-png-image_4774078.jpg",
            "https://png.pngtree.com/png-clipart/20190924/original/pngtree-female-user-avatars-flat-style-women-profession-vector-png-image_4822944.jpg",
            "https://cdn.pixabay.com/photo/2014/04/03/10/32/user-310807_1280.png"
        )

        val names = listOf(
            "Иван Петров",
            "Сергей Пупкин",
            "Мария Сидорова",
            "Елена Иванова"
        )

        val programmingLanguages = listOf(
            "Kotlin",
            "Java",
            "C++",
            "Python",
            "Javascript"
        )

        return (0..count).map {
            val id = it.toLong()
            val name = names.random()
            val avatar = avatars.random()
            val programmingLanguage = programmingLanguages.random()
            val isDeveloper = Random.nextBoolean()
            val age = Random.nextInt(15, 50)

            if (isDeveloper) {
                Person.Developer(
                    id = id,
                    name = name,
                    avatarLink = avatar,
                    age = age,
                    programmingLanguage = programmingLanguage
                )
            } else {
                Person.User(
                    id = id,
                    name = name,
                    avatarLink = avatar,
                    age = age
                )
            }
        }
    }

    fun createPerson(persons: List<Person>): List<Person> {
        val newPerson = generatePersons(1).first().let {
            when (it) {
                is Person.Developer -> it.copy(id = Random.nextLong())
                is Person.User -> it.copy(id = Random.nextLong())
            }
        }
        return listOf(newPerson) + persons
    }

    fun deletePerson(persons: List<Person>, position: Int): List<Person> {
        return persons.filterIndexed { index, user -> index != position }
    }
}