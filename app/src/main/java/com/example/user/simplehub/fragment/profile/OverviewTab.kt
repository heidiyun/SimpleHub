package com.example.user.simplehub.fragment.profile

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import com.example.user.simplehub.utils.getSimpleDate
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.profile_tab_overview.*
import kotlinx.android.synthetic.main.profile_tab_overview.view.*
import java.text.SimpleDateFormat
import java.util.*


class OverviewTab : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_tab_overview, container, false)
        val dateFormat = SimpleDateFormat("MMM dd, YYYY", Locale.US)
        val login = arguments?.getString("login")
        val uri = Uri.parse("https://ghchart.rshah.org/${login as String}")

        GlideToVectorYou
                .init()
                .with(requireActivity())
                .setPlaceHolder(R.drawable.ic_github, R.drawable.ic_round_error_symbol_red)
                .load(uri, view.gitChart)

        val userApi = provideUserApi(requireContext())
        val call = userApi.getUser(login as String)
        call.enqueue({
            response ->
            val result = response.body()
            result?.let {
                joinDate.text = getSimpleDate(it.joinDate, dateFormat)
                repoNum.text = it.repoNumber.toString()
                followerNum.text = it.followers.toString()
                followingNum.text = it.following.toString()
                gistNum.text = it.gistNumber.toString()
            }
        }, {

        })

        return view
    }
}



