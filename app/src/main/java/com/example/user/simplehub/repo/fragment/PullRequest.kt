package com.example.user.simplehub.repo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubPulls
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.pulls.fragment.PullsListAdapter
import com.example.user.simplehub.pulls.fragment.PullsViewHolder
import com.example.user.simplehub.ui.PullsActivity
import com.example.user.simplehub.ui.RepoActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_closed.view.*
import kotlinx.android.synthetic.main.item_issue_created_closed.view.*




class PullRequest: Fragment() {

    inner class RepoPullsListAdapter : RecyclerView.Adapter<PullsViewHolder>() {
        var items: List<GithubPulls> = emptyList()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullsViewHolder {
            return PullsViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: PullsViewHolder, position: Int) {
            val item = items[position]

            with(holder.itemView) {
                if (item.pullRequest != null) {


                    pullsTitle.text = item.title
                    pullsDate.text = item.PullsDate
                    pullsNumber.text = item.number.toString()
                    pullsRepo.text = ownerName + "/" + repoName
                    pullsUser.text = item.user.login
                } else {

                }
            }
        }


    }

    lateinit var pullsListAdapter: RepoPullsListAdapter
    var repoName = ""
    var ownerName = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.created_tab_closed, container, false)

        arguments?.let {
            repoName = it.getString("repoName")
            ownerName = it.getString("ownerName")

        }

        pullsListAdapter = RepoPullsListAdapter()
        view.created_closed_view.adapter = pullsListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideUserApi(activity!!.applicationContext)
        val call = issueApi.getRepoPulls(ownerName, repoName, "all", "all")
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                for (i in 0 .. it.size-1) {
                    if (it[i].pullRequest != null) {
                        pullsListAdapter.items = it
                        pullsListAdapter.notifyDataSetChanged()
                    }
                }
            }
        }, {

        })
        return view
    }
}