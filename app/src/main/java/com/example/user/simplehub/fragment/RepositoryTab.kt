package com.example.user.simplehub.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideGithubApi
import com.example.user.simplehub.ui.SearchListAdapter
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.profile_tab_repository.*
import kotlinx.android.synthetic.main.profile_tab_repository.view.*

class RepositoryTab: Fragment() {
    lateinit var listAdapter: SearchListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_tab_repository, container,false)
        listAdapter = SearchListAdapter()
        view.repositoryView.adapter = listAdapter
        view.repositoryView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val githubApi = provideGithubApi(activity!!.applicationContext)

        val call = githubApi.getUserInfo()
        call.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    listAdapter.items = it
                    listAdapter.notifyDataSetChanged()
                }
            }
        }, {

        })

        return view
    }
}

