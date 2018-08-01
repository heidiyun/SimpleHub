package com.example.user.simplehub.pullsFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.fragment.SectionsPageAdapter
import kotlinx.android.synthetic.main.issue_tab_assigned.view.*

class Mentioned : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.issue_tab_assigned, container, false)

        setupSubViewPager(view.pager_issue_assigned)
        view.tab_issue_assigned.setupWithViewPager(view.pager_issue_assigned)

        return view
    }

    private fun setupSubViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(childFragmentManager)
        adapter.addFragment(MentionedOpen(), "Open")
        adapter.addFragment(MentionedClosed(), "Closed")
        viewPager.adapter = adapter
    }
}