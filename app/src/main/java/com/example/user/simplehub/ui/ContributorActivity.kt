package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepoContributors
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_repo_contributor.*
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

class ContributorActivity: AppCompatActivity() {


    lateinit var listAdapter: ContributorListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_contributor)

        listAdapter = ContributorListAdapter()

        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        val ownerName = bundle.getString("ownerName")

        contributorView.adapter = listAdapter
        contributorView.layoutManager = LinearLayoutManager(this)

        val contributorApi = provideUserApi(this)
        val call = contributorApi.getRepoContributors(ownerName, repoName)
        call.enqueue({
            response ->
            val result = response.body()
            result?.let{
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()
            }
        }, {

        })

    }
}