package com.example.lessons_16_lists1

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation

class PersonAdapter(
    private val onItemClick: (position: Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() { //RecyclerView.ViewHolder это базовый клас для всех ВьюХолдеров. И теперь мы смодем исп.разные виды ВьюХОлдеры

    private var persons: List<Person> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_USER -> UserHolder(parent.inflate(R.layout.item_user), onItemClick)
            TYPE_DEVELOPER -> DeveloperHolder(parent.inflate(R.layout.item_developer), onItemClick)
            else -> error("Incorrect viewType =$viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(persons[position]) {
            is Person.Developer -> TYPE_DEVELOPER
            is Person.User -> TYPE_USER
        }
    }

    override fun getItemCount(): Int = persons.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is UserHolder -> {
                val person = persons[position].let { it as? Person.User }
                    ?: error("Person at position = $position is not a user")//les 16.6 t23.30
                holder.bind(person)
            }

            is DeveloperHolder -> {
                val person = persons[position].let { it as? Person.Developer }
                    ?: error("Person at position = $position is not a developer")
                holder.bind(person)
            }
            else -> error("Incorrect view holder = $holder")
        }
    }

    fun updatePersons(newPersons: List<Person>) {
        persons = newPersons
    }

    abstract class BasePersonHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ): RecyclerView.ViewHolder(view) {

        private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = view.findViewById(R.id.ageTextView)
        private val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

        init {
            view.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        protected fun bindMainInfo( //protected чтобы классы насл.могли его вызывать внутри своих методов bind
            name: String,
            avatarLink: String,
            age: Int
        ) {

            nameTextView.text = name
            ageTextView.text = "Age = ${age}"

            avatarImageView.load(avatarLink) {
                error(R.drawable.ic_error)
                placeholder(R.drawable.ic_portrait)
                transformations(CircleCropTransformation())
            }

        }

    }

    class UserHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ): BasePersonHolder(view, onItemClick) {
        init {
            view.findViewById<TextView>(R.id.developerTextView).isVisible = false
        }

        fun bind(person: Person.User) {
            bindMainInfo(person.name, person.avatarLink, person.age)
        }
    }

    class DeveloperHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ): BasePersonHolder(view, onItemClick) {

        private val programmingLanguageView: TextView = view.findViewById(R.id.programmingLanguageTextView)

        fun bind(person: Person.Developer) {
            bindMainInfo(person.name, person.avatarLink, person.age)
            programmingLanguageView.text = "Programming language ${person.programmingLanguage}"
//            itemView.context.resources.getString(R.string.app_name)
        }

    }

    companion object {
        private const val TYPE_USER = 1
        private const val TYPE_DEVELOPER = 2
    }
}