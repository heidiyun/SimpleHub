package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.Adapter.ContributorListAdapter
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_repo_contributor.*
import kotlinx.android.synthetic.main.app_bar_navigation.*


class ContributorActivity : AppCompatActivity() {

    lateinit var listAdapter: ContributorListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_contributor)

        profile.text = getString(R.string.contributors)

        listAdapter = ContributorListAdapter()

        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        val ownerName = bundle.getString("ownerName")

        contributorView.adapter = listAdapter
        contributorView.layoutManager = LinearLayoutManager(this)

        val contributorApi = provideUserApi(this)
        val call = contributorApi
                .getRepoContributors(ownerName, repoName)

        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()
            }
        }, {

        })

    }
}