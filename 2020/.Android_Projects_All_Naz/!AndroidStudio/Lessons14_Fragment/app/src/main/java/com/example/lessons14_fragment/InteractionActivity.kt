package com.example.lessons14_fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons14_fragment.databinding.ActivityInteractionBinding

// не забываем добавить новую акт.в манифест
class InteractionActivity : AppCompatActivity(), ItemSelectListener {
    lateinit var binding: ActivityInteractionBinding

    override fun onItemSelect(text: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, InfoFragment.newInstance(text))
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInteractionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}