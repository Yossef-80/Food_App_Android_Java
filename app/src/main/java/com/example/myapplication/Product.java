package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Product extends AppCompatActivity {

    ImageView foodImage,backImage;
    TextView foodName,foodPrice,totalPrice;
    Button AddToCartButton,DeleteOrderButton;
    int foodId;
    SeekBar amountBar;
    Orders order;
    Db_sqlite db=new Db_sqlite(Product.this);

    ArrayList<Integer> MealId=new ArrayList<>();
    ArrayList<String> MealName=new ArrayList<>();
    ArrayList<Integer> MealTotalPrice=new ArrayList<>();
    ArrayList<Integer> MealCount=new ArrayList<>();
    int price;
    AlertDialog.Builder addBuilder,deleteBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);
        displayData();
        addBuilder = new AlertDialog.Builder(Product.this);
        deleteBuilder = new AlertDialog.Builder(Product.this);

        deleteBuilder.setMessage("Do You Want To Delete The Meal?");
        deleteBuilder.setCancelable(true);
        deleteBuilder.setPositiveButton(
                "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteOrder(getIntent().getExtras().getString("name"));

                       // MealId.remove(MealName.indexOf(getIntent().getExtras().getInt("id")));
                       // MealCount.remove(MealName.indexOf(getIntent().getExtras().getString("name")));
                        //MealTotalPrice.remove(MealName.indexOf(getIntent().getExtras().getInt("price")));
                        displayData();
                        DeleteOrderButton.setVisibility(View.INVISIBLE);
                        Toast.makeText(Product.this, "The Meal Have Been Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        deleteBuilder.setNegativeButton(
                "No", (dialog, which) -> dialog.cancel()
        );


        addBuilder.setMessage("Do You Want To Add Meal To The Order List?");
        addBuilder.setCancelable(true);

        addBuilder.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    order = new Orders(foodId, getIntent().getExtras().getString("name"), price * amountBar.getProgress(), amountBar.getProgress());
                    db.insertOrderData(order.mealId,order.mealName,order.TotalCost,order.mealCount);
                    DeleteOrderButton.setVisibility(View.VISIBLE);
                });

        addBuilder.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());


        //read layout
        backImage=findViewById(R.id.back_Image);
        DeleteOrderButton=findViewById(R.id.deleteOrder);
        foodImage=findViewById(R.id.food_image);
        foodName=findViewById(R.id.food_text);
        foodPrice=findViewById(R.id.food_price);
        totalPrice=findViewById(R.id.total_price);
        amountBar=findViewById(R.id.seekBar);
        AddToCartButton=findViewById(R.id.add_To_cart_button);

        AddToCartButton.setClickable(false);

        foodName.setText(getIntent().getExtras().getString("name"));
        price=getIntent().getExtras().getInt("price");
        foodPrice.setText(String.valueOf(price));
        foodId=getIntent().getExtras().getInt("id");

        //if the image pressed the activity finishes
        backImage.setOnClickListener(v -> finish());

        //if there is data in array the button is visible
        if (CancelVisibile())
        {
            DeleteOrderButton.setVisibility(View.VISIBLE);
        }
        else
        {
            DeleteOrderButton.setVisibility(View.INVISIBLE);
        }

        //to read the image from intent and store it into image view
        foodImage.setImageResource(getIntent().getExtras().getInt("image"));

        //the seekBar
        amountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               displayData();
                int amount=amountBar.getProgress();
                totalPrice.setText(String.valueOf(price*amount));
                if (amount==10)
                {
                    Toast.makeText(Product.this, "You Have Reached The Max Amount", Toast.LENGTH_SHORT).show();
                }
                else if (amount==0)
                {
                    AddToCartButton.setClickable(false);
                    AddToCartButton.setBackgroundResource(R.drawable.un_availabe_button);

                }
                else{
                    AddToCartButton.setClickable(true);
                    AddToCartButton.setBackgroundResource(R.drawable.custom_button);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                int amount=amountBar.getProgress();
                totalPrice.setText(String.valueOf(price*amount));
                if (amount==0)
                {
                    AddToCartButton.setClickable(false);
                    AddToCartButton.setBackgroundResource(R.drawable.un_availabe_button);

                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int amount=amountBar.getProgress();
                if (amount==0)
                {
                    AddToCartButton.setClickable(false);
                    AddToCartButton.setBackgroundResource(R.drawable.un_availabe_button);

                }
            }
        });



    }
    void  displayData()
    {
        MealId.clear();
        MealName.clear();
        MealTotalPrice.clear();
        MealCount.clear();
        Cursor cursor=db.getAllOrders();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"The Meal List Is Empty",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext())
            {
                MealId.add(cursor.getInt(1));
                MealName.add(cursor.getString(2));
                MealTotalPrice.add(cursor.getInt(3));
                MealCount.add(cursor.getInt(4));

            }
        }
    }

    public void addToCart(View view) {
        displayData();
        if (MealId.size()>0) {
            if (MealName.contains(getIntent().getExtras().getString("name")))
            {

                if (MealCount.get(MealName.indexOf(getIntent().getExtras().getString("name"))) == amountBar.getProgress())
                {
                    Toast.makeText(Product.this, "You Have Already This Order In Your Cart", Toast.LENGTH_SHORT).show();
                    order = new Orders(foodId, getIntent().getExtras().getString("name"), price * amountBar.getProgress(), amountBar.getProgress());
                    db.UpdateFoodPrice(order.mealId,order.mealName,order.TotalCost,order.mealCount);
                } else {

                    order = new Orders(foodId, getIntent().getExtras().getString("name"), price * amountBar.getProgress(), amountBar.getProgress());
                    db.UpdateFoodPrice(order.mealId,order.mealName,order.TotalCost,order.mealCount);
                    Toast.makeText(Product.this, "Order Updated Successfully", Toast.LENGTH_LONG).show();



                }
            }
            else if (amountBar.getProgress()==0)
            {
                Toast.makeText(this, "You Have not Selected The Quantity", Toast.LENGTH_LONG).show();
            }
            else {
                AlertDialog alert11 = addBuilder.create();
                alert11.show();


            }
        }
        else
        {
            AlertDialog alert11 = addBuilder.create();
            alert11.show();
        }

        displayData();
    }
    public boolean CancelVisibile()
    {

        return MealName.contains(getIntent().getExtras().getString("name"));
    }

    public void deleteTheOrder(View view) {

        AlertDialog alert12 = deleteBuilder.create();
        alert12.show();


    }
}