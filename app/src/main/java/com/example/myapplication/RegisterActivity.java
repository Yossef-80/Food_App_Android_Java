package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    EditText username,email,password,repassword,phone;
    RadioGroup gender;
    Button register,cancel;
    SharedPreferences Prefrences;
    Db_sqlite DB;
    ArrayList<String> EmailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.usernam);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.conpassword);
        phone=findViewById(R.id.phone);
        EmailList=new ArrayList<>();
        DB=new Db_sqlite(this);
        UsersData();
        Spinner spinner=findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        register=findViewById(R.id.register);
        cancel=findViewById(R.id.cancel);
        Prefrences =getSharedPreferences("Userinfo",0);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String emailvalue = email.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String phonev = phone.getText().toString();


                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(emailvalue) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(phonev)) {
                    Toast.makeText(RegisterActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                } else if (pass.equals(repass)) {
                    if (EmailList.contains(emailvalue)) {
                        Toast.makeText(RegisterActivity.this, "There Exist Email With This Value", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    SharedPreferences.Editor editor = Prefrences.edit();
                    editor.putString("username ", user);
                    editor.putString("email ", emailvalue);
                    editor.putString("password", pass);
                    editor.putString("repassword ", repass);
                    editor.putString("phone ", phonev);
                    //   editor.putString("gender",gend);
                    editor.apply();
                    Boolean checkuser = DB.checkusername(user);
                    if (checkuser == false) {
                        Boolean insert = DB.insertdata(user, emailvalue, pass);
                        if (insert == true)
                            Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, Login.class);
                        startActivity(i);
                        finish();
                    } else
                        Toast.makeText(RegisterActivity.this, "Registrion Failed", Toast.LENGTH_SHORT).show();
                }
            }
                else
                    Toast.makeText(RegisterActivity.this, " password are not matching ", Toast.LENGTH_SHORT).show();


            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyField();
            }
        });
    }

    public void emptyField(){
        username.setText("");
        email.setText("" );
        password.setText("");
        repassword.setText("");
        phone.setText("");
    }

    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text=parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }


    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    void  UsersData()
    {

        EmailList.clear();

        Cursor cursor=DB.getAllUsers();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext())
            {

                EmailList.add(cursor.getString(1));


            }
        }
    }

}