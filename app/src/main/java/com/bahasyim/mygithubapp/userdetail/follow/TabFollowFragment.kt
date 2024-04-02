package com.bahasyim.mygithubapp.userdetail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahasyim.mygithubapp.databinding.FragmentTabFollowBinding
import com.bahasyim.mygithubapp.ui.UserAdapter


class TabFollowFragment : Fragment() {

    private lateinit var binding: FragmentTabFollowBinding
    private lateinit var adapter: UserAdapter

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    private var username: String =""
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME, "")
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTabFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followersViewModel = ViewModelProvider(this)[FollowersViewModel::class.java]
        followingViewModel = ViewModelProvider(this)[FollowingViewModel::class.java]

        showRecyclerView()
        observFollow()
    }

    private fun showRecyclerView() {
        adapter = UserAdapter()
        binding.rvFollowTab.layoutManager = LinearLayoutManager(requireContext())

        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvFollowTab.addItemDecoration(itemDecoration)
    }

    private fun observFollow() {
        if (position == 0) {
            followersViewModel.setUsername(username)
            followersViewModel.followers.observe(viewLifecycleOwner) { followers ->
                showLoading(false)
                adapter.submitList(followers)
                binding.rvFollowTab.adapter = adapter
            }
        } else if (position == 1) {
            followingViewModel.setUsername(username)
            followingViewModel.following.observe(viewLifecycleOwner) { following ->
                showLoading(false)
                adapter.submitList(following)
                binding.rvFollowTab.adapter = adapter
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBarFollow.visibility = View.VISIBLE
            binding.rvFollowTab.visibility = View.GONE
        } else {
            binding.progressBarFollow.visibility = View.GONE
            binding.rvFollowTab.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"
    }
}