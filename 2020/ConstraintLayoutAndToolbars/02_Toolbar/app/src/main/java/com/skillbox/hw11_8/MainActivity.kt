package com.skillbox.hw11_8

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.skillbox.hw11_8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myImage.load("https://learnenglish.britishcouncil.org/sites/podcasts/files/styles/article/public/RS8414_GettyImages-1189211944-low_0.jpg?itok=VkSa2N-c")

        initToolbar()
    }

    private val searchText = listOf(
        "User",
        "User2",
        "Andrew",
        "Sergey",
        "Leo",
        "Blood",
        "Felix",
        "Helena",
        "Tom"
    )

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            toast("You clicked button 'back'")
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.play -> {
                    toast("Began reading book")
                    true
                }
                R.id.timer_shutdown -> {
                    toast("You turned on the shutdown timer ")
                    true
                }
                R.id.night_theme -> {
                    toast("You turned on the night theme")
                    true
                }
                else -> false
            }
        }

        val searchItem = binding.toolbar.menu.findItem(R.id.search_toolbar)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                binding.searchTextView.text = "search expanded"
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                binding.searchTextView.text = "search collapsed"
                return true
            }
        })

        (searchItem.actionView as android.widget.SearchView).setOnQueryTextListener(
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    searchText.filter { it.contains(p0 ?: "", ignoreCase = true) }
                        .joinToString()
                        .let {
                            binding.searchTextView.text = it
                        }
                    return true
                }
            }
        )
    }
}

