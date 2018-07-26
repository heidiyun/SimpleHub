package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepo
import com.example.user.simplehub.api.provideGithubApi
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.fragment.*
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_tab.*
import kotlinx.android.synthetic.main.activity_repository.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.profile_tab_repository.*
import kotlinx.android.synthetic.main.repo_item.view.*
import org.jetbrains.anko.toast

//class MyAdapter(val fm: FragmentManager): FragmentPagerAdapter(fm) {
//     var mFragments = arrayListOf<Fragment>()
//     var mFragmentTitles = arrayListOf<String>()
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

class RepoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
)

class SearchListAdapter : RecyclerView.Adapter<RepoViewHolder>() {
    var items: List<GithubRepo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            repoNameText.text = item.name
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


        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener(this)


//        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
//        tabLayout.addTab(tabLayout.newTab().setText("Overview"))
//        tabLayout.addTab(tabLayout.newTab().setText("Repository"))
//        tabLayout.addTab(tabLayout.newTab().setText("Stars"))
//        tabLayout.addTab(tabLayout.newTab().setText("Following"))
//        tabLayout.addTab(tabLayout.newTab().setText("Followers"))
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)


//        val pagerAdapter = TabPagerAdapter(supportFragmentManager, tabLayout.tabCount)
//        pager.adapter = pagerAdapter
//        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab) {
//                pager.setCurrentItem(tab.position)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//            }
//
//        })



        setupViewPager(pager)
        tabLayout.setupWithViewPager(pager)
        toast("ProfileActivity")

//        val viewPager: ViewPager = findViewById(R.id.viewpager)
//        val adapter: MyAdapter = MyAdapter(getSupportFragmentManager())
//        adapter.addFragment(MyFragment(), "Overview")
//        adapter.addFragment(MyFragment(), "Repository")
//        adapter.addFragment(MyFragment(), "Follow")
//        adapter.addFragment(MyFragment(), "Stars")
//
//        val tabLayout: TabLayout = findViewById(R.id.tabs)
//        tabLayout.setupWithViewPager(viewPager)


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

                    Glide.with(this).load(it.avatarUrl).into(ownerAvatarImage)


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
        drawer_layout.closeDrawer(GravityCompat.START)

        when (item.itemId) {
            R.id.nav_profile-> {
                // Handle the camera action
            }
            R.id.nav_pullrequest -> {

            }
            R.id.nav_issue-> {

            }

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.home -> {
                Log.i(TAG, "TTTTTT")
                drawer_layout.openDrawer(GravityCompat.START)
                return true}
            R.id.action_settings -> {
                Log.i(TAG, "WWWWWWW")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



}