package com.example.lessons15_viewpager_and_dialogs

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.lessons15_viewpager_and_dialogs.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {
    private lateinit var binding: FragmentOnboardingBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardingBinding.bind(view)
//Установим цветфона, текст и картинку в наш фрагмент(достанем) из бандела
        requireView().setBackgroundResource(requireArguments().getInt(KEY_COLOR))
        binding.textView.setText(requireArguments().getInt(KEY_TEXT))
        binding.imageView.setImageResource(requireArguments().getInt(KEY_IMAGE))
    }

    companion object {

        private const val KEY_TEXT = "text"
        private const val KEY_COLOR = "color"
        private const val KEY_IMAGE = "image"
            // положим необходимое в бандл
        fun newInstance(
            //мы создаем аннотации @StringRes чтобы нельзя было передать сюда что-то другое,
            // кроме как ссылку на конкретный ресурс в формате Int
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