package com.example.user.simplehub.fragment.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_opend.*
import kotlinx.android.synthetic.main.created_tab_opend.view.*

class Open: Fragment() {

    lateinit var issueListAdapter: IssueListAdapter
    lateinit var listener: Listener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.created_tab_opend, container, false)

        issueListAdapter = IssueListAdapter()
        view.created_open_view.adapter = issueListAdapter
        view.created_open_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val issueApi = provideUserApi(activity!!.applicationContext)
        val call = issueApi.getIssue(listener.getFilter(), listener.getState())

        call.enqueue({
            response ->
            val result = response.body()
            result?.let {

                for (i in 0 until it.size) {
                    if (it[i].pullRequest == null) {
                        issueListAdapter.items.add(it[i])
                        issueListAdapter.notifyDataSetChanged()
                    }
                }

                if (issueListAdapter.items.isEmpty()) {
                    issueText.visibility = View.VISIBLE
                }
            }
        }, {

        })

        return view
    }

    fun setOnListener(listener: Listener) {
        this.listener = listener
    }

}