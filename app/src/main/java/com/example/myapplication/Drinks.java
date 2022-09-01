package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Drinks extends AppCompatActivity {
    RecyclerView recycleView;
    DessertAdapter DrinkAdapter;
    Db_sqlite db=new Db_sqlite(Drinks.this);
    ArrayList drinkId=new ArrayList<>();
    ArrayList drinkName=new ArrayList<>();
    ArrayList drinkPrice=new ArrayList<>();
    ArrayList drinkImage=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        recycleView=(RecyclerView) findViewById(R.id.drink_recycle_view);



        displayData();
        DrinkAdapter=new DessertAdapter(Drinks.this,drinkId,drinkName,drinkPrice,drinkImage);
        recycleView.setAdapter(DrinkAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(Drinks.this));
    }
    void  displayData()
    {
        Cursor cursor=db.getAllDrinks();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"nodata",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext())
            {
                drinkId.add(cursor.getInt(0));
                drinkName.add(cursor.getString(1));
                drinkPrice.add(cursor.getInt(2));
                drinkImage.add(cursor.getInt(3));
            }
        }
    }

    public void exit_drinks(View view) {
        finish();
    }

}