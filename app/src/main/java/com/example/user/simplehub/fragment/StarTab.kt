package com.example.user.simplehub.fragment

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
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.item_starred.view.*
import kotlinx.android.synthetic.main.profile_tab_stars.view.*

class StarringViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_starred, parent, false)
)

class StarringListAdapter : RecyclerView.Adapter<StarringViewHolder>() {
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
            starNumger.text = item.starNumber.toString()
        }
    }

}


class StarTab : Fragment() {

    lateinit var listAdapter: StarringListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.profile_tab_stars, container, false)
        listAdapter = StarringListAdapter()
        view.starringView.adapter = listAdapter
        view.starringView.layoutManager = LinearLayoutManager(activity!!.applicationContext)


        val starringApi = provideUserApi(activity!!.applicationContext)
        val followerCall = starringApi.getStarredInfo()
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
}