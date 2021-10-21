package com.skillbox.hw14_Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.skillbox.hw14_Fragments.databinding.FragmentListBinding

open class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

//чтоб не делать отдельный лисенер на все 4 кнопки, сделаем сразу на все
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? MaterialButton }
            .forEach { materialButton ->
                materialButton.setOnClickListener {

                    onButtonClick(materialButton.text.toString())
                }
            }
    }

    private fun onButtonClick(buttonText: String) {
        itemSelectListener?.onItemSelect(buttonText)
    }

    private val itemSelectListener: ItemSelectListener?
        get() = parentFragment.let { it as? ItemSelectListener }


}
override fun onBackPressed() {
    supportFragmentManager.popBackStack()
}
