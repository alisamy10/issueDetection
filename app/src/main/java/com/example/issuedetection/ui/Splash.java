package com.example.issuedetection.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;

public class Splash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this,Login.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
