package com.example.user.simplehub.fragment.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.fragment.profile.SectionsPageAdapter
import kotlinx.android.synthetic.main.issue_tab_created.view.*

class Mentioned : Fragment() {

    val mentionedOpen = Open()
    val mentionedClosed = Closed()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.issue_tab_created, container, false)
        setupSubViewPager(view.pager_issue_created)
        view.tab_issue_created.setupWithViewPager(view.pager_issue_created)

        mentionedOpen.setOnListener(object: Listener {
            override fun getFilter(): String = "mentioned"

            override fun getState(): String = "open"

        })

        mentionedClosed.setOnListener(object: Listener {
            override fun getFilter(): String = "mentioned"

            override fun getState(): String = "closed"

        })

        return view

    }

    private fun setupSubViewPager(viewPager: ViewPager) {
        val adpater = SectionsPageAdapter(childFragmentManager)
        adpater.addFragment(mentionedOpen, "Open")
        adpater.addFragment(mentionedClosed, "Closed")
        viewPager.adapter = adpater
    }
}