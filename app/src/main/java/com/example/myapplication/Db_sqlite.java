package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Db_sqlite extends SQLiteOpenHelper {
   /* public static final String ID_COL="id";
    public static final String NAME_COL="name";
    public static final String  PASSWORD_COL="password";
    public static final String  PRICE_COL="price";
    public static final String  IMAGE_COL="image";
    public static final String  EMAIL_COL="email";
    public static final String FOOD_ID_COL="food_id";
    public static final String FOOD_NAME_COL="food_name";
    public static final String FOOD__COL="food_total_price";
    public static final String FOOD_ID_COL="food_amount";
    */



    public static final String db_name="data.db";
    public Db_sqlite(@Nullable Context context) {
        super(context,db_name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table users(username Text primary key,email Text,password Text)");
        db.execSQL("create table dessert_table(id integer primary key autoincrement,name text ,price integer,image int)");
        db.execSQL("create table meal_table(id integer primary key autoincrement,name text ,price integer,image int)");
        db.execSQL("create table drink_table(id integer primary key autoincrement,name text ,price integer,image int)");
        db.execSQL("create table orders(id integer primary key autoincrement,food_id integer ,food_name text ,food_total_price integer,food_amount integer)");




    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_table");

        onCreate(db);
    }
    public Cursor getAllOrders()
    {
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor res=null;
        if (db!=null)
        {
            res= db.rawQuery("select * from orders",null);
        }

        return res;
    }
    public void deleteOrder(String orderName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("orders","food_name=?",new String[]{orderName});

    }
    public void UpdateFoodPrice(int meal_id,String name,int Total_price,int food_amount)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("food_name",name);
        contentValues.put("food_total_price",Total_price);
        contentValues.put("food_id",meal_id);
        contentValues.put("food_amount",food_amount);
        db.update("orders",contentValues,"food_name like ?",new String[]{name});
    }


    public boolean insertOrderData(int meal_id,String name,int Total_price,int food_amount)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("food_name",name);
        contentValues.put("food_total_price",Total_price);
        contentValues.put("food_id",meal_id);
        contentValues.put("food_amount",food_amount);

        long result =db.insert("orders",null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public boolean insertDessertData(String name,int price,int imageSrc)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("image",imageSrc);
        long result =db.insert("dessert_table",null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public boolean insertMealData(String name,int price,int imageSrc)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("image",imageSrc);
        long result =db.insert("meal_table",null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
    public boolean insertDrinkData(String name,int price,int imageSrc)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("image",imageSrc);
        long result =db.insert("drink_table",null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
   /* public boolean insertUsersData(String name,int password,String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("email",email);
        long result =db.insert("user_table",null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }*/


    //to fill table
   /* public void  fillTable(){
            SQLiteDatabase db=this.getWritableDatabase();

        String names[]={"Ahmed","Omar","Nader","Mostafa","Hani","Ayman"};
        String Emails[]={"Ahmed@gmail.com","Omar@gmail.com","Nader@gmail.com","Mostafa@gmail.com","Hani@gmail.com","Ayman@gmail.com"};
        int passwords[]={123,1234,12345,123456,1234567,12345678};

        ContentValues UserValues=new ContentValues();


            UserValues.put("name",names[0]);
            UserValues.put("email",Emails[0]);
            UserValues.put("password",passwords[0]);
            db.insert("user_table",null,UserValues);

        ContentValues DessertValues=new ContentValues();

        String DessertNames[]={"cheese_cake","ice_cream_bowl","strawberry_cake","cup_cake","chocolate_donuts","chocolate_cake"};
        int DessertPrices[]={50,20,30,15,10,30};



       String dessertImages[]={"cheese_cake","ice_cream_bowl","strawberry_cake","cup_cake","chocolate_donuts","chocolate_cake"};

            DessertValues.put("name",DessertNames[0]);
            DessertValues.put("price",DessertPrices[0]);

            Bitmap src= BitmapFactory.decodeFile(dessertImages[i]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            src.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] data = baos.toByteArray();
            DessertValues.put("image",data);

           // db.insert("user_table",null,DessertValues);

        //ContentValues MealValues=new ContentValues();
        //String MealNames[]={"burger","cheese burger","chicken burger with fries","chicken pizza","frensh fries","meat pizza","salad","salmon"};
        //int MealPrices[]={  50,         60,             80,                         40,             20,             30,         20,     60};
      //  int MealImages[]={R.drawable.burger,R.drawable.cheeseburger,R.drawable.chickenburgerwithfries,R.drawable.chicken_pizaa,R.drawable.frensh_fries,R.drawable.meat_pizza,R.drawable.salad,R.drawable.salmon};

            //MealValues.put("name",MealNames[0]);
           // MealValues.put("price",MealPrices[0]);

           Bitmap src= BitmapFactory.decodeFile(MealImages[i]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            src.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] data = baos.toByteArray();
            DessertValues.put("image",data);

          //  db.insert("meal_table",null,MealValues);

        //ContentValues DrinkValues=new ContentValues();
        //String DrinkNames[]={"coffee","iced coffee","latte","orange juice","strawberry juice","tea"};
      //  int DrinkPrices[]={  50,         60,             50,         20,          20,           10};
       // int DrinkImages[]={R.drawable.coffee,R.drawable.iced_coffee_image,R.drawable.latte,R.drawable.orange_juice,R.drawable.strawberry_juice,R.drawable.tea};

          //  DrinkValues.put("name",DrinkNames[0]);
         //   DrinkValues.put("price",DessertPrices[0]);

           Bitmap src= BitmapFactory.decodeFile(DrinkImages[i]);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            src.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] data = baos.toByteArray();
            DessertValues.put("image",data);

         //   db.insert("drink_table",null,DrinkValues);


       // db.close();


    */


    public Cursor getallrecords(){
        Food food = null;
        ArrayList<String> arrayList=new ArrayList();
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor res=null;
       if (db!=null)
       {
            res= db.rawQuery("select * from dessert_table",null);
       }

        return res;
    }
    public Cursor getAllMeals()
    {
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor res=null;
        if (db!=null)
        {
            res= db.rawQuery("select * from meal_table",null);
        }

        return res;
    }
    public Cursor getAllDrinks()
    {
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor res=null;
        if (db!=null)
        {
            res= db.rawQuery("select * from drink_table",null);
        }

        return res;
    }
    public boolean isDessertEmpty()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from dessert_table",null);
        if (cursor.moveToFirst())
        {
            return false;
        }
        else
        {return  true;}
    }
    public boolean isMealEmpty()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from meal_table",null);
        if (cursor.moveToFirst())
        {
            return false;
        }
        else
        {return  true;}
    }
    public boolean isDrinkEmpty()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from drink_table",null);
        if (cursor.moveToFirst())
        {
            return false;
        }
        else
        {return  true;}
    }
    public boolean isUserEmpty()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from users",null);
        if (cursor.moveToFirst())
        {
            return false;
        }
        else
        {return  true;}
    }

    public  Boolean checkusername( String username){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from users where username =?", new String[] {username} );
        if (cursor.getCount()>0)
            return  true;
        else
            return  false;
    }
    public Boolean insertdata(String username, String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password",password);
        long result=db.insert("users", null, values);
        if (result==-1)
            return  false;
        else
            return true;
    }
    public Boolean checkuseremail (String username, String email)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from users where username =? and email ", new String[] {username, email} );
        if (cursor.getCount()>0)
            return  true;
        else
            return false;
    }
    public Boolean checkuserpass (String username, String email, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from users where username =? and email  and password", new String[] {username, email,password} );
        if (cursor.getCount()>0)
            return  true;
        else
            return false;

    }

    public Cursor getAllUsers()
    {
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor res=null;
        if (db!=null)
        {
            res= db.rawQuery("select * from users",null);
        }

        return res;
    }
    public void deleteAllOrders()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("orders",null,null);
        db.close();
    }


}
