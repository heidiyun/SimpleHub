package com.example.user.simplehub.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubIssueEvents
import com.example.user.simplehub.api.model.IssueRequestModel
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.dateFormat
import com.example.user.simplehub.utils.enqueue
import com.example.user.simplehub.utils.getSimpleDate
import kotlinx.android.synthetic.main.fragment_issue_detail.*
import kotlinx.android.synthetic.main.fragment_issue_detail.view.*
import kotlinx.android.synthetic.main.item_issue_event.view.*

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
            stateText.text = "closed by"
        }

        val userApi = provideUserApi(this)
        closeButton.setOnClickListener {
            val issueRequestModel = IssueRequestModel("closed")
            val call = userApi.editIssue(ProfileActivity.ownerLogin,
                    bundle.getString("repo"),
                    bundle.getInt("number"),
                    issueRequestModel)
//
            call.enqueue({ response ->
            }, {

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

class IssueEventViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_issue_event, parent, false)
)

class IssueEventListAdapter : RecyclerView.Adapter<IssueEventViewHolder>() {
    var items: List<GithubIssueEvents> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueEventViewHolder {
        return IssueEventViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: IssueEventViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            if (item.event == "assigned") {
                Log.i("IssueDetailActivity", "assigned")
                eventMessage.text = item.assignee.login + "  assigned this  " + item.assigner?.login
                eventIcon.setImageResource(R.drawable.ic_user)
            } else if (item.event == "renamed") {
                eventMessage.text =  "renamed from  " + item.rename.from + "  to  " + item.rename.to
                eventIcon.setImageResource(R.drawable.ic_create_new_pencil_button)
            } else if (item.event == "closed") {
                eventMessage.text = "issue closed"
                eventIcon.setImageResource(R.drawable.ic_round_error_symbol_red)

            } else {
                issueEventCardView.visibility = View.GONE
            }
        }
    }
}
