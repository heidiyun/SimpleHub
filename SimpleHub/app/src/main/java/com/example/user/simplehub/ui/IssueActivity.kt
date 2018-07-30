package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubIssue
import com.example.user.simplehub.api.model.GithubRepo
import com.example.user.simplehub.api.provideIssueApi
import com.example.user.simplehub.api.removeToken
import com.example.user.simplehub.fragment.SectionsPageAdapter
import com.example.user.simplehub.issueFragment.*
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_issue.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.activity_profile_tab.*
import kotlinx.android.synthetic.main.app_bar_issue.*
import kotlinx.android.synthetic.main.issue_tab_created.*
import kotlinx.android.synthetic.main.item_issue_created.view.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.repo_item.view.*
import org.jetbrains.anko.startActivity


class IssueViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_issue_created, parent, false)
)

class IssueListAdapter : RecyclerView.Adapter<IssueViewHolder>() {
    var items: List<GithubIssue> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            Log.i(IssueActivity::class.java.simpleName, "item title, ${item.title}")
            Log.i(IssueActivity::class.java.simpleName, "item closedDate, ${item.closedDate}")
            Log.i(IssueActivity::class.java.simpleName, "item fullName ${item.repository.fullName}")

            issueNameText.text = item.title
            issueDate.text = item.closedDate
            issueOwner.text = item.repository.fullName
        }
    }

}


class IssueActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)
        setSupportActionBar(bar_issue)
        supportActionBar!!.title = null
        // 원래 적용되는 이름이 있다. 없애주는 역할.

        val toggle = ActionBarDrawerToggle(
                this, issue_drawer, bar_issue, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        issue_drawer.addDrawerListener(toggle)
        toggle.syncState()

        navView_issue.setNavigationItemSelectedListener(this)


//        setupSubViewPager(pager_issue_created)
//        tab_issue_crated.setupWithViewPager(pager_issue_created)

        setupViewPager(pager_issue)
        tab_issue.setupWithViewPager(pager_issue)



    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(Created(), "Created")
        adapter.addFragment(Assigned(), "Assigned")
        adapter.addFragment(Mentioned(), "Mentioned")
        adapter.addFragment(Participated(), "Participated")
        viewPager.adapter = adapter
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.setChecked(true)

        when (item.itemId) {
            R.id.nav_profile-> {
                startActivity<ProfileActivity>()
            }
            R.id.nav_pullrequest -> {

            }
            R.id.nav_issue-> {
                startActivity<IssueActivity>()
            }

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        issue_drawer.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onBackPressed() {
        if (profileDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            profileDrawerLayout.closeDrawer(GravityCompat.START)
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