package com.example.user.simplehub.fragment.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.utils.dateFormat
import com.example.user.simplehub.utils.getSimpleDate
import kotlinx.android.synthetic.main.fragment_issue_detail.view.*

class Detail : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_issue_detail, container, false)
        view.issueMessage.text =  arguments?.getString("body")
        view.issueRepo.text = arguments?.getString("repo")
        view.issueTitle.text = arguments?.getString("title")
        view.issueNumber.text = arguments?.getInt("number").toString()
        view.ownerName.text = arguments?.getString("owner")
        view.date.text = getSimpleDate(arguments?.getString("date"), dateFormat)
        val avatarUrl = arguments?.getString("avatarUrl")
        Glide.with(this).load(avatarUrl).into(view.ownerAvatar)
        Glide.with(this).load(avatarUrl).into(view.ownerAvatar2)

        return view
    }




}