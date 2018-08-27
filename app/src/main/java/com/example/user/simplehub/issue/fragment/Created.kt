package com.example.user.simplehub.issue.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.fragment.SectionsPageAdapter

import kotlinx.android.synthetic.main.issue_tab_created.view.*

class Created : Fragment() {

    val createdOpen = Open()
    val createdClosed = Closed()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.issue_tab_created, container, false)

        setupSubViewPager(view.pager_issue_created)
        view.tab_issue_created.setupWithViewPager(view.pager_issue_created)

        createdOpen.setOnListener(object: Listener {
            override fun getFilter(): String = "created"

            override fun getState(): String = "open"

        })

        createdClosed.setOnListener(object: Listener {
            override fun getFilter(): String = "created"

            override fun getState(): String = "closed"

        })

        return view
    }

    private fun setupSubViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(createdOpen, "Open")
        adapter.addFragment(createdClosed, "Closed")
        viewPager.adapter = adapter
    }

}