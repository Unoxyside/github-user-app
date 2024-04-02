package com.bahasyim.mygithubapp.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahasyim.mygithubapp.data.local.entity.FavEntity
import com.bahasyim.mygithubapp.databinding.ItemUserViewBinding
import com.bahasyim.mygithubapp.userdetail.UserDetailActivity
import com.bumptech.glide.Glide

class FavoriteAdapter: ListAdapter<FavEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDetailActivity::class.java)

            intent.putExtra(UserDetailActivity.USER_DETAIL, user.username)
            intent.putExtra(UserDetailActivity.EXTRA_AVATAR, user.avatarUrl)

            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemUserViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavEntity) {
            binding.tvUsername.text = favoriteUser.username
            Glide.with(itemView.context)
                .load(favoriteUser.avatarUrl)
                .into(binding.ivUser)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavEntity>() {
            override fun areItemsTheSame(
                oldItem: FavEntity,
                newItem: FavEntity,
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: FavEntity,
                newItem: FavEntity,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}