package com.example.user.simplehub.fragment

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Color
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
import com.example.user.simplehub.api.model.GithubRepo
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.RepoActivity
import com.example.user.simplehub.utils.enqueue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.profile_tab_repository.view.*
import kotlinx.android.synthetic.main.repo_item.view.*
import org.jetbrains.anko.colorAttr
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream


class RepositoryTab : Fragment() {


    var login = ""

    class RepoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
    )

    inner class SearchListAdapter(val context: Context) : RecyclerView.Adapter<RepoViewHolder>() {
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
                if (item.language != null) {
                    langText.text = item.language
                    val languageColor = jsonObject.get(item.language)
                    if (languageColor != null) {
                        val color: Int = Color.parseColor(languageColor.asString)
                        langColor.setColorFilter(color)

                    }
                }
                card_repo.setOnClickListener {
                    startAct(item.name, item.fullName, item.owner.login)
                    //                Log.i(ProfileActivity::class.java.simpleName, "click!!")

                }
            }


        }

    }


    lateinit var listAdapter: SearchListAdapter
    lateinit var jsonObject: JsonObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_tab_repository, container, false)
        listAdapter = SearchListAdapter(activity!!.applicationContext)
        view.repositoryView.adapter = listAdapter
        view.repositoryView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        val bundle = arguments


//        jsonParse()

        val json = getAssetJsonDate(requireContext())
        val parser = JsonParser()
        jsonObject = parser.parse(json) as JsonObject

        bundle?.let {
            login = it.getString("login")
        }

        val githubApi = provideUserApi(activity!!.applicationContext)
        val call = githubApi.getRepoInfo(login)
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

    fun startAct(repoName: String, fullName: String, ownerName: String) {
        val intent = Intent(activity, RepoActivity::class.java)
        intent.putExtra("repoName", repoName)
        intent.putExtra("fullName", fullName)
        intent.putExtra("ownerName", ownerName)
        startActivity(intent)
    }

    fun getAssetJsonDate(context: Context): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open("color.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        Log.e("data", json)
        return json
    }

}



