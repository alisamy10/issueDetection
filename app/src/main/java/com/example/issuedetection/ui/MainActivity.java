package com.example.issuedetection.ui;


import android.content.Intent;
import android.os.Bundle;

import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,SaveIssueActivity.class));
    }
}
