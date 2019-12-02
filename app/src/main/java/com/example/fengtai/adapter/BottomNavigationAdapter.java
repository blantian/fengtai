package com.example.fengtai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class BottomNavigationAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public BottomNavigationAdapter(FragmentManager fragmentManager, List<Fragment> fragments)
        {
            super(fragmentManager);
            this.fragmentList=fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return this.fragmentList.get(i);
        }

        @Override
        public int getCount() {

            return this.fragmentList.size();
        }
    }

