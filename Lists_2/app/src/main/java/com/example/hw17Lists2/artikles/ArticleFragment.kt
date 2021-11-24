package com.example.hw17Lists2.artikles

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.hw17Lists2.R
import com.example.hw17Lists2.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private lateinit var binding: FragmentArticleBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        binding.textArticle.setText(requireArguments().getInt(KEY_TEXT))
        binding.imageArticle.setImageResource(requireArguments().getInt(KEY_IMAGE))

    }

    companion object {
        private const val KEY_TEXT = "text"
        private const val KEY_IMAGE = "image"

        fun newInstance(
            @StringRes textRes: Int,
            @DrawableRes drawableRes: Int
        ): ArticleFragment {
            return ArticleFragment().withArguments {
                putInt(KEY_TEXT, textRes)
                putInt(KEY_IMAGE, drawableRes)
            }
        }
    }
}