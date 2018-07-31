package com.example.user.simplehub.pullsFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideIssueApi
import com.example.user.simplehub.api.providePullsApi
import com.example.user.simplehub.issueFragment.IssueListAdapter
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_closed.view.*
import kotlinx.android.synthetic.main.created_tab_opend.view.*

class AssignedOpen : Fragment() {

    lateinit var issueListAdapter: PullsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.created_tab_closed, container, false)

        issueListAdapter = PullsListAdapter()
        view.created_closed_view.adapter = issueListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        issueListAdapter = PullsListAdapter()
        view.created_closed_view.adapter = issueListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val pullsApi = providePullsApi(activity!!.applicationContext)
        val call = pullsApi.getPulls("assigned", "open")
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
