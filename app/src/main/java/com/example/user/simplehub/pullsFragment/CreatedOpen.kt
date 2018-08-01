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
import com.example.user.simplehub.api.model.GithubPulls
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.PullsActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_closed.view.*
import kotlinx.android.synthetic.main.item_issue_created_closed.view.*

class PullsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_issue_created_closed, parent, false)
)

class PullsListAdapter : RecyclerView.Adapter<PullsViewHolder>() {
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
                Log.i(PullsActivity::class.java.simpleName, "들어옴")
                Log.i(PullsActivity::class.java.simpleName, "pullsTitle: ${item.title}")
                Log.i(PullsActivity::class.java.simpleName, "pullsDate: ${item.PullsDate}")
                Log.i(PullsActivity::class.java.simpleName, "pullsNumber: ${item.number}")
                Log.i(PullsActivity::class.java.simpleName, "pullsRepo: ${item.repository.repoName}")
                Log.i(PullsActivity::class.java.simpleName, "pullsUser: ${item.user.login}")

                pullsTitle.text = item.title
                pullsDate.text = item.PullsDate
                pullsNumber.text = item.number.toString()
                pullsRepo.text = item.repository.repoName
                pullsUser.text = item.user.login
            } else {

            }
        }
    }


}


class CreatedOpen : Fragment() {
    lateinit var issueListAdapter: PullsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.created_tab_closed, container, false)


        issueListAdapter = PullsListAdapter()
        view.created_closed_view.adapter = issueListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideUserApi(activity!!.applicationContext)
        val call = issueApi.getIssuePulls("created", "open")
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                for (i in 0 .. it.size-1) {
                    if (it[i].pullRequest != null) {
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
