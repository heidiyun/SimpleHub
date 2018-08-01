package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
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
import com.example.user.simplehub.fragment.SectionsPageAdapter
import com.example.user.simplehub.pullsFragment.Assigned
import com.example.user.simplehub.pullsFragment.Created
import com.example.user.simplehub.pullsFragment.Mentioned
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_profile_tab.*
import kotlinx.android.synthetic.main.activity_pull_request.*
import kotlinx.android.synthetic.main.app_bar_pulls.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.startActivity

class PullsActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)
        setSupportActionBar(bar_pulls)
        supportActionBar!!.title = null

        setupViewPager(pager_issue)
        tab_issue.setupWithViewPager(pager_issue)

        val toggle = ActionBarDrawerToggle(
                this, pulls_drawer, bar_pulls, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        pulls_drawer.addDrawerListener(toggle)
        toggle.syncState()

        navView_pulls.setNavigationItemSelectedListener(this)

        val githubApi = provideUserApi(this)
        val call = githubApi.getUserInfo()
        call.enqueue({
            response ->
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.setChecked(true)

        when (item.itemId) {
            R.id.nav_profile-> {
                startActivity<ProfileActivity>()
            }
            R.id.nav_pullrequest -> {
                startActivity<PullsActivity>()

            }
            R.id.nav_issue-> {
                startActivity<IssueActivity>()
            }

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        pulls_drawer.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onBackPressed() {
        if (pulls_drawer.isDrawerOpen(GravityCompat.START)) {
            pulls_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        signoutButton.setOnClickListener {
            removeToken(this)
            Log.i(ProfileActivity.TAG, "sign out button")
        }
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {

            R.id.action_settings -> {
                startActivity<MainActivity>()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}