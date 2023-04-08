package com.dicoding.subgithubuser.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.subgithubuser.data.response.main.UsersResponse
import com.dicoding.subgithubuser.databinding.ItemRowUserBinding

class UserAdapter(
    private val data: MutableList<UsersResponse> = mutableListOf(),
    private val listener: (UsersResponse) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    private var onClick: OnItemClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<UsersResponse>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onClick: OnItemClickListener) {
        this.onClick = onClick
    }

    class UserViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(user: UsersResponse) {
            with(binding) {
                tvUsername.text = user.login

                val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(16))
                Glide.with(this.root.context)
                    .load(user.avatarUrl)
                    .apply(requestOptions)
                    .into(imgUserPicture)
                root.setOnClickListener {
                    val onClick: OnItemClickListener? = null
                    if (onClick != null) {
                        onClick.onUserItemClick(user)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder =
        UserViewHolder(
            ItemRowUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    interface OnItemClickListener {
        fun onUserItemClick(user: UsersResponse)
    }

    override fun getItemCount(): Int = data.size

}