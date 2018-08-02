package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.fragment.SectionsPageAdapter
import com.example.user.simplehub.repo.fragment.Code
import com.example.user.simplehub.repo.fragment.Issue
import com.example.user.simplehub.repo.fragment.PullRequest
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_profile_tab.*
import kotlinx.android.synthetic.main.app_bar_repo.*

class RepoActivity: AppCompatActivity() {
    companion object {
        var repoName: String = ""
        var fullName: String = ""
        var ownerName: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        bar_repo_text.text = fullName
        setupWithViewpager(pager_issue)
        tab_issue.setupWithViewPager(pager_issue)


    }

    private fun setupWithViewpager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(Code(), "code")
        adapter.addFragment(Issue(), "Issue")
        adapter.addFragment(PullRequest(), "Pull Request")
        viewPager.adapter = adapter
    }
}