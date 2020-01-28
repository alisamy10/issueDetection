package com.example.issuedetection.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.issuedetection.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText username_txt,mail_txt,pass_txt;
    Button signUp_btn;
    ProgressBar prog;
    LinearLayout parent;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    protected void intiView(){
        username_txt=findViewById(R.id.username_field);
        mail_txt=findViewById(R.id.signUp_email_editText);
        pass_txt=findViewById(R.id.signUp_password_editText);
        signUp_btn=findViewById(R.id.signUp_button);
        prog=findViewById(R.id.signUp_progress);
        parent=findViewById(R.id.signUp_parent);
        mAuth=FirebaseAuth.getInstance();
    }
    protected void signUp(){
        String username=username_txt.getText().toString().trim();
        String mail=mail_txt.getText().toString().trim();
        String pass=pass_txt.getText().toString().trim();

        if (username.equals("")||mail.equals("")||pass.equals(""))
            Toast.makeText(this, "Please check your data", Toast.LENGTH_SHORT).show();
        else {
             prog.setVisibility(View.VISIBLE);
             parent.setVisibility(View.GONE);
             mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful())
                     {
                         Intent intent =new Intent(SignUp.this,Login.class);
                         startActivity(intent);
                         finish();
                     }
                     else {
                         Toast.makeText(SignUp.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            parent.setVisibility(View.VISIBLE);
                            prog.setVisibility(View.GONE);
                     }

                 }
             });

        }
    }
}
