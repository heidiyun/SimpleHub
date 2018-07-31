package com.example.user.simplehub.issueFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideIssueApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_closed.view.*




class CreatedClosed: Fragment() {

    lateinit var issueListAdapter: IssueListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.created_tab_closed, container, false)


        issueListAdapter = IssueListAdapter()
        view.created_closed_view.adapter = issueListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideIssueApi(activity!!.applicationContext)
        val call = issueApi.getIssue("created", "closed")
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