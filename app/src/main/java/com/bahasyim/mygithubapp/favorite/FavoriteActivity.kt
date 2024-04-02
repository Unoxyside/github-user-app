package com.bahasyim.mygithubapp.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahasyim.mygithubapp.R
import com.bahasyim.mygithubapp.data.local.entity.FavEntity
import com.bahasyim.mygithubapp.databinding.ActivityFavoriteBinding
import com.bahasyim.mygithubapp.userdetail.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = FavoriteAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        favoriteViewModel.getAllFavorite().observe(this) { users ->
            val items = users.map { favoriteUser ->
                FavEntity(username = favoriteUser.username, avatarUrl = favoriteUser.avatarUrl)
            }
            adapter.submitList(items)
            binding.rvFavorite.adapter = adapter
        }

    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFavorite.visibility = if (state) View.VISIBLE else View.GONE
    }
}
