package com.example.user.simplehub.fragment.pulls

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubPulls
import com.example.user.simplehub.utils.dateFormat
import com.example.user.simplehub.utils.getSimpleDate
import kotlinx.android.synthetic.main.item_issue_created_closed.view.*

class PullsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_issue_created_closed, parent, false)
)

class PullsListAdapter : RecyclerView.Adapter<PullsViewHolder>() {
    var items: MutableList<GithubPulls> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullsViewHolder {
        return PullsViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: PullsViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            if (item.pullRequest != null) {
                pullsTitle.text = item.title
                pullsDate.text = getSimpleDate(item.PullsDate, dateFormat)
                pullsNumber.text = item.number.toString()
                pullsRepo.text = item.repository.repoName
                pullsUser.text = item.user.login
            } else {

            }
        }
    }


}
