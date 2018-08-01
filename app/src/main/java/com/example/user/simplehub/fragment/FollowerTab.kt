package com.example.user.simplehub.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.FollowerListAdapter
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.profile_tab_follower.view.*

class FollowerTab : Fragment() {
    lateinit var listAdapter: FollowerListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_tab_follower, container,false)
        listAdapter = FollowerListAdapter()
        view.followerView.adapter = listAdapter
        view.followerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)


        val followerApi = provideUserApi(activity!!.applicationContext)
        val followerCall = followerApi.getFollowerInfo()
        followerCall.enqueue({
            response ->
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