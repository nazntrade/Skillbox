package com.skillbox.hw_m2_layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.skillbox.hw_m2_layout.databinding.MyCustomeLayoutBinding

class MyCustomView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = MyCustomeLayoutBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setText1(text: String) {
        binding.textView1.text = text
    }

    fun setText2(text: String) {
        binding.textView2.text = text
    }

    fun setImage1(imageResource: Int) {
        binding.imageView.setImageResource(imageResource)
    }

    fun setImage2(imageResource: Int) {
        binding.imageViewBig.setImageResource(imageResource)
    }
}