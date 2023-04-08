package com.dicoding.subgithubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.subgithubuser.R
import com.dicoding.subgithubuser.data.adapter.ListUserAdapter
import com.dicoding.subgithubuser.data.adapter.UserAdapter
import com.dicoding.subgithubuser.data.response.main.UsersResponse
import com.dicoding.subgithubuser.data.room.database.DatabaseModule
import com.dicoding.subgithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.subgithubuser.ui.detail.DetailActivity
import com.dicoding.subgithubuser.ui.detail.DetailActivity.Companion.EXTRA_USERNAME
import com.dicoding.subgithubuser.ui.detail.follow.SectionsPagerAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                putExtra("extra_username", user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavoriteViewModel>() {
        FavoriteViewModel.Factory(DatabaseModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.list_favorite)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.favoriteActivity.layoutManager = LinearLayoutManager(this)
        binding.favoriteActivity.adapter = adapter
        onClickCallback()

        viewModel.getFavorite().observe(this) {
            adapter.setData(it as MutableList<UsersResponse>)
        }
    }

    private fun onClickCallback() {
        adapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun onUserItemClick(data: UsersResponse) {
                val moveIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                moveIntent.putExtra("item", data)
                startActivity(moveIntent)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}