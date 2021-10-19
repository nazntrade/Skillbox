package com.skillbox.a10_fragment_

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment: Fragment(R.layout.fragment_info) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("lifecycle", "InfoFragment onAttach, ${hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifecycle", "InfoFragment onCreate, ${hashCode()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("lifecycle", "InfoFragment onCreateView, ${hashCode()}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inputTextView.text = requireArguments().getString(KEY_TEXT)
        Log.d("lifecycle", "InfoFragment onActivityCreated, ${hashCode()}")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("lifecycle", "InfoFragment onViewStateRestored, ${hashCode()}")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "InfoFragment onStart, ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "InfoFragment onResume, ${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "InfoFragment onPause, ${hashCode()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("lifecycle", "InfoFragment onSaveInstanceState, ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "InfoFragment onStop, ${hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("lifecycle", "InfoFragment onDestroyView, ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "InfoFragment onDestroy, ${hashCode()}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("lifecycle", "InfoFragment onDetach ${hashCode()}")
    }

    companion object {

        private const val KEY_TEXT = "key_text"

        fun newInstance(text: String): InfoFragment {
            return InfoFragment().withArguments {
                putString(KEY_TEXT, text)
            }
        }
    }

}