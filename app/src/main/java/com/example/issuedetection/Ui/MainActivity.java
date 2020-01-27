package com.example.issuedetection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.issuedetection.Adapter.PagerAdapter;
import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;
import com.example.issuedetection.ui.Fragment.AddNewEvent;
import com.example.issuedetection.ui.Fragment.History;
import com.example.issuedetection.ui.Fragment.HomeFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends BaseActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=findViewById(R.id.mTabLayout);
        viewPager=findViewById(R.id.viewpager);
        pagerAdapter =new PagerAdapter(getSupportFragmentManager(),0);
        pagerAdapter.addFragment(new History(),"الذاكره");
        pagerAdapter.addFragment(new AddNewEvent(),"اضافه حدث");
        pagerAdapter.addFragment(new HomeFragment(),"الصفحه الرئسيه");



        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);



    }
}
