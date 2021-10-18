package com.skillbox.hw14_Fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.skillbox.hw14_Fragments.databinding.FragmentListBinding

open class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding

    private val itemSelectListener: ItemSelectListener?
        get() = activity?.let { it as? ItemSelectListener }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        Toast.makeText(activity, "Attention. You are logged into the pentagon.", Toast.LENGTH_LONG)
            .show()

//чтоб не делать отдельный лисенер на все 4 кнопки, сделаем сразу на все
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? ConstraintLayout }
            .forEach { constraintLayout: ConstraintLayout ->
                constraintLayout.setOnClickListener {

                    onButtonClick(binding.containerSpiderMan.toString())

                }
            }
    }

//    private fun addDetailFragment() {
//        val detailFragment = DetailFragment()
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.containerMain, detailFragment, "findDetailFragment")
//            .addToBackStack(null)
//            .commit()
//    }

    private fun onButtonClick(buttonText: String) {
        itemSelectListener?.onItemSelect(buttonText)
    }

}