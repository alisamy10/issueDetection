package com.example.issuedetection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;

public class Login extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goSignUp(View view) {
        Intent intent = new Intent(Login.this,SignUp.class);
        startActivity(intent);

    }
}
