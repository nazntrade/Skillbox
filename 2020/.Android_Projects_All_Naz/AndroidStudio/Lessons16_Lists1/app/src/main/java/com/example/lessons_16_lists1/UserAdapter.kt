package com.example.lessons_16_lists1

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation

//Lesson16.2
//0.0 этот класс создаст айтемВью из нашего списка пользователей
class UserAdapter(

    private val onItemClicked: (position: Int) -> Unit

) : RecyclerView.Adapter<UserAdapter.Holder>() { //Все адаптеры РесайклерВью должны наслед.от этого класса
    //ЮзерАдаптер будет работать с классом.Холдер
    //1.1 в качестве <аргумента типа>нужно использовать класс ВьюХолдера. Он сод.корневую Вью для списка и все ссылки

    private var users: List<User> = emptyList()

    //2.2 переопределим 3 обяз.метода
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.d("UserAdapter", "onCreateViewHolder")
        //чтобы создать вьюЭлементы списка нам нужно заинфлэйтить из нашей разметку айтемюзер
        //для этого нужно получить доступ к инфлэйтеру,кот.будет преобразовывать разметку во вью
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_user, parent, false)
//        return Holder(view) //создадим вьюХолдер и сразу его вернем. все упростили с помощью Экстеншен
        return Holder(parent.inflate(R.layout.item_user), onItemClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d("UserAdapter", "onBindViewHolder|position=$position")
        //3.3
        val user: User = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int =
        users.size //должен вернуть кол-во элементов кот.будет в наш.списке

    fun updateUsers(newUsers: List<User>) {
        users = newUsers

    }

    //1.1 сами создаем класс ВьюХолдера прям в классе Адаптера! Внизу(по соглашениямКотлин)! Он должен наследоваться от...
    class Holder(
        view: View,
        onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        //3.3 он будет устанавливать все данные во вьюшки
        private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = view.findViewById(R.id.ageTextView)
        private val developerTextView: TextView = view.findViewById(R.id.developerTextView)
        private val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

        init {
            view.setOnClickListener {
                onItemClicked(bindingAdapterPosition)//adapterPosition deprecated
            }
        }

        fun bind(user: User) {
            nameTextView.text = user.name
            ageTextView.text = "Age = ${user.age}"

            developerTextView.visibility = if (user.isDeveloper) View.VISIBLE else View.GONE

            avatarImageView.load(user.avatarLink) {
                error(R.drawable.ic_error)
                placeholder(R.drawable.ic_portrait)
                transformations(CircleCropTransformation())
            }
        }
    }
}