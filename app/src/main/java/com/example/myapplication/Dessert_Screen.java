package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Dessert_Screen<dessertAdapter> extends AppCompatActivity {

    RecyclerView recycleView;
    DessertAdapter dessertAdapter;
    Db_sqlite db=new Db_sqlite(Dessert_Screen.this);
    ArrayList dessertId=new ArrayList<>();
    ArrayList dessertName=new ArrayList<>();
    ArrayList dessertPrice=new ArrayList<>();
    ArrayList dessertImage=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert__screen);

        recycleView=(RecyclerView) findViewById(R.id.recycle_view_dessert);
        recycleView.setItemViewCacheSize(35);
        recycleView.setDrawingCacheEnabled(true);

        recycleView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);


        displayData();
        dessertAdapter=new DessertAdapter(Dessert_Screen.this,dessertId,dessertName,dessertPrice,dessertImage);
        recycleView.setAdapter(dessertAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(Dessert_Screen.this));
    }


   void  displayData()
   {
       Cursor cursor=db.getallrecords();
       if (cursor.getCount()==0)
       {
           Toast.makeText(this,"nodata",Toast.LENGTH_LONG).show();
       }
       else {
           while (cursor.moveToNext())
           {
            dessertId.add(cursor.getInt(0));
            dessertName.add(cursor.getString(1));
            dessertPrice.add(cursor.getInt(2));
            dessertImage.add(cursor.getInt(3));

           }
       }
   }
    public void exit_desserts(View view) {
        finish();
    }



}