package com.bahasyim.mygithubapp.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahasyim.mygithubapp.R
import com.bahasyim.mygithubapp.darkmode.DarkModeActivity
import com.bahasyim.mygithubapp.darkmode.DarkModeViewModel
import com.bahasyim.mygithubapp.darkmode.DarkModeViewModelFactory
import com.bahasyim.mygithubapp.darkmode.SettingPreferences
import com.bahasyim.mygithubapp.darkmode.dataStore
import com.bahasyim.mygithubapp.data.response.ItemsItem
import com.bahasyim.mygithubapp.databinding.ActivityMainBinding
import com.bahasyim.mygithubapp.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    val query = searchView.text.toString()
                    mainViewModel.findUser(query)
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_LONG).show()
                    false
                }
            searchBar.inflateMenu(R.menu.menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId){
                    R.id.menu_setting -> {
                        val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu_favorite -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        mainViewModel.listUser.observe(this) { user ->
            setListUser(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        darkMode()

    }

    private fun darkMode() {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val darkModeViewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref)).get(
            DarkModeViewModel::class.java
        )
        darkModeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


    private fun setListUser(user: List<ItemsItem>?) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}
