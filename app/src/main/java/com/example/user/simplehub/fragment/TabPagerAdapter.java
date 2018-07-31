package com.example.user.simplehub.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                OverviewTab overviewTab = new OverviewTab();
                return overviewTab;
            case 1:
                RepositoryTab repositoryTab = new RepositoryTab();
                return repositoryTab;
            case 2:
                StarTab starTab = new StarTab();
                return starTab;
            case 3:
                FollowingTab followingTab = new FollowingTab();
                return followingTab;
            case 4:
                FollowerTab followerTab = new FollowerTab();
                return followerTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
