package com.example.user.simplehub.issue.fragment

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
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.IssueActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_opend.view.*
import kotlinx.android.synthetic.main.item_issue_created.view.*

class IssueViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_issue_created, parent, false)
)

class IssueListAdapter : RecyclerView.Adapter<IssueViewHolder>() {
    var items: List<GithubIssue> = emptyList()
    var pullsUrl: String = ""



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            Log.i(IssueActivity::class.java.simpleName, "item title, ${item.title}")
            Log.i(IssueActivity::class.java.simpleName, "item closedDate, ${item.closedDate}")
            Log.i(IssueActivity::class.java.simpleName, "item fullName ${item.repository.fullName}")

            issueNameText.text = item.title
            issueDate.text = item.closedDate
            issueOwner.text = item.repository.fullName
            pullsUrl = item.pullUrl
        }
    }



}

val issueListAdapter = IssueListAdapter()
val pullsUrl = "repos/ ${issueListAdapter.pullsUrl}/pulls"



class CreatedOpen: Fragment() {
    lateinit var issueListAdapter: IssueListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.created_tab_opend, container, false)


        issueListAdapter = IssueListAdapter()
        view.created_open_view.adapter = issueListAdapter
        view.created_open_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideUserApi(activity!!.applicationContext)
        val call = issueApi.getIssue("created", "open")
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