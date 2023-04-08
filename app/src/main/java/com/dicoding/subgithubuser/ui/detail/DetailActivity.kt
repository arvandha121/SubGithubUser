package com.dicoding.subgithubuser.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.subgithubuser.R
import com.dicoding.subgithubuser.ui.detail.follow.SectionsPagerAdapter
import com.dicoding.subgithubuser.databinding.ActivityDetailBinding
import com.dicoding.subgithubuser.data.response.main.UsersResponse
import com.dicoding.subgithubuser.data.room.database.DatabaseModule
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private val detailViewModel by viewModels<DetailViewModel>() {
        DetailViewModel.Factory(DatabaseModule(this))
    }

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

        showLoading()

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.resultSuccessFavorite.observe(this) {
            binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }

        detailViewModel.resultDeleteFavorite.observe(this) {
            binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)
        }

        binding.btnFavorite.setOnClickListener {
            detailViewModel.setFavorite(username)
        }

        detailViewModel.findFavorite(username?.id ?: 0) {
            binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun showLoading(){
        val empty = true
        detailViewModel.isLoading.observe(this){list ->
            if (list == empty){
                isLoading(false)
            }

        }
        detailViewModel.isLoading.observe(this) { loading ->
            isLoading(loading)
        }
    }

    private fun isLoading(loading : Boolean){
        if (loading == true){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}