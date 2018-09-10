package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.api.removeToken
import com.example.user.simplehub.fragment.profile.SectionsPageAdapter
import com.example.user.simplehub.fragment.issue.Assigned
import com.example.user.simplehub.fragment.issue.Created
import com.example.user.simplehub.fragment.issue.Detail
import com.example.user.simplehub.fragment.issue.Mentioned
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_issue.*
import kotlinx.android.synthetic.main.activity_profile_tab.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.startActivity

class IssueActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)
        setSupportActionBar(navigationBar)
        supportActionBar?.title = null
        // 원래 적용되는 이름이 있다. 없애주는 역할.

        val toggle = ActionBarDrawerToggle(
                this, issue_drawer, navigationBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        issue_drawer.addDrawerListener(toggle)
        toggle.syncState()

        navView_issue.setNavigationItemSelectedListener(this)

        profile.text = "issue"

        setupViewPager(pager_issue)
        tab_issue.setupWithViewPager(pager_issue)

        val githubApi = provideUserApi(this)
        val call = githubApi.getUserInfo()
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                nameText_drawer.text = it.name
                IDText_drawer.text = it.login
                Glide.with(this).load(it.avatarUrl).into(ownerAvatarImage_drawer)
            }
        }, {

        })
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(Created(), "Created")
        adapter.addFragment(Assigned(), "Assigned")
        adapter.addFragment(Mentioned(), "Mentioned")
        viewPager.adapter = adapter
    }


    override fun onBackPressed() {
        if (issue_drawer.isDrawerOpen(GravityCompat.START)) {
            issue_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true

        when (item.itemId) {
            R.id.nav_profile -> {
                startActivity<ProfileActivity>()
            }

            R.id.nav_pullrequest -> {
                startActivity<PullsActivity>()
            }

            R.id.nav_issue -> {
                startActivity<IssueActivity>()
            }

        }
        issue_drawer.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        signoutButton.setOnClickListener {
            removeToken(this)
            Log.i(ProfileActivity.TAG, "sign out button")
        }
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
