package com.bahasyim.mygithubapp.userdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bahasyim.mygithubapp.userdetail.follow.TabFollowFragment

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    var username: String? = activity.intent.getStringExtra(UserDetailActivity.USER_DETAIL)

    override fun createFragment(position: Int): Fragment {
        val fragment = TabFollowFragment()
        fragment.arguments = Bundle().apply {
            putString(TabFollowFragment.ARG_USERNAME, username)
            putInt(TabFollowFragment.ARG_POSITION, position)
        }
        return fragment
    }
}