package com.example.user.simplehub.pullsFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.fragment.SectionsPageAdapter
import kotlinx.android.synthetic.main.issue_tab_created.view.*
import kotlinx.android.synthetic.main.pulls_tab_created.view.*


class Mentioned : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pulls_tab_created, container, false)
        setupSubViewPager(view.pager_pulls_created)
        view.tab_pulls_created.setupWithViewPager(view.pager_pulls_created)

        return view

    }

    private fun setupSubViewPager(viewPager: ViewPager) {
        val adpater = SectionsPageAdapter(childFragmentManager)
        adpater.addFragment(MentionedOpen(), "Open")
        adpater.addFragment(MentionedClosed(), "Closed")
        viewPager.adapter = adpater
    }
}