package com.example.user.simplehub.ui.fragment.pulls

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_closed.*
import kotlinx.android.synthetic.main.created_tab_closed.view.*

interface PullListener {
    fun getFilter(): String
    fun getState(): String
}

class Closed: Fragment() {
    private lateinit var issueListAdapter: PullsListAdapter
    private lateinit var listener: PullListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.created_tab_closed, container, false)


        issueListAdapter = PullsListAdapter()
        view.created_closed_view.adapter = issueListAdapter
        view.created_closed_view.layoutManager = LinearLayoutManager(requireContext())

        val issueApi = provideUserApi(activity!!.applicationContext)
        val call = issueApi.getIssue(listener.getFilter(), listener.getState())
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                for (i in 0 until it.size) {
                    if (it[i].pullRequest != null) {
                        issueListAdapter.items.add(it[i])
                        issueListAdapter.notifyDataSetChanged()
                    }
                }

                if (issueListAdapter.items.isEmpty()) {
                    issueTextClosed.text = getString(R.string.no_pulls)
                    issueTextClosed.visibility = View.VISIBLE
                }
            }
        }, {

        })

        return view
    }

    fun setOnListener(listener: PullListener) {
        this.listener = listener
    }
}