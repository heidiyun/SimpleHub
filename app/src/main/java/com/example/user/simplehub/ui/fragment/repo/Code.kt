package com.example.user.simplehub.ui.fragment.repo


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
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.item_repo_contents.view.*
import kotlinx.android.synthetic.main.profile_tab_follower.view.*


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
                        startAct(item.name, repoName, ownerName, "directory")

                    }
                }

                if (item.type == "file") {
                    Glide.with(this).load(R.drawable.ic_book).into(content_repo_image)

                    card_repo.setOnClickListener {
                        Log.i(DirActivity::class.java.simpleName, "click dir!!!")

                        startAct(item.name, repoName, ownerName, "file")
                    }
                }

            }
        }


    }

    lateinit var listAdapter: RepoListAdapter

    lateinit var repoName: String
    lateinit var ownerName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.profile_tab_follower, container, false)
        listAdapter = RepoListAdapter()
        arguments?.let {
            Log.i("Arguments", "arguments: ${it.getString("repoName")}")
            repoName = it.getString("repoName")
            ownerName = it.getString("ownerName")

        }

        view.followerView.adapter = listAdapter
        view.followerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val repoApi = provideUserApi(activity!!.applicationContext)

        val call = repoApi.getRepoContents(ownerName, repoName, "")

        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()
            }
        }, {

        })


        return view
    }


    fun startAct(dirName: String, repoName: String, ownerName: String, type: String) {
        val intent = when(type) {
            "directory" -> {
                Intent(activity, DirActivity::class.java)
            }
            "file" -> {
                Intent(activity, FileActivity::class.java)
            }
            else -> return
        }
        val dirNameList: ArrayList<String> = arrayListOf()
        dirNameList.add(dirName)
        intent.putExtra("dirName", dirNameList)
        intent.putExtra("repoName", repoName)
        intent.putExtra("ownerName", ownerName)
        startActivity(intent)
    }
}