package com.example.lessons_16_lists1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

    //Lesson16.2
    //0.0 этот класс создаст айтемВью из нашего списка пользователей
class UserAdaprer(
    private val users: List<User>,
) : RecyclerView.Adapter<UserAdaprer.Holder>() { //Все адаптеры РесайклерВью должны наслед.от этого класса
                    //ЮзерАдаптер будет работать с классом.Холдер
                   //1.1 в качестве <аргумента типа>нужно использовать класс ВьюХолдера. Он сод.корневую Вью для списка и все ссылки

      //2.2 переопределим 3 обяз.метода
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //чтобы создать вьюЭлементы списка нам нужно заинфлэйтить из нашей разметку айтемюзер
        //для этого нужно получить доступ к инфлэйтеру,кот.будет преобразовывать разметку во вью
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_user, parent, false)
//        return Holder(view) //создадим вьюХолдер и сразу его вернем. все упростили с помощью Экстеншен
        return Holder(parent.iflate((R.layout.item_user)))
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        TODO("Not yet implemented")
    }
    override fun getItemCount(): Int = users.size //должен вернуть кол-во элементов кот.будет в наш.списке

//1.1 сами создаем класс ВьюХолдера прям в классе Адаптера! Внизу(по соглашениямКотлин)! Он должен наследоваться от...
    class Holder(view: View) : RecyclerView.ViewHolder(view)
}