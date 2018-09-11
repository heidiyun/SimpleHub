package com.example.user.simplehub.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubUsers
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.OtherProfileActivity
import com.example.user.simplehub.ui.ProfileActivity
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.fragment_example.view.*
import kotlinx.android.synthetic.main.item_follower.view.*

class FragmentHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
)

class FragmentExample : Fragment() {
    var query: String? = null
    var page: Int = 0
    var pageLimit: Int = 1

    inner class ListAdapter : RecyclerView.Adapter<FragmentHolder>() {
        var items: MutableList<GithubUsers> = mutableListOf()

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
        val layoutManager = LinearLayoutManager(requireContext().applicationContext)

        view.searchView.adapter = listAdapter
        view.searchView.layoutManager = layoutManager

        view.searchView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = view.searchView.layoutManager.childCount
                val totalItemCount = view.searchView.layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >=
                        totalItemCount) {
                    // 최하단이벤트를 받아올 수 있게 되었다.!!
                    setApi(query, ++page)

                }
            }
        })

//        setApi("")

        return view
    }

    fun setApi(query: String?, page: Int) {
        val searchApi = provideUserApi(activity!!.applicationContext)
        if (page > pageLimit) return
        val call = searchApi.getUsers(query, page)
        this.query = query
        this.page = page
        call.enqueue({ response ->
            println("query $page")
            println("response 들어옴.")
            val result = response.body()
            result?.let {
                pageLimit = (it.totalCount / 30) + 1
                if (page == 1)
                    listAdapter.items.clear()
                listAdapter.items.addAll(it.items)
                listAdapter.notifyDataSetChanged()
            }
        }, {

        })

    }

    fun startAct(login: String) {
        val intent: Intent
        if (login == ProfileActivity.ownerLogin) {
            intent = Intent(activity, ProfileActivity::class.java)
        } else {
            intent = Intent(activity, OtherProfileActivity::class.java)
            intent.putExtra("login", login)
        }
        startActivity(intent)
    }

}
