package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepoCommits
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.dateFormat
import com.example.user.simplehub.utils.enqueue
import com.example.user.simplehub.utils.getSimpleDate
import kotlinx.android.synthetic.main.activity_repo_commits.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.fragment_example.view.*
import kotlinx.android.synthetic.main.item_repo_commits.view.*
import java.text.SimpleDateFormat
import java.util.*

class CommitViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_repo_commits, parent, false)
)

class CommitListAdapter : RecyclerView.Adapter<CommitViewHolder>() {
    var items: MutableList<GithubRepoCommits> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        return CommitViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            commitMessage.text = item.commit.message
            commitDate.text = getSimpleDate(item.commit.committer.date, dateFormat)
            committer.text = item.committer.login
            Glide.with(this).load(item.committer.avatar_url).into(commiterAvatarImage)
        }
    }
}

class CommitActivity : AppCompatActivity() {

    lateinit var listAdapter: CommitListAdapter
    var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_commits)

        profile.text = "commits"

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

