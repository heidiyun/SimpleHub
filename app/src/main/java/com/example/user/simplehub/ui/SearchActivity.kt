package com.example.user.simplehub.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubUsers
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_follower.view.*
import kotlinx.android.synthetic.main.item_repo_contents.view.*


class SearchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
)

class SearchResultListAdapter : RecyclerView.Adapter<SearchViewHolder>() {
    var items: List<GithubUsers> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            IDText.text = item.login
            Glide.with(this).load(item.avatarUrl).into(ownerAvatarImage)
        }
    }


}


class SearchActivity : Activity() {

    lateinit var listAdapter: SearchResultListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            listAdapter = SearchResultListAdapter()
            searchView.adapter = listAdapter
            searchView.layoutManager = LinearLayoutManager(this)

            val githubUser = provideUserApi(this)
            val call = githubUser.getUsers(query)
            call.enqueue({ response ->
                val result = response.body()
                result?.let {

                    listAdapter.items = it.items
                    if (it.items.size == 0) {
                        noResult.visibility = VISIBLE
                    }
                    listAdapter.notifyDataSetChanged()

                }
            }, {
                println("error!!!!! ${it.localizedMessage}")
            })
        }
    }

}