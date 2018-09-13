package com.example.user.simplehub.ui.fragment.profile

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPageAdapter(fn: FragmentManager) : FragmentPagerAdapter(fn) {
    private val fragmentList : ArrayList<Fragment> = arrayListOf()
    private val fragmentTitleList : ArrayList<String> = arrayListOf()

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}