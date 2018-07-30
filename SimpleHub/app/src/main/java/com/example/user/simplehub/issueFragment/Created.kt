package com.example.user.simplehub.issueFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideIssueApi
import com.example.user.simplehub.fragment.SectionsPageAdapter
import com.example.user.simplehub.ui.IssueListAdapter
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.created_tab_opend.*
import kotlinx.android.synthetic.main.issue_tab_created.*
import kotlinx.android.synthetic.main.issue_tab_created.view.*

class Created : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.issue_tab_created, container, false)

        setupSubViewPager(view.pager_issue_created)
        view.tab_issue_created.setupWithViewPager(view.pager_issue_created)

        return view
    }

    private fun setupSubViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(Opened(), "Opened")
        adapter.addFragment(Closed(), "Closed")
        viewPager.adapter = adapter
    }

}