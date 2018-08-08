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
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_repo_commits.*
import kotlinx.android.synthetic.main.activity_repo_contributor.*
import kotlinx.android.synthetic.main.item_repo_commits.view.*
import java.text.SimpleDateFormat
import java.util.*


class CommitViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_repo_commits, parent, false)
)

class CommitListAdapter : RecyclerView.Adapter<CommitViewHolder>() {
    var items: List<GithubRepoCommits> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        return CommitViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            val dateFormat = SimpleDateFormat("MMM d YYYY HH:mm", Locale.getDefault())


            commitMessage.text = item.commit.message
            commitDate.text = getSimpleDate(item.commit.committer.date, dateFormat)
            committer.text = item.committer.login
            Glide.with(this).load(item.committer.avatar_url).into(commiterAvatarImage)
        }
    }


}

class CommitActivity : AppCompatActivity() {


    lateinit var listAdapter: CommitListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_commits)

        listAdapter = CommitListAdapter()

        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        val ownerName = bundle.getString("ownerName")

        commitView.adapter = listAdapter
        commitView.layoutManager = LinearLayoutManager(this)

        val contributorApi = provideUserApi(this)
        val call = contributorApi.getRepoCommits(ownerName, repoName)
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

fun getSimpleDate(date: String, dateFormat: SimpleDateFormat): String {
    val timeZone = TimeZone.getTimeZone("Africa/Casablanca")
    val splitDate = date.replace("Z", ".000" + timeZone.displayName)
    println(splitDate)
    val givenDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    val parsedDate = givenDateFormat.parse(splitDate)
//        val simpleDate = SimpleDateFormat("EEE, MMM d, HH:mm", Locale.getDefault())
    return dateFormat.format(parsedDate)
}