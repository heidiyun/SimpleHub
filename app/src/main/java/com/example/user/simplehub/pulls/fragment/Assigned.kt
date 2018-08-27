package com.example.user.simplehub.pulls.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.fragment.SectionsPageAdapter
import kotlinx.android.synthetic.main.issue_tab_assigned.view.*

class Assigned : Fragment() {

    val assignedOpen = Open()
    val assignedClosed = Closed()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.issue_tab_assigned, container, false)

        setupSubViewPager(view.pager_issue_assigned)
        view.tab_issue_assigned.setupWithViewPager(view.pager_issue_assigned)

        assignedOpen.setOnListener(object: PullListener {
            override fun getFilter(): String = "assigned"

            override fun getState(): String = "open"

        })

        assignedClosed.setOnListener(object: PullListener {
            override fun getFilter(): String = "assigned"

            override fun getState(): String = "closed"

        })

        return view
    }

    private fun setupSubViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(assignedOpen, "Open")
        adapter.addFragment(assignedClosed, "Closed")
        viewPager.adapter = adapter
    }
}