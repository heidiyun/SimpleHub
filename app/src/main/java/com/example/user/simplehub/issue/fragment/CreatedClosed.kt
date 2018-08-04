package com.example.user.simplehub.issue.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_closed.view.*

class CreatedClosed: Fragment() {

    lateinit var issueListAdapter: IssueListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.created_tab_closed, container, false)


        issueListAdapter = IssueListAdapter()
        view.created_closed_view.adapter = issueListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideUserApi(activity!!.applicationContext)
        val call = issueApi.getIssue("created", "closed")
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                for (i in 0..it.size - 1) {
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