package com.example.user.simplehub

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.api.model.GithubUsers
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.OtherProfileActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_example.view.*
import kotlinx.android.synthetic.main.item_follower.view.*

class FragmentHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
)

class FragmentExample : Fragment() {

    inner class ListAdapter() : RecyclerView.Adapter<FragmentHolder>() {
        var items: List<GithubUsers> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentHolder {
            return FragmentHolder(parent)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: FragmentHolder, position: Int) {
            val item = items[position]

            with(holder.itemView) {
                IDText.text = item.login
                Glide.with(this).load(item.avatarUrl).into(ownerAvatarImage)

                followerCardView.setOnClickListener {
                    startAct(item.login)
                }
            }
        }
    }

    val listAdapter = ListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_example, container, false)

        view.searchView.adapter = listAdapter
        view.searchView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
//        setApi("")

        return view
    }

    fun setApi(query: String?) {
        val searchApi = provideUserApi(activity!!.applicationContext)
        val call = searchApi.getUsers(query)
        call.enqueue({ response ->
            println("response 들어옴.")
            val result = response.body()
            result?.let {
                listAdapter.items = it.items
                listAdapter.notifyDataSetChanged()
            }
        }, {

        })

    }

    fun startAct(login: String) {
        val intent = Intent(activity, OtherProfileActivity::class.java)
        intent.putExtra("login", login)
        startActivity(intent)
    }


}
