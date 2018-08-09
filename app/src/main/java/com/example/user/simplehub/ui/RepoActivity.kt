package com.example.user.simplehub.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.user.simplehub.R
import com.example.user.simplehub.fragment.SectionsPageAdapter
import com.example.user.simplehub.repo.fragment.Code
import com.example.user.simplehub.repo.fragment.Issue
import com.example.user.simplehub.repo.fragment.PullRequest
import kotlinx.android.synthetic.main.activity_profile_tab.*
import kotlinx.android.synthetic.main.activity_repository.*
import kotlinx.android.synthetic.main.app_bar_repo.*

class RepoActivity : AppCompatActivity(), View.OnClickListener {

    var isFabOpen = false
    var fab_open: Animation? = null
    var fab_close: Animation? = null
    var repoName = ""
    var ownerName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        val bundle = intent.extras
        val fullName = bundle.getString("fullName")
        repoName = bundle.getString("repoName")
        ownerName = bundle.getString("ownerName")

//        val bundle2 = Bundle(2)
//        bundle2.putString("repoName", repoName)
//        bundle2.putString("ownerName", ownerName)
//        val code : Fragment = Code()
//        code.arguments = bundle2
//        Issue().arguments = bundle2
//        PullRequest().arguments = bundle2

        bar_repo_text.text = fullName
        setupWithViewpager(pager_issue, repoName, ownerName)
        tab_issue.setupWithViewPager(pager_issue)


        fab_open = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)

        fab.setOnClickListener(this)
        commit_feed_button.setOnClickListener(this)
        contributor_button.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        val id = v.getId()
        when (id) {
            R.id.fab -> {
                anim()
                if (view.visibility == VISIBLE)
                    view.visibility = INVISIBLE
                else view.visibility = VISIBLE
            }
            R.id.contributor_button -> {
                val intent = Intent(this, ContributorActivity::class.java)
                intent.putExtra("repoName", repoName)
                intent.putExtra("ownerName", ownerName)
                startActivity(intent)

            }
            R.id.commit_feed_button -> {
                val intent = Intent(this, CommitActivity::class.java)
                intent.putExtra("repoName", repoName)
                intent.putExtra("ownerName", ownerName)
                startActivity(intent)
            }
        }
    }

    private fun setupWithViewpager(viewPager: ViewPager, repoName: String, ownerName: String) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        val code = Code()
        val issue = Issue()
        val pullRequest = PullRequest()
        val args = Bundle()
        args.putString("repoName", repoName)
        args.putString("ownerName", ownerName)
        code.arguments = args
        adapter.addFragment(code, "code")
        adapter.addFragment(issue, "Issue")
        adapter.addFragment(pullRequest, "Pull Request")
        viewPager.adapter = adapter
    }

    fun anim() {
        if (isFabOpen) {
            text_commit.startAnimation(fab_close)
            text_contributor.startAnimation(fab_close)
            contributor_button.startAnimation(fab_close)
            commit_feed_button.startAnimation(fab_close)
            contributor_button.setClickable(false)
            commit_feed_button.setClickable(false)
            isFabOpen = false
        } else {
            text_commit.startAnimation(fab_open)
            text_contributor.startAnimation(fab_open)
            contributor_button.startAnimation(fab_open)
            commit_feed_button.startAnimation(fab_open)
            contributor_button.setClickable(true)
            commit_feed_button.setClickable(true)
            isFabOpen = true
        }
    }

}
