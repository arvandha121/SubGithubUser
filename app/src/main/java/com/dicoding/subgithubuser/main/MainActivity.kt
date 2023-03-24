package com.dicoding.subgithubuser.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.subgithubuser.R
import com.dicoding.subgithubuser.adapter.ListUserAdapter
import com.dicoding.subgithubuser.databinding.ActivityMainBinding
import com.dicoding.subgithubuser.detail.DetailActivity
import com.dicoding.subgithubuser.response.main.UsersResponse

class MainActivity : AppCompatActivity(), ListUserAdapter.OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModels()
    private var hint = ""

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: ListUserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Github User"

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = ListUserAdapter()
        userAdapter.notifyDataSetChanged()

        mainViewModels()
        onClickCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(hint: String?): Boolean {
                    hint?.let { it ->
                        this@MainActivity.hint = it
                        mainViewModel.findUsers(this@MainActivity.hint)
                    }
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return true
    }

    private fun mainViewModels() {
        mainViewModel.listUser.observe(this) { list ->
            if (list.isNotEmpty()) {
                userAdapter.setData(list)
                showLoading(false)
                RecyclerView()
            }
        }

        mainViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }
    }

    private fun RecyclerView() {
        binding.rvListView.apply {
            val layoutManager = LinearLayoutManager(this.context)
            val itemDecoration = DividerItemDecoration(this.context, layoutManager.orientation)
            addItemDecoration(itemDecoration)
            setHasFixedSize(true)

            this.layoutManager = layoutManager
            this.adapter = userAdapter

            userAdapter.setOnItemClickListener(object : ListUserAdapter.OnItemClickListener {
                override fun onUserItemClick(user: UsersResponse) {
                    val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                    intentDetail.putExtra("extra_username", user)
                    startActivity(intentDetail)
                }
            })
        }
    }

    private fun showLoading(loading: Boolean?) {
        if (loading == true) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun onClickCallback() {
        userAdapter.setOnItemClickListener(object : ListUserAdapter.OnItemClickListener {
            override fun onUserItemClick(data: UsersResponse) {
                val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USERNAME, data)
                startActivity(moveIntent)
            }
        })
    }

    override fun onUserItemClick(user: UsersResponse) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
        startActivity(intent)
    }
}