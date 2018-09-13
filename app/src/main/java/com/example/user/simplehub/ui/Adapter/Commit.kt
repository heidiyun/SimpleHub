package com.example.user.simplehub.ui.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepoCommits
import com.example.user.simplehub.utils.dateFormat
import com.example.user.simplehub.utils.getSimpleDate
import kotlinx.android.synthetic.main.item_repo_commits.view.*

class CommitViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repo_commits, parent, false)
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
