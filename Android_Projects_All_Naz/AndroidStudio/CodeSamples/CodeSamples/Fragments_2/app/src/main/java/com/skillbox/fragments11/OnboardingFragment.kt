package com.skillbox.fragments11

import android.os.Bundle
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.skillbox.fragment10.withArguments
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment: Fragment(R.layout.fragment_onboarding) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("viewPager", "OnboardingFragment oncreate = ${resources.getString(requireArguments().getInt(
            KEY_TEXT))}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireView().setBackgroundResource(requireArguments().getInt(KEY_COLOR))
        textView.setText(requireArguments().getInt(KEY_TEXT))
        imageView.setImageResource(requireArguments().getInt(KEY_IMAGE))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("viewPager", "OnboardingFragment onDestroy = ${resources.getString(requireArguments().getInt(
            KEY_TEXT))}")
    }

    companion object {

        private const val KEY_TEXT = "text"
        private const val KEY_COLOR = "color"
        private const val KEY_IMAGE = "image"

        fun newInstance(
            @StringRes textRes: Int,
            @ColorRes bgColorRes: Int,
            @DrawableRes drawableRes: Int
        ): OnboardingFragment {
            return OnboardingFragment().withArguments {
                putInt(KEY_TEXT, textRes)
                putInt(KEY_COLOR, bgColorRes)
                putInt(KEY_IMAGE, drawableRes)
            }
        }
    }

}