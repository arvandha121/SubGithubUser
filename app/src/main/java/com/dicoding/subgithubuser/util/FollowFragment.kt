package com.dicoding.subgithubuser.util

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.subgithubuser.adapter.ListUserAdapter
import com.dicoding.subgithubuser.databinding.FragmentFollowBinding
import com.dicoding.subgithubuser.detail.DetailActivity
import com.dicoding.subgithubuser.response.main.UsersResponse

class FollowFragment : Fragment() {
    private val viewModel by viewModels<FollowViewModel>()
    private lateinit var userAdapter: ListUserAdapter
    private lateinit var binding: FragmentFollowBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userAdapter = ListUserAdapter()
        userAdapter.notifyDataSetChanged()

        showLoading()
    }

    private fun showLoading(){
        viewModel.isLoading.observe(this) { list ->
            val empty = true
            if (list == empty) {
                isLoading(false)
                RecyclerView()
            }
        }

        viewModel.isLoading.observe(this) { loading ->
            isLoading(loading)
        }
    }

    private fun isLoading(loading : Boolean){
        if (loading == true){
            binding.progressBars.visibility = View.VISIBLE
        }else{
            binding.progressBars.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val username = arguments?.getString(idFollow)

        val position = arguments?.getInt(idInt)

        binding = FragmentFollowBinding.inflate(layoutInflater)
        val recyclerView = binding.idLayout

        if (position == 1) {
            username?.let { viewModel.getFollower(it) }
            viewModel.listFollower.observe(viewLifecycleOwner) {
                userAdapter.setData(it)
                RecyclerView()
                Log.d(idFollow, it.toString())
            }
        }
        else{
            username?.let { viewModel.getFollowing(it) }
            viewModel.listFollowing.observe(viewLifecycleOwner){
                userAdapter.setData(it)
                RecyclerView()
                Log.d(idFollow, it.toString())
            }
        }

        recyclerView.adapter = userAdapter

        return binding.root
    }

    private fun RecyclerView() {
        binding.idLayout.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
            addItemDecoration(itemDecoration)
            setHasFixedSize(true)

            this.layoutManager = layoutManager
            this.adapter = userAdapter

            userAdapter.setOnItemClickListener(object : ListUserAdapter.OnItemClickListener{
                override fun onUserItemClick(user: UsersResponse) {
                    val intentDetail = Intent(context, DetailActivity::class.java)
                    intentDetail.putExtra("extra_username", user)
                    startActivity(intentDetail)
                }

            })
        }
    }

    companion object {
        const val idFollow = "FollowFragment"
        const val idInt = "FollowInt"
    }
}