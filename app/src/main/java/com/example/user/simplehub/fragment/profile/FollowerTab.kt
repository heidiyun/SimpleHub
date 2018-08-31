package com.example.user.simplehub.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubFollowers
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.OtherProfileActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.item_follower.view.*
import kotlinx.android.synthetic.main.profile_tab_follower.view.*


class FollowerTab : Fragment() {

    class FollowerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
    )

    inner class FollowerListAdapter : RecyclerView.Adapter<FollowerViewHolder>() {
        var items: List<GithubFollowers> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
            return FollowerViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
            val item = items[position]

            with(holder.itemView) {
                IDText.text = item.login
                Glide.with(this).load(item.avatarUrl).into(ownerAvatarImage)

                followerCardView.setOnClickListener {
                    startAct(item.login)
                }
            }
        }
    }

    lateinit var listAdapter: FollowerListAdapter

    var login = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_tab_follower, container, false)
        listAdapter = FollowerListAdapter()
        view.followerView.adapter = listAdapter
        view.followerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val bundle = arguments

        bundle?.let {
            login = it.getString("login")
        }


        val followerApi = provideUserApi(activity!!.applicationContext)
            val followerCall = followerApi.getFollowerInfo(login)
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

    fun startAct(login: String) {
        val intent = Intent(activity, OtherProfileActivity::class.java)
        intent.putExtra("login", login)
        startActivity(intent)
    }

}

