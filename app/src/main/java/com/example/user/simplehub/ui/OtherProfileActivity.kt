package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.api.removeToken
import com.example.user.simplehub.ui.fragment.profile.*
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.startActivity

class OtherProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val fragment = Search()

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)

        val bundle = intent.extras
        val login = bundle.getString("login")

        profile.text = getString(R.string.profile)
        setSupportActionBar(navigationBar)
        supportActionBar!!.title = null

        val toggle = ActionBarDrawerToggle(
                this, profileDrawerLayout, navigationBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        profileDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        val userApi = provideUserApi(this)
        val userFollowingCall = userApi.getAuthFollowingInfo()

        val userCall = userApi.getUser(login)
        userCall.enqueue({ response ->
            val result = response.body()
            result?.let {

                userFollowingCall.enqueue({ response ->
                    val result2 = response.body()
                    result2?.let { following ->
                        for (i in 0 until following.size) {
                            println("following name: ${following[i].login}")
                            if (IDText.text == ProfileActivity.ownerLogin) {
                                followButton.visibility = View.GONE
                            } else if (following[i].login == IDText.text) {
                                followButton.text = getString(R.string.unfollow)
                            }
                        }
                    }
                }, { exception ->
                    exception.printStackTrace()
                })

                nameText.text = it.name
                IDText.text = it.login
                if (it.email == null) {
                    emailText.visibility = View.GONE
                } else {
                    emailText.text = it.email
                }

                nameText_drawer.text = ProfileActivity.ownerName
                IDText_drawer.text = ProfileActivity.ownerLogin

                Glide.with(this).load(it.avatarUrl).into(ownerAvatarImage)
                Glide.with(this).load(ProfileActivity.ownerAvatarUrl).into(ownerAvatarImage_drawer)


                setupViewPager(pager, it.login)


            }
            progressBar.visibility = View.GONE

        }, {

        })

        tabLayout.setupWithViewPager(pager)

        followButton.setOnClickListener {
            if (followButton.text == "Unfollow") {
                val call = userApi.deleteFollowing(IDText.text as String)
                call.enqueue({ response ->
                    val status = response.code()
                    if (status in 200..299)
                        followButton.text = getString(R.string.follow)

                }, { exception ->
                    exception.printStackTrace()
                })
            } else {
                val call = userApi.putFollowing(IDText.text as String)
                call.enqueue({ response ->
                    val status = response.code()
                    if (status in 200..299)
                        followButton.text = getString(R.string.unfollow)
                }, { exception ->
                    exception.printStackTrace()
                })
            }
        }
    }

    private fun setupViewPager(viewPager: ViewPager, login: String) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        val overview = OverviewTab()
        val repository = RepositoryTab()
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
        }
        profileDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        when {
            profileDrawerLayout.isDrawerOpen(GravityCompat.START) -> {
                profileDrawerLayout.closeDrawer(GravityCompat.START)
            }
            fragment.isVisible -> {
                searchView.isIconified = true
                searchView.clearFocus()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fragment_search, menu)
        (menu?.findItem(R.id.menuItemSearch)?.actionView as SearchView).apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    println("newText $newText")
                    fragment.setApi(newText, 1)

                    return true
                }
            })

            queryHint = "Search User"

            setOnSearchClickListener {
                supportFragmentManager.beginTransaction()
                        .add(R.id.contents, fragment)
                        .commit()
            }
            searchView = this
            setOnCloseListener {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
                false
            }
        }

        signoutButton.setOnClickListener {
            removeToken(this)
            Log.i(ProfileActivity.TAG, "sign out button")
        }
        menuInflater.inflate(R.menu.main, menu)

        return true

    }
}