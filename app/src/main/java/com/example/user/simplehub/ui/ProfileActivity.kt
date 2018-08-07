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
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubFollowers
import com.example.user.simplehub.api.model.GithubRepo
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.api.removeToken
import com.example.user.simplehub.fragment.*
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.item_follower.view.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.repo_item.*
import kotlinx.android.synthetic.main.repo_item.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

//class MyAdapter(val fm: FragmentManager): FragmentPagerAdapter(fm) {
//     var mFragmentTitles = arrayListOf<String>()//     var mFragments = arrayListOf<Fragment>()

//
//    fun addFragment(fragment: Fragment, title: String) {
//        mFragments.add(fragment)
//        mFragmentTitles.add(title)
//    }
//
//    override fun getPageTitle(position: Int): CharSequence {
//        return mFragmentTitles.get(position)
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return mFragments.get(position)
//    }
//
//    override fun getCount(): Int {
//        return mFragments.size
//    }
//
//}
//
//class MyFragment: Fragment() {
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val resId: Int = R.layout.activity_profile
//        return inflater.inflate(resId, null)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }
//}


class FollowerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
)

class FollowerListAdapter : RecyclerView.Adapter<FollowerViewHolder>() {
    var items: List<GithubFollowers> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        return FollowerViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            IDText.text = item.login
            Glide.with(this).load(item.avatarUrl).into(ownerAvatarImage)
        }
    }

}

class ProfileActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val TAG = ProfileActivity::class.java.simpleName
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)
        setSupportActionBar(navigationBar)
        supportActionBar!!.title = null

        val toggle = ActionBarDrawerToggle(
                this, profileDrawerLayout, navigationBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        profileDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        setupViewPager(pager)
        tabLayout.setupWithViewPager(pager)
        toast("ProfileActivity")

        val userApi = provideUserApi(this)
        val userCall = userApi.getUserInfo()
        userCall.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    nameText.text = it.name
                    IDText.text = it.login
                    emailText.text = it.email
                    nameText_drawer.text = it.name
                    IDText_drawer.text = it.login

                    Glide.with(this).load(it.avatarUrl).into(ownerAvatarImage)
                    Glide.with(this).load(it.avatarUrl).into(ownerAvatarImage_drawer)
                }
            }
        }, {

        })


    }



    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(OverviewTab(), "Overview")
        adapter.addFragment(RepositoryTab(), "Repository")
        adapter.addFragment(StarTab(), "Star")
        adapter.addFragment(FollowerTab(), "Follower")
        adapter.addFragment(FollowingTab(), "Following")
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

        profileDrawerLayout.closeDrawer(GravityCompat.START)

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
            Log.i(TAG, "sign out button")
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