package com.example.user.simplehub.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue

class SearchActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_search)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

                val githubUser = provideUserApi(this)
                val call = githubUser.getUsers(query)
                call.enqueue({ response ->
                    val result = response.body()
                    result?.let {
                        println("userinfo : ${it}")
                    }
                }, {
                    println("error!!!!! ${it.localizedMessage}")
                })
        }
    }

}