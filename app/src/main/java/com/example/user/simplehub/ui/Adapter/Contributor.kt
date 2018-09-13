package com.example.user.simplehub.ui.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepoContributors
import kotlinx.android.synthetic.main.item_repo_contributor.view.*

class ContributorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_repo_contributor, parent, false)
)

class ContributorListAdapter : RecyclerView.Adapter<ContributorViewHolder>() {
    var items: List<GithubRepoContributors> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            loginText.text = item.login
            contributionNumber.text = item.contributions.toString()
            Glide.with(this).load(item.avatarUrl).into(loginAvatarImage)
        }
    }
}