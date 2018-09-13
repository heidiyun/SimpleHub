package com.example.user.simplehub.ui.fragment.repo

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
import com.example.user.simplehub.ui.RepoActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.issue_tab_mentioned.view.*
import kotlinx.android.synthetic.main.item_repo_issue.view.*

class RepoIssuesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_repo_issue, parent, false)
)

class RepoIssueListAdapter : RecyclerView.Adapter<RepoIssuesViewHolder>() {
    var items: List<GithubPulls> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoIssuesViewHolder {
        return RepoIssuesViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: RepoIssuesViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {

            Log.i(RepoActivity::class.java.simpleName, "title: ${item.title}")

            issueTitle.text = item.title
            issueDate.text = item.PullsDate
            issueNumber.text = item.number.toString()
            issueUser.text = item.user.login

        }
    }


}


class Issue : Fragment() {

    private lateinit var issueListAdapter: RepoIssueListAdapter
    private lateinit var repoName: String
    private lateinit var ownerName: String

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater
                .inflate(R.layout.issue_tab_mentioned, container, false)

        arguments?.let {
            repoName = it.getString("repoName")
            ownerName = it.getString("ownerName")

        }

        issueListAdapter = RepoIssueListAdapter()
        view.repo_issue_view.adapter = issueListAdapter
        view.repo_issue_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideUserApi(activity!!.applicationContext)
        val call = issueApi
                .getRepoPulls(ownerName, repoName, "all", "all")
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                for (i in 0 until it.size) {
                    if (it[i].pullRequest == null) {
                        issueListAdapter.items = it
                        issueListAdapter.notifyDataSetChanged()
                    }
                }
            }
        }, {

        })
        return view
    }
}

