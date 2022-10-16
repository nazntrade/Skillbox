package com.skillbox.a10_fragment_

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment: Fragment(R.layout.fragment_list) {

    private val itemSelectListener: ItemSelectListener?
        get() = activity?.let { it as? ItemSelectListener }

    init {
        Log.d("ListFragment", "init activity=$activity")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ListFragment", "onAttach activity=$activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? Button }
            .forEach { button ->
                button.setOnClickListener {
                    onButtonClick(button.text.toString())
                }
            }
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun onButtonClick(buttonText: String) {
        itemSelectListener?.onItemSelected(buttonText)
    }

}