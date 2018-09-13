package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.IssueRequestModel
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.ui.Adapter.IssueEventListAdapter
import com.example.user.simplehub.utils.dateFormat
import com.example.user.simplehub.utils.enqueue
import com.example.user.simplehub.utils.getSimpleDate
import kotlinx.android.synthetic.main.fragment_issue_detail.*

class IssueDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_issue_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras

        issueMessage.text = bundle.getString("body")
        issueRepo.text = bundle.getString("repo")
        issueTitle.text = bundle.getString("title")
        issueNumber.text = bundle.getInt("number").toString()
        ownerName.text = bundle.getString("owner")
        openOwner.text = bundle.getString("owner")
        date.text = getSimpleDate(bundle.getString("date"), dateFormat)
        val avatarUrl = bundle.getString("avatarUrl")
        Glide.with(this).load(avatarUrl).into(ownerAvatar)
        Glide.with(this).load(avatarUrl).into(ownerAvatar2)

        val state = bundle.getString("state")
        if (state == "closed") {
            closeButton.visibility = View.GONE
            stateIcon.setImageResource(R.drawable.ic_round_error_symbol_red)
            stateText.text = getString(R.string.issue_closed)
        }

        val userApi = provideUserApi(this)
        closeButton.setOnClickListener {
            val issueRequestModel = IssueRequestModel("closed")
            val call = userApi.editIssue(ProfileActivity.ownerLogin,
                    bundle.getString("repo"),
                    bundle.getInt("number"),
                    issueRequestModel)

            call.enqueue(
                    { response ->
                        response.body()
                    },
                    { exception ->
                        exception.printStackTrace()
                    })
        }

        val listAdapter = IssueEventListAdapter()
        issueEventRecyclerView.adapter = listAdapter
        issueEventRecyclerView.layoutManager = LinearLayoutManager(this)

        val call = userApi.getIssueEvents(ProfileActivity.ownerLogin,
                bundle.getString("repo"),
                bundle.getInt("number"))

        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                Log.i("IssueDetailActivity", "size : ${it.size}")
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()
            }
        }, {
            Log.e("IssueDetailActivity", "come out")

        })
    }
}


