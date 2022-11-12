package com.skillbox.lesson11_5

import android.os.Bundle
import android.telephony.mbms.StreamingServiceInfo
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.skillbox.lesson11_5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val users = listOf(
        "User1",
        "User2",
        "Unknown"
    )

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
    }


    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {

        binding.toolbar.setNavigationOnClickListener {
            toast("Navigation clicked")
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_1 -> {
                    toast("action_1 clicked")
                    true
                }
                R.id.action_2 -> {
                    toast("action_2 clicked")
                    true
                }
                R.id.action_3 -> {
                    toast("action_3 clicked")
                    true
                }
                else -> false
            }
        }

        val searchItem = binding.toolbar.menu.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                binding.expendTextView.text = "search expanded"
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                binding.expendTextView.text = "search collapsed"
                return true
            }
        })

        (searchItem.actionView as android.widget.SearchView).setOnQueryTextListener(
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    users.filter { it.contains(newText ?: "", ignoreCase = true) }
                        .joinToString()
                        .let {
                            binding.searchResultTextView.text = it
                        }
                    return true
                }

            })

    }
}

