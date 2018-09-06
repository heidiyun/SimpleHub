package com.example.user.simplehub.fragment.issue

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubPulls
import com.example.user.simplehub.utils.dateFormat
import com.example.user.simplehub.utils.getSimpleDate
import kotlinx.android.synthetic.main.item_issue_created.view.*

class IssueViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_issue_created, parent, false)
)

class IssueListAdapter : RecyclerView.Adapter<IssueViewHolder>() {
    var items: MutableList<GithubPulls> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            issueNameText.text = item.title
            issueDate.text = getSimpleDate(item.PullsDate, dateFormat)
            issueOwner.text = item.repository.repoName

            issueCardView.setOnClickListener {
                Log.i("Adapter", "come in")
                val bundle = Bundle()
                bundle.putString("body", item.body)
                bundle.putInt("number", item.number)
                bundle.putString("title", item.title)
                bundle.putString("owner", item.user.login)
                bundle.putString("avatarUrl", item.user.avatarUrl)
                bundle.putString("repo", item.repository.name)
                bundle.putString("date", item.PullsDate)
                val fragment = Detail()
                fragment.arguments = bundle
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction().add(R.id.issueFragment, fragment).commit()
            }
        }

    }

}
