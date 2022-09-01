package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Meal_Screen extends AppCompatActivity {
    RecyclerView recycleView;
    DessertAdapter MealAdapter;

    ArrayList MealId=new ArrayList<>();
    ArrayList MealName=new ArrayList<>();
    ArrayList MealPrice=new ArrayList<>();
    ArrayList MealImage=new ArrayList<>();
    Db_sqlite db=new Db_sqlite(Meal_Screen.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal__screen);
        recycleView=(RecyclerView) findViewById(R.id.Meal_Recycle_view);
        displayData();
        MealAdapter=new DessertAdapter(Meal_Screen.this,MealId,MealName,MealPrice,MealImage);
        recycleView.setAdapter(MealAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(Meal_Screen.this));

    }


    void  displayData()
    {
        Cursor cursor=db.getAllMeals();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"nodata",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext())
            {
                MealId.add(cursor.getInt(0));
                MealName.add(cursor.getString(1));
                MealPrice.add(cursor.getInt(2));
                MealImage.add(cursor.getInt(3));

            }
        }
    }



    public void exit_meals(View view) {
        finish();
    }


}