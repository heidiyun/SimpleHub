package com.example.user.simplehub.issueFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.fragment.SectionsPageAdapter
import kotlinx.android.synthetic.main.issue_tab_assigned.*
import kotlinx.android.synthetic.main.issue_tab_assigned.view.*
import kotlinx.android.synthetic.main.issue_tab_created.view.*

class Assigned : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.issue_tab_created, container, false)

        setupSubViewPager(view.pager_issue_created)
        view.tab_issue_created.setupWithViewPager(view.pager_issue_created)

        return view

    }

    private fun setupSubViewPager(viewPager: ViewPager) {
        val adpater = SectionsPageAdapter(childFragmentManager)
        adpater.addFragment(AssignedOpen(), "Open")
        adpater.addFragment(AssignedClosed(), "Closed")
        viewPager.adapter = adpater
    }
}