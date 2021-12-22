package com.skillbox.lessons_ViewModelAndNavigation_From_List13.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.lessons_ViewModelAndNavigation_From_List13.Person
import com.skillbox.lessons_ViewModelAndNavigation_From_List13.R
import com.skillbox.lessons_ViewModelAndNavigation_From_List13.inflate

class DeveloperAdapterDelegate(
    private val onItemClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Person.Developer, Person, DeveloperAdapterDelegate.DeveloperHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): DeveloperHolder {
        return DeveloperHolder(
            parent.inflate(R.layout.item_developer),
            onItemClick
        )
    }

    override fun isForViewType(item: Person, items: MutableList<Person>, position: Int): Boolean {
        return item is Person.Developer
    }

    override fun onBindViewHolder(
        item: Person.Developer,
        holder: DeveloperHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class DeveloperHolder(
        view: View,
        onItemClick: (id: Long) -> Unit
    ) : BasePersonHolder(view, onItemClick) {

        private val programmingLanguageView: TextView =
            view.findViewById(R.id.programmingLanguageTextView)

        fun bind(person: Person.Developer) {
            bindMainInfo(person.id, person.name, person.avatarLink, person.age)
            programmingLanguageView.text = "Язык программирования ${person.programmingLanguage}"
//            itemView.context.resources.getString(R.string.app_name)
        }

    }
}