package com.dicoding.subgithubuser.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.subgithubuser.R
import com.dicoding.subgithubuser.util.SectionsPagerAdapter
import com.dicoding.subgithubuser.databinding.ActivityDetailBinding
import com.dicoding.subgithubuser.response.main.UsersResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private val detailViewModel by viewModels<DetailViewModel>()

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getParcelableExtra<UsersResponse>(EXTRA_USERNAME)
        binding.tvUsername.text = username?.login
        Log.d("DetailActivity", username.toString())
        Glide.with(this)
            .load(username?.avatarUrl)
            .into(binding.imgUserPicture)

        username?.login?.let { detailViewModel.getUserDetail(it) }
        detailViewModel.detailUser.observe(this){
            binding.tvFullname.text = it.name
            binding.tvFollower.text = "${it.followers} Followers"
            binding.tvFollowing.text = "${it.following} Followings"
        }

        val sectionsPagerAdapter = username?.login?.let { SectionsPagerAdapter(this, it) }
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}