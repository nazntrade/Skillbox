package com.example.screen_listpage.tools

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.tools.SetSearch
import com.example.core.tools.adapter.adapters.NestedAdapterBase
import com.example.core.tools.category.TypeSettings
import com.example.core.tools.general.GenreCountry
import com.example.fueture_yerpicker.view.YerPicker
import com.example.screen_listpage.presentation.setting.SettingsSearchFragment
import kotlin.math.max
import kotlin.math.min

@SuppressLint("NotifyDataSetChanged")
fun SettingsSearchFragment.createDialogSetting(
    list: List<GenreCountry>,
    typeSettings: TypeSettings,
    block: (id: Int) -> Unit
) {

    val view = LayoutInflater.from(requireContext())
        .inflate(R.layout.setting_searhc, null)
    val editText = view.findViewById<EditText>(R.id.editText_Test)
    val title = view.findViewById<TextView>(R.id.category_name)
    val back = view.findViewById<ImageView>(R.id.back_arrow)
    val recyclerView = view.findViewById<RecyclerView>(R.id.settings_recycler)

    editText.hint = typeSettings.hint
    title.text = typeSettings.title

    val dialog = AlertDialog.Builder(requireContext())
        .setView(view)
        .create()

    val adapter = NestedAdapterBase({
        if (it.query?.id != null)
            block(it.query!!.id!!)
        dialog.dismiss()
    }, {}, typeSettings)

    val id = if (typeSettings == TypeSettings.COUNTRY) SetSearch.counterID
    else SetSearch.genreId

    for (i in 0..list.lastIndex) {
        if (list[i].id == id) {
            recyclerView.scrollToPosition(i)
            break
        }
    }

    var itemList = list.toMutableList()
    recyclerView.adapter = adapter.apply { items = itemList.toList() }

    editText.doOnTextChanged { text, _, _, _ ->

        if (text != null) {
            itemList = list.toMutableList()
            list.forEach {
                if (!it.info.contains(text))
                    itemList.remove(it)
            }
        }

        adapter.items = itemList.toList()
        recyclerView.adapter = adapter
        recyclerView.adapter?.notifyDataSetChanged()
    }

    back.setOnClickListener { dialog.dismiss() }

    dialog.show()
}

fun SettingsSearchFragment.createDialogYearPicker(block: () -> Unit) {
    val view = LayoutInflater.from(requireContext())
        .inflate(com.example.screen_listpage.R.layout.year_picker, null)

    val nextButton = view.findViewById<Button>(com.example.screen_listpage.R.id.set_button)
    val back = view.findViewById<ImageView>(R.id.back_arrow)

    var from =  SetSearch.yearFrom
    var to =  SetSearch.yearTo

    val rangePickerFrom =view.findViewById<YerPicker>(com.example.screen_listpage.R.id.rangeFrom)
    val rangePickerTo =view.findViewById<YerPicker>(com.example.screen_listpage.R.id.rangeTo)

    val dialog = AlertDialog.Builder(requireContext())
        .setView(view)
        .create()

    rangePickerFrom.setDateListener ={ from = it }
    rangePickerTo.setDateListener ={ to = it }

    nextButton.setOnClickListener {
        SetSearch.yearFrom = min(from,to)
        SetSearch.yearTo = max(from,to)
        block()
        dialog.dismiss()
    }

    back.setOnClickListener { dialog.dismiss() }
    dialog.show()
}