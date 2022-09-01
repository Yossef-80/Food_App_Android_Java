package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Login extends AppCompatActivity {
    EditText username,email,password;
    Button login,register;
    SharedPreferences Prefrences,loginScreen;
    ArrayList<String> EmailList;
    ArrayList<String> NameList;
    ArrayList<String> PassWordList;

    SharedPreferences LoginDetails ;


    Db_sqlite db = new Db_sqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginDetails = getSharedPreferences(
                "LoginDetails"
                ,MODE_PRIVATE);
        EmailList=new ArrayList<>();
        NameList=new ArrayList<>();
        PassWordList=new ArrayList<>();
        UsersData();

        username=findViewById(R.id.usernam);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        Prefrences =getSharedPreferences("Userinfo",0);
        loginScreen=getSharedPreferences("login",MODE_PRIVATE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernamevalue= username.getText().toString();
                String emailvalue= email.getText().toString();
                String passwordvalue=password.getText().toString();
                String registeredUsername= Prefrences.getString("username",usernamevalue);
                String registeredemail= Prefrences.getString("email",emailvalue);
                String registeredPass= Prefrences.getString("password",passwordvalue);

                if(EmailList.contains(emailvalue)&&NameList.contains(usernamevalue)&&PassWordList.contains(passwordvalue))
                { int indexOfLogin = 0;

                    for (int i=0; i <EmailList.size();i++)
                    {
                        if (EmailList.get(i).equals(emailvalue))
                        {
                            indexOfLogin =i;
                            break;
                        }
                    }

                    if (EmailList.get(indexOfLogin).equals(emailvalue)&&NameList.get(indexOfLogin).equals(usernamevalue)&&PassWordList.get(indexOfLogin).equals(passwordvalue)){
                    SharedPreferences.Editor editor=loginScreen.edit();
                    editor.putBoolean("loggedIn",true);
                    editor.commit();

                        SharedPreferences.Editor LoginEditor = LoginDetails.edit();

                        LoginEditor.putString("name",usernamevalue);
                        LoginEditor.putString("email",emailvalue);
                        LoginEditor.commit();


                    Intent intent=new Intent(Login.this,HomePage.class);
                    startActivity(intent);
                        Toast.makeText(Login.this, "You Have Logged In Successfully!!", Toast.LENGTH_SHORT).show();
                    finish();

                    }
                    else
                    {
                        Toast.makeText(Login.this, "May User Or Password is Wrong...Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "There is Missing Fields or there is no data like in field text", Toast.LENGTH_LONG).show();
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void  UsersData()
    {
        NameList.clear();
        PassWordList.clear();
        EmailList.clear();

        Cursor cursor=db.getAllUsers();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext())
            {
                NameList.add(cursor.getString(0));
                PassWordList.add(cursor.getString(2));
                EmailList.add(cursor.getString(1));


            }
        }
    }




}