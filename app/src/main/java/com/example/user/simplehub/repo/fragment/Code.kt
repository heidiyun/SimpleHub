package com.example.user.simplehub.repo.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepoContents
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.DirActivity
import com.example.user.simplehub.ui.FileActivity
import com.example.user.simplehub.ui.RepoActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.item_repo_contents.view.*
import kotlinx.android.synthetic.main.repo_tab_code.view.*


class Code : Fragment() {


    class RepoContentsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo_contents, parent, false)
    )

    inner class RepoListAdapter : RecyclerView.Adapter<RepoContentsViewHolder>() {
        var items: List<GithubRepoContents> = emptyList()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoContentsViewHolder {
            return RepoContentsViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: RepoContentsViewHolder, position: Int) {
            val item = items[position]

            with(holder.itemView) {
                content_repo_name.text = item.name

                if (item.type == "dir") {
                    Glide.with(this).load(R.drawable.ic_baseline_folder_24px).into(content_repo_image)
                    card_repo.setOnClickListener {
                        startAct(item.name)

                    }
                }

                if (item.type == "file") {
                    Glide.with(this).load(R.drawable.ic_book).into(content_repo_image)

                    card_repo.setOnClickListener {
                        Log.i(DirActivity::class.java.simpleName, "click dir!!!")

                        startFileAct(item.name)
                    }
                }

            }
        }


    }

    lateinit var listAdapter: RepoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.repo_tab_code, container, false)
        listAdapter = RepoListAdapter()

        view.repoContentsView.adapter = listAdapter
        view.repoContentsView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val repoApi = provideUserApi(activity!!.applicationContext)
        val call = repoApi.getRepoContents(RepoActivity.ownerName, RepoActivity.repoName, DirActivity.dirName.joinToString(separator = "/"))
        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()
//                for(i in 0..result.size-1)
//                    Log.i(RepoActivity::class.java.simpleName, "repo name: ${it[i].name}")
            }
        }, {

        })

        return view
    }


    fun startAct(dirName: String) {
        DirActivity.dirName.add(dirName)
        val intent = Intent(activity, DirActivity::class.java)
        startActivity(intent)
    }

    fun startFileAct(fileName: String) {
        DirActivity.dirName.add(fileName)
        val intent = Intent(activity, FileActivity::class.java)
        startActivity(intent)
    }
}