package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAccount extends AppCompatActivity {
    TextView NickNameText,NickNameAccent,EmailText;
    ImageView backBtn;
    SharedPreferences AccountDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        NickNameText=findViewById(R.id.NickNameTextView);
        NickNameAccent=findViewById(R.id.NickNameColorAccent);
        EmailText=findViewById(R.id.EmailTextView);
        backBtn=(ImageView) findViewById(R.id.backFromAccountBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AccountDetails = getSharedPreferences(
                "LoginDetails"
                ,MODE_PRIVATE);
        String name=AccountDetails.getString("name","");
        String Email=AccountDetails.getString("email","");
        NickNameText.setText(name);
        NickNameAccent.setText(name);
        EmailText.setText(Email);

    }
}