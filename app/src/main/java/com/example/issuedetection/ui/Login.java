package com.example.issuedetection.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.issuedetection.Base.BaseActivity;
import com.example.issuedetection.R;



public class Login extends BaseActivity {

    TextView register_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        intiView();
        register_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,SignUp.class);
                startActivity(intent);


            }
        });
    }

    protected void intiView(){
        register_textview=findViewById(R.id.signIn_signUp_text_view);
    }


    }

