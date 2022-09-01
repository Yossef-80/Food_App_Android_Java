package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class TheSplashScreen extends AppCompatActivity {

    //for database
    String usersNames[]={"Ahmed","Omar","Nader","Mostafa","Hani","Ayman"};
    String usersEmails[]={"Ahmed@mail.com","Omar@mail.com","Nader@mail.com","Mostafa@mail.com","Hani@mail.com","Ayman@mail.com"};
    String usersPasswords[]={"123","1234","12345","123456","1234567","12345678"};

    String DessertNames[]={"Cheese Cake","Ice Cream Bowl","Strawberry Cake","Cup Cake","Chocolate Donuts","Chocolate Cake"};
    int DessertPrices[]={50,20,30,15,10,30};
    int dessertImages[]={R.mipmap.cheese_cake,R.mipmap.ice_cream_bowl,R.mipmap.strawberry_cake,R.mipmap.cup_cake,R.mipmap.chocolate_donuts,R.mipmap.chocolate_cake};


    String DrinkNames[]={"Coffee","Iced Coffee","Latte","Orange Juice","Strawberry Juice","Tea"};
    int DrinkPrices[]={  50,         60,             50,         20,          20,           10};
    int DrinkImages[]={R.mipmap.coffee,R.mipmap.iced_coffee_image,R.mipmap.latte,R.mipmap.orange_juice,R.mipmap.strawberry_juice,R.mipmap.tea};



    String MealNames[]={"Burger","Cheese Burger","Chicken Burger With Fries","Chicken Pizza","French Fries","Meat Pizza","Salad","Salmon"};
    int MealPrices[]={  50,         60,             80,                         40,             20,             30,         20,     60};
    int MealImages[]={R.mipmap.burger,R.mipmap.cheeseburger,R.mipmap.chickenburgerwithfries,R.mipmap.chicken_pizaa,R.mipmap.frensh_fries,R.mipmap.meat_pizza,R.mipmap.salad,R.mipmap.salmon};


    Db_sqlite db=new Db_sqlite(TheSplashScreen.this);


    //////////////
    ImageView logoImage;
    TextView nameLine;
    private static int splashTimer=2000;
    //animation
    Animation logoAnimation;


    SharedPreferences onBoardingScreen,loginScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        logoImage=findViewById(R.id.logoSrc);
        nameLine=findViewById(R.id.splash_text);


        logoAnimation= AnimationUtils.loadAnimation(this,R.anim.fading);
//        logoImage.setAnimation(logoAnimation);
  //      nameLine.setAnimation(logoAnimation);

        if (db.isDessertEmpty())
        {
            for (int i=0;i<DessertPrices.length;i++) {
            db.insertDessertData(DessertNames[i],DessertPrices[i],dessertImages[i]);
        }
        }
        if (db.isDrinkEmpty())
        {
            for (int i=0;i<DrinkPrices.length;i++) {
                db.insertDrinkData(DrinkNames[i],DrinkPrices[i],DrinkImages[i]);}
        }
        if (db.isMealEmpty())
        {
            for (int i=0;i<MealPrices.length;i++) {
           db.insertMealData(MealNames[i],MealPrices[i],MealImages[i]);}
        }
        if (db.isUserEmpty())
        {
            for (int i=0;i<usersEmails.length;i++) {
                db.insertdata(usersNames[i],usersEmails[i],usersPasswords[i]);}
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginScreen=getSharedPreferences("login",MODE_PRIVATE);
                    boolean loggedIn=loginScreen.getBoolean("loggedIn",false);
                onBoardingScreen=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime=onBoardingScreen.getBoolean("firstTime",true);
                if(isFirstTime)
                {
                    //SharedPreferences.Editor editor=onBoardingScreen.edit();
                    //editor.putBoolean("firstTime",false);
                    //editor.commit();
                    Intent intent = new Intent(TheSplashScreen.this, OnBoardingScreen.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    if (!loggedIn)
                    {
                        Intent intent = new Intent(TheSplashScreen.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(TheSplashScreen.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        },splashTimer);
    }

}