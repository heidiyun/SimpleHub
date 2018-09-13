package com.example.user.simplehub.ui.Adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubIssueEvents
import kotlinx.android.synthetic.main.item_issue_event.view.*

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
            when {
                item.event == "assigned" -> {
                    Log.i("IssueDetailActivity", "assigned")
                    eventMessage.text = item.assignee.login + "  assigned this  " + item.assigner.login
                    eventIcon.setImageResource(R.drawable.ic_user)
                }
                item.event == "renamed" -> {
                    eventMessage.text = "renamed from  " + item.rename.from + "  to  " + item.rename.to
                    eventIcon.setImageResource(R.drawable.ic_create_new_pencil_button)
                }
                item.event == "closed" -> {
                    eventMessage.text = "issue closed"
                    eventIcon.setImageResource(R.drawable.ic_round_error_symbol_red)

                }
                else -> issueEventCardView.visibility = View.GONE
            }
        }
    }
}