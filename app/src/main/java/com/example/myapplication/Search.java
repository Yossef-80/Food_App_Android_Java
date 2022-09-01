package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    //database
    Db_sqlite db=new Db_sqlite(Search.this);
    //Meals Array List
    ArrayList MealId=new ArrayList<>();
    ArrayList MealName=new ArrayList<>();
    ArrayList MealPrice=new ArrayList<>();
    ArrayList MealImage=new ArrayList<>();

    //Dessert Array List
    ArrayList dessertId=new ArrayList<>();
    ArrayList dessertName=new ArrayList<>();
    ArrayList dessertPrice=new ArrayList<>();
    ArrayList dessertImage=new ArrayList<>();

    //Drink Array List
    ArrayList drinkId=new ArrayList<>();
    ArrayList drinkName=new ArrayList<>();
    ArrayList drinkPrice=new ArrayList<>();
    ArrayList drinkImage=new ArrayList<>();

    DessertAdapter foodAdapter;
    RadioGroup foodRadioGroup;
    RadioButton MealButton,DessertButton,DrinkButton;
    RecyclerView searchRecycler;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buildRecyclerView();
        displayDessertData();
        displayDrinkData();
        displayMealData();


        editText=findViewById(R.id.Search_edit_text);
        MealButton=findViewById(R.id.MealRadio);
        DessertButton=findViewById(R.id.DessertRadio);
        DrinkButton=findViewById(R.id.DrinksRadio);
        MealButton.setChecked(true);
        foodRadioGroup=findViewById(R.id.radioGrouping);
        foodRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.DessertRadio){foodAdapter.filter(Search.this,dessertId,dessertName,dessertPrice,dessertImage);};
                if (checkedId==R.id.MealRadio){ foodAdapter.filter(Search.this,MealId,MealName,MealPrice,MealImage);};
                if (checkedId==R.id.DrinksRadio){foodAdapter.filter(Search.this,drinkId,drinkName,drinkPrice,drinkImage);};
            }
        });



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }
    public void filter(String text)
    {
        ArrayList filteredId=new ArrayList();
        ArrayList filteredName=new ArrayList();
        ArrayList filteredPrice=new ArrayList();
        ArrayList filteredImage=new ArrayList();

        if (MealButton.isChecked())
        {
            for (int i=0;i<MealId.size();i++)
            {
                if (MealName.get(i).toString().toLowerCase().contains(text.toLowerCase()))
                {
                    filteredId.add(MealId.get(i));
                    filteredImage.add(MealImage.get(i));
                    filteredName.add(MealName.get(i));
                    filteredPrice.add(MealPrice.get(i));
                }
            }
        }
        if (DessertButton.isChecked())
        {
            for (int i=0;i<dessertId.size();i++)
            {
                if (dessertName.get(i).toString().toLowerCase().contains(text.toLowerCase()))
                {
                    filteredId.add(dessertId.get(i));
                    filteredImage.add(dessertImage.get(i));
                    filteredName.add(dessertName.get(i));
                    filteredPrice.add(dessertPrice.get(i));
                }
            }
        }
        if (DrinkButton.isChecked())
        {
            for (int i=0;i<drinkId.size();i++)
            {
                if (drinkName.get(i).toString().toLowerCase().contains(text.toLowerCase()))
                {
                    filteredId.add(drinkId.get(i));
                    filteredImage.add(drinkImage.get(i));
                    filteredName.add(drinkName.get(i));
                    filteredPrice.add(drinkPrice.get(i));
                }
            }
        }
        foodAdapter.filter(Search.this,filteredId,filteredName,filteredPrice,filteredImage);

    }

    public void backButton(View view) {
        finish();
    }
    private void  buildRecyclerView()
    {
        searchRecycler=findViewById(R.id.recyclerViewSearch);
        searchRecycler.setHasFixedSize(true);
        searchRecycler.setLayoutManager(new LinearLayoutManager(Search.this));
        foodAdapter=new DessertAdapter(Search.this,MealId,MealName,MealPrice,MealImage);
        searchRecycler.setAdapter(foodAdapter);

    }
    void  displayMealData()
    {
        MealId.clear();
        MealName.clear();
        MealImage.clear();
        MealPrice.clear();
        Cursor cursor=db.getAllMeals();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
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
    void  displayDessertData()
    {
        dessertId.clear();
        dessertPrice.clear();
        dessertName.clear();
        dessertImage.clear();
        Cursor cursor=db.getallrecords();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
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
    void  displayDrinkData()
    {
        drinkPrice.clear();
        drinkName.clear();
        drinkImage.clear();
        drinkId.clear();

        Cursor cursor=db.getAllDrinks();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
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

}