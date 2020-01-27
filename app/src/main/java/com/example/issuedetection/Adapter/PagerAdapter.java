package com.example.issuedetection.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.issuedetection.ui.Fragment.AddNewEvent;
import com.example.issuedetection.ui.Fragment.History;
import com.example.issuedetection.ui.Fragment.HomeFragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

 ArrayList<Fragment>fragmentArrayList=new ArrayList<>();
 ArrayList<String>tiltleArraylist=new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
       return fragmentArrayList.get(position);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tiltleArraylist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public   void  addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);
        tiltleArraylist.add(title);
    }


}
