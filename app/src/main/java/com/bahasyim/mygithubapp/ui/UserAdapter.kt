package com.bahasyim.mygithubapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasyim.mygithubapp.data.response.ItemsItem
import com.bahasyim.mygithubapp.databinding.ItemUserViewBinding
import com.bahasyim.mygithubapp.userdetail.UserDetailActivity
import com.bumptech.glide.Glide

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    //mengisi data api -> recycler view
    class MyViewHolder(private val binding: ItemUserViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.USER_DETAIL, user.login)
                intent.putExtra(UserDetailActivity.EXTRA_AVATAR, user.avatarUrl)
                itemView.context.startActivity(intent)
            }
            with(binding) {
                Glide.with(itemView.context).load(user.avatarUrl).into(ivUser)
                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemUserViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}