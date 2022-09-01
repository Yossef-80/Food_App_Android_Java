package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    Db_sqlite db=new Db_sqlite(HomePage.this);

    ImageView menuImage;
    SharedPreferences loginScreen;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.myCart)
        {
            Intent intent=new Intent(HomePage.this,OrderList.class);
            startActivity(intent);
            return true;
        }
        else if (id==R.id.Contact_Us)
        {
            Intent intent=new Intent(HomePage.this,ContactUs.class);
            startActivity(intent);
            return true;
        }
        else if (id==R.id.Log_Out)
        {
            db.deleteAllOrders();
            SharedPreferences.Editor editor=loginScreen.edit();
            editor.putBoolean("loggedIn",false);
            editor.commit();
            Intent intent=new Intent(HomePage.this,Login.class);
            startActivity(intent);

            finish();
            return true;
        }
        else if(id==R.id.myAccount)
        {


            Intent intent=new Intent(HomePage.this,MyAccount.class);
            startActivity(intent);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db.getAllOrders();
        setContentView(R.layout.activity_home_page);
        menuImage=findViewById(R.id.menu_icon);
        loginScreen=getSharedPreferences("login",MODE_PRIVATE);
        registerForContextMenu(menuImage);
        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  PopupMenu popupMenu=new PopupMenu(HomePage.this,menuImage);
                popupMenu.getMenuInflater().inflate(R.menu.home_menu,popupMenu.getMenu());



                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override

                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        if (id==R.id.myCart)
                        {
                            Intent intent=new Intent(HomePage.this,OrderList.class);
                            startActivity(intent);
                            return true;
                        }
                        else if (id==R.id.Contact_Us)
                        {
                            Intent intent=new Intent(HomePage.this,ContactUs.class);
                            startActivity(intent);
                            return true;
                        }
                        else if (id==R.id.Log_Out)
                        {
                            SharedPreferences.Editor editor=loginScreen.edit();
                            editor.putBoolean("loggedIn",false);
                            editor.commit();
                            Intent intent=new Intent(HomePage.this,Login.class);
                            startActivity(intent);

                            finish();
                            return true;
                        }
                        else
                        {
                            return true;
                        }



                    }
                });

                popupMenu.show();*/
            }
        });

    }


    public void dessertpage(View view) {
        Intent intent=new Intent(HomePage.this,Dessert_Screen.class);
        startActivity(intent);

    }

    public void Meal_Page(View view) {
        Intent intent=new Intent(HomePage.this,Meal_Screen.class);
        startActivity(intent);
    }

    public void Drink_Page(View view) {
        Intent intent=new Intent(HomePage.this,Drinks.class);
        startActivity(intent);
    }

    public void SearchPage(View view) {
        Intent intent=new Intent(HomePage.this,Search.class);
        startActivity(intent);
    }
}