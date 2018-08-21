package com.example.user.simplehub.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.user.simplehub.FragmentExample
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.fragment.*
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.startActivity

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val TAG = ProfileActivity::class.java.simpleName
    }

    val fragment = FragmentExample()
    var listener: SearchView.OnCloseListener = SearchView.OnCloseListener {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        false
    }

    var searchview: SearchView? = null

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

        followButton.visibility = View.GONE

        val userApi = provideUserApi(this)
        val userCall = userApi.getUserInfo()
        userCall.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    nameText.text = it.name
                    IDText.text = it.login
                    if (it.email == null) {
                        emailText.visibility = View.GONE
                    } else {
                        emailText.text = it.email
                    }
                    nameText_drawer.text = it.name
                    IDText_drawer.text = it.login

                    Glide.with(this).load(it.avatarUrl).into(ownerAvatarImage)
                    Glide.with(this).load(it.avatarUrl).into(ownerAvatarImage_drawer)

                    setupViewPager(pager, it.login)
                }
            }
        }, {

        })

        tabLayout.setupWithViewPager(pager)

    }

    private fun setupViewPager(viewPager: ViewPager, login: String) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        val overview = OverviewTab()
        val repository: RepositoryTab = RepositoryTab()
        val star = StarTab()
        val follower = FollowerTab()
        val following = FollowingTab()
        val args = Bundle()
        args.putString("login", login)
        overview.arguments = args
        repository.arguments = args
        star.arguments = args
        follower.arguments = args
        following.arguments = args
        adapter.addFragment(overview, "Overview")
        adapter.addFragment(repository, "Repository")
        adapter.addFragment(star, "Star")
        adapter.addFragment(follower, "Follower")
        adapter.addFragment(following, "Following")
        viewPager.adapter = adapter
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
            println("drawer open")
            profileDrawerLayout.closeDrawer(GravityCompat.START)
        } else if (fragment.isVisible) {
            println("fragment visible")
//            supportFragmentManager.beginTransaction().remove(fragment).commit()
            listener.onClose()
            searchview?.isIconified = true

        } else {
            println("super")
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        signoutButton.setOnClickListener {
//            removeToken(this)
//            Log.i(TAG, "sign out button")
//        }
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fragment_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.menuItemSearch)?.actionView as SearchView).apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    println("newText $newText")
                    fragment.setApi(newText)

                    return true
                }
            })

            queryHint = "Search User"

            setOnSearchClickListener {
                supportFragmentManager.beginTransaction()
                        .add(R.id.contents, fragment)
                        .commit()
            }
            searchview = this

            setOnCloseListener(listener)

//            setOnCloseListener {
//                supportFragmentManager.beginTransaction().remove(fragment).commit()
//                println("close click!")
//                false
//            }
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }
}