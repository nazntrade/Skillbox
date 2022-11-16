package com.example.lessons14_fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.lessons14_fragment.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {
    lateinit var binding: FragmentListBinding

    private val itemSelectListener: ItemSelectListener?
        get() = activity?.let { it as? ItemSelectListener }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        //чтоб не делать отдельный лисенер на все 4 кнопки, сделаем сразу на все
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? Button }
            .forEach { button: Button ->
                button.setOnClickListener {
                    onButtonClick(button.text.toString())
                }
            }
    }

    private fun onButtonClick(buttonText: String) {
        itemSelectListener?.onItemSelect(buttonText)
    }
}