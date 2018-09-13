package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.Adapter.CommitListAdapter
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_repo_commits.*
import kotlinx.android.synthetic.main.app_bar_navigation.*


class CommitActivity : AppCompatActivity() {

    private lateinit var listAdapter: CommitListAdapter
    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_commits)

        profile.text = getString(R.string.commit)
        listAdapter = CommitListAdapter()

        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        val ownerName = bundle.getString("ownerName")

        val layoutManager = LinearLayoutManager(this)

        commitView.adapter = listAdapter
        commitView.layoutManager = layoutManager

        commitView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = commitView.layoutManager.childCount
                val totalItemCount = commitView.layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >=
                        totalItemCount) {
                    // 최하단이벤트를 받아올 수 있게 되었다.!!
                    setApi(ownerName, repoName, ++page)
                }
            }
        })
        setApi(ownerName, repoName, 1)
    }

    fun setApi(ownerName: String, repoName: String, page: Int) {
        this.page = page
        val contributorApi = provideUserApi(this)
        val call = contributorApi.getRepoCommits(ownerName, repoName, page)
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                listAdapter.items.addAll(it)
                listAdapter.notifyDataSetChanged()
            }
        }, {

        })
    }
}

