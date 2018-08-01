package com.example.user.simplehub.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepo
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.ProfileActivity
import com.example.user.simplehub.ui.RepoActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.profile_tab_repository.view.*
import kotlinx.android.synthetic.main.repo_item.*
import kotlinx.android.synthetic.main.repo_item.view.*


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

            repoNameText.setOnClickListener {

            }
        }


    }

}


class RepositoryTab: Fragment() {
    lateinit var listAdapter: SearchListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_tab_repository, container,false)
        listAdapter = SearchListAdapter()
        view.repositoryView.adapter = listAdapter
        view.repositoryView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val githubApi = provideUserApi(activity!!.applicationContext)


        val call = githubApi.getRepoInfo()
        call.enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    listAdapter.items = it
                    listAdapter.notifyDataSetChanged()
                }
            }
        }, {

        })


        return view
    }

}

