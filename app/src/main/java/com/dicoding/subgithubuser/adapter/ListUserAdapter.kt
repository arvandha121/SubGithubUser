package com.dicoding.subgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.subgithubuser.databinding.ItemRowUserBinding
import com.dicoding.subgithubuser.response.UsersResponse

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    private val listUser = ArrayList<UsersResponse>()
    private var onClick: OnItemClickListener? = null

    fun setData(listUser: MutableList<UsersResponse>) {
        this.listUser.clear()
        this.listUser.addAll(listUser)
    }

    fun setOnItemClickListener(onClick: OnItemClickListener) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersResponse) {
            with(binding) {
                tvUsername.text = user.login

                val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(16))
                Glide.with(this.root.context)
                    .load(user.avatarUrl)
                    .apply(requestOptions)
                    .into(imgUserPicture)

                root.setOnClickListener { onClick?.onUserItemClick(user) }
            }
        }
    }

    interface OnItemClickListener {
        fun onUserItemClick(user: UsersResponse)
    }
}