package com.example.user.simplehub.pullsFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubIssue
import com.example.user.simplehub.api.model.GithubPulls
import com.example.user.simplehub.api.provideIssueApi
import com.example.user.simplehub.api.providePullsApi
import com.example.user.simplehub.ui.IssueActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_closed.view.*
import kotlinx.android.synthetic.main.created_tab_opend.view.*
import kotlinx.android.synthetic.main.item_issue_created.view.*
import kotlinx.android.synthetic.main.item_pulls.view.*

class PullsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_pulls, parent, false)
)

class PullsListAdapter : RecyclerView.Adapter<PullsViewHolder>() {
    var items: List<GithubPulls> = emptyList()
    var pullsUrl: String = ""



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullsViewHolder {
        return PullsViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: PullsViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {

            pullsNameText.text = item.title
            pullsDate.text = item.createdDate
            pullsNumber.text = item.number.toString()
            pullsRepo.text = item.repository.fullName
            pullsUser.text = item.user.login
//
//            issueNameText.text = item.title
//            issueDate.text = item.closedDate
//            issueOwner.text = item.repository.fullName
//            pullsUrl = item.pullUrl
        }
    }



}




class CreatedOpen: Fragment() {
    lateinit var issueListAdapter: PullsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.created_tab_closed, container, false)


        issueListAdapter = PullsListAdapter()
        view.created_closed_view.adapter = issueListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val pullsApi = providePullsApi(activity!!.applicationContext)
        val call = pullsApi.getPulls("created", "open")
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                issueListAdapter.items = it
                issueListAdapter.notifyDataSetChanged()
            }
        }, {

        })

        return view
    }
}