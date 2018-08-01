package com.example.user.simplehub.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubFollowing
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.item_follower.view.*
import kotlinx.android.synthetic.main.profile_tab_follow.view.*

class FollowingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
)

class FollowingListAdapter : RecyclerView.Adapter<FollowingViewHolder>() {
    var items: List<GithubFollowing> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        return FollowingViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            IDText.text = item.login
            Glide.with(this).load(item.avatarUrl).into(ownerAvatarImage)


        }
    }

}


class FollowingTab : Fragment() {

    lateinit var listAdapter: FollowingListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.profile_tab_follow, container, false)
        listAdapter = FollowingListAdapter()
        view.followingView.adapter = listAdapter
        view.followingView.layoutManager = LinearLayoutManager(activity!!.applicationContext)


        val followingApi = provideUserApi(activity!!.applicationContext)
        val followerCall = followingApi.getFollowingInfo()
        followerCall.enqueue({ response ->
            val result = response.body()
            result?.let {
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()

            }

        }, {

        })

        return view
    }
}