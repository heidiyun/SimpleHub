package com.example.user.simplehub.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue


class OverviewTab : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.fragment_search, menu)
//        val searchView = menu?.findItem(R.id.menuItemSearch)?.actionView as SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                val githubUser = provideUserApi(activity!!.applicationContext)
//                val call = githubUser.getUsers(query)
//                call.enqueue({ response ->
//                    val result = response.body()
//                    result?.let {
//                        println("userinfo : ${it}")
//                    }
//                }, {
//                    println("error!!!!! ${it.localizedMessage}")
//                })
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_tab_overview, container, false)
    }
}