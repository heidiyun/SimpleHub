package com.example.user.simplehub.ui.fragment.profile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubStarring
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.RepoActivity
import com.example.user.simplehub.utils.enqueue
import com.example.user.simplehub.utils.getAssetJsonDate
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.item_starred.view.*
import kotlinx.android.synthetic.main.profile_tab_stars.view.*


class StarTab : Fragment() {

    class StarringViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_starred, parent, false)
    )

    inner class StarringListAdapter : RecyclerView.Adapter<StarringViewHolder>() {
        var items: List<GithubStarring> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarringViewHolder {
            return StarringViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: StarringViewHolder, position: Int) {
            val item = items[position]

            with(holder.itemView) {
                //            Log.i(TAG, "item name : $item.fullName")
                starringText.text = item.fullName
                RepoActivity.starRepoList.add(item.name)
                starNumger.text = item.starNumber.toString()
                RepoActivity.starNameList.add(item.owner.login)

                if (item.language != null) {
                    langText.text = item.language
                    val languageColor = jsonObject.get(item.language)
                    if (languageColor != null) {
                        val color: Int = Color.parseColor(languageColor.asString)
                        langColor.setColorFilter(color)

                    }
                }


                starredCardView.setOnClickListener {
                    startAct(item.name, item.fullName, item.owner.login, item.starNumber)
                }
            }
        }

    }


    lateinit var listAdapter: StarringListAdapter
    lateinit var jsonObject: JsonObject


    var login = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.profile_tab_stars, container, false)
        listAdapter = StarringListAdapter()
        view.starringView.adapter = listAdapter
        view.starringView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        val bundle = arguments

        bundle?.let {
            login = it.getString("login")
        }

        val json = getAssetJsonDate(requireContext())
        val parser = JsonParser()
        jsonObject = parser.parse(json) as JsonObject


        val starringApi = provideUserApi(activity!!.applicationContext)
        println("login id: $login")
        val followerCall = starringApi.getStarredInfo(login)
        followerCall.enqueue({ response ->
            val result = response.body()
            result?.let {
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()
            }

        }, {

        })

        return view
    }

    fun startAct(repoName: String, fullName: String, ownerName: String, starCount: Int) {
        val intent = Intent(activity, RepoActivity::class.java)
        intent.putExtra("repoName", repoName)
        intent.putExtra("fullName", fullName)
        intent.putExtra("ownerName", ownerName)
        intent.putExtra("starCount", starCount)
        startActivity(intent)
    }

}