package com.bahasyim.mygithubapp.userdetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bahasyim.mygithubapp.R
import com.bahasyim.mygithubapp.data.local.entity.FavEntity
import com.bahasyim.mygithubapp.data.response.DetailUserResponse
import com.bahasyim.mygithubapp.databinding.ActivityUserDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var isFavorite: Boolean = false

    companion object {
        const val USER_DETAIL = "user_detail"
        const val EXTRA_AVATAR = "extra_avatar"
        private val TAB_SECTION = intArrayOf(
            R.id.tv_followers,
            R.id.tv_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        val username = intent.getStringExtra(USER_DETAIL) ?: ""
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR) ?: ""

        val items = intent.getStringExtra(USER_DETAIL)

        //add
        userDetailViewModel.getFavoriteByUsername(username).observe(this) {
            isFavorite = it.isNotEmpty()
            val favoriteUser = FavEntity(username, avatarUrl)
            if (it.isEmpty()) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context, R.drawable.ic_fav_outline
                    )
                )
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context, R.drawable.ic_fav_fill
                    )
                )
            }
            binding.fabFavorite.setOnClickListener {
                if (isFavorite) {
                    userDetailViewModel.delete(favoriteUser)
                    Toast.makeText(
                        this@UserDetailActivity,
                        "${username} deleted from favorites",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    userDetailViewModel.insert(favoriteUser)
                    Toast.makeText(
                        this@UserDetailActivity,
                        "${username} added to favorites",
                        Toast.LENGTH_LONG
                    ).show()
                }
                binding.fabFavorite.apply {
                    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", 0.7f, 1.1f, 1.0f)
                    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", 0.7f, 1.1f, 1.0f)
                    scaleUpX.duration = 300
                    scaleUpY.duration = 300

                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(scaleUpX, scaleUpY)
                    animatorSet.start()
                }

            }
        }

        userDetailViewModel.setUserDetail(userDetail = items.toString())

        userDetailViewModel.userDetail.observe(this) { userDetail ->
            setUserDetail(userDetail)
        }


        //sectionPagerAdapter
        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager2: ViewPager2 = binding.viewPager2
        viewPager2.adapter = sectionPagerAdapter

        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                tab.text = resources.getString(TAB_SECTION[position])
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.followersSection)
                    }

                    1 -> {
                        tab.text = getString(R.string.followingSection)
                    }

                    else -> {

                    }
                }
            }
        tabLayoutMediator.attach()

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }


    @SuppressLint("StringFormatMatches")
    private fun setUserDetail(userDetailResponse: DetailUserResponse) {
        with(binding) {
            tvNameDetail.text = userDetailResponse.name
            tvUsernameDetail.text = userDetailResponse.login
            tvFollowers.text = getString(R.string.followers, userDetailResponse.followers)
            tvFollowing.text = getString(R.string.following, userDetailResponse.following)

            Glide.with(binding.root.context)
            .load(userDetailResponse.avatarUrl)
            .circleCrop()
            .into(binding.ivUserDetail)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }
}