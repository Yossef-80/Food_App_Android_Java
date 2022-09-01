package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity {
    ListView listView;
    ArrayList<String> nameAndPriceList;
    ArrayList<String> name;
    AlertDialog.Builder DeleterBuilder,CheckOutBuilder;
    Db_sqlite db=new Db_sqlite(OrderList.this);
    int totalCost;

    TextView TotalPaymentText;
    String currentName;
    int positionToRemove;
    String currentName2;
    Button CheckOutButton;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        listView=(ListView) findViewById(R.id.orderListView);
       nameAndPriceList=new ArrayList<>();
         name=new ArrayList<>();

          getOrders();
          TotalPaymentText=findViewById(R.id.TotalPayment);
          TotalPaymentText.setText(String.valueOf(totalCost));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nameAndPriceList);

        listView.setAdapter(adapter);

        CheckOutButton=findViewById(R.id.paymentButton);


        //To End Order
        CheckOutBuilder = new AlertDialog.Builder(OrderList.this);

        CheckOutBuilder.setMessage("Are You Sure To Finish Ordering?");
        CheckOutBuilder.setCancelable(true);
        CheckOutBuilder.setPositiveButton(
                "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //       db.deleteOrder(currentName);
                        while (!name.isEmpty()||!nameAndPriceList.isEmpty()) {
                            if (nameAndPriceList.size()==1)
                            {
                                db.deleteOrder(name.get(0));

                                adapter.remove(name.get(0));
                                nameAndPriceList.remove(0);
                                name.remove(0);
                            }
                            else {
                                db.deleteOrder(name.get(name.size() - 1));


                                //  adapter.remove(currentName2);
                                //    adapter.notifyDataSetChanged();
                                nameAndPriceList.remove(nameAndPriceList.size() - 1);
                                name.remove(name.size() - 1);
                                // listView.invalidateViews();
                                adapter.remove(name.get(name.size() - 1));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        //  adapter.remove(currentName2);
                        //    adapter.notifyDataSetChanged();
                        //      nameAndPriceList.remove(nameAndPriceList.indexOf(currentName2));
                        //       name.remove(name.indexOf(currentName));
                        // listView.invalidateViews();
                        //         adapter.remove(currentName2);
                        //          adapter.notifyDataSetChanged();

                        Toast.makeText(OrderList.this, "You Have Finished Ordering Successfully", Toast.LENGTH_SHORT).show();
                        getOrders();
                        TotalPaymentText.setText(String.valueOf(totalCost));
                        // getOrders();
                    }
                }
        );
        CheckOutBuilder.setNegativeButton(
                "No", (dialog, which) -> dialog.cancel()
        );

        CheckOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameAndPriceList.isEmpty()||name.isEmpty())
                {
                    Toast.makeText(OrderList.this, "The Order List Is Empty", Toast.LENGTH_LONG).show();
                }
                else {

                    AlertDialog alertCheck = CheckOutBuilder.create();

                    alertCheck.show();
                }
            }
        });



        //To Delete Item
        DeleterBuilder = new AlertDialog.Builder(OrderList.this);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                int positionToRemove=position;
                currentName=name.get(position);
                currentName2 = nameAndPriceList.get(position);
                AlertDialog alert13 = DeleterBuilder.create();

                alert13.show();
                return true;
            }
        });
        DeleterBuilder.setMessage("Do You Want To Delete The Meal?");
        DeleterBuilder.setCancelable(true);
        DeleterBuilder.setPositiveButton(
                "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteOrder(currentName);


                        //  adapter.remove(currentName2);
                        //    adapter.notifyDataSetChanged();
                        nameAndPriceList.remove(nameAndPriceList.indexOf(currentName2));
                        name.remove(name.indexOf(currentName));
                        // listView.invalidateViews();
                        adapter.remove(currentName2);
                        adapter.notifyDataSetChanged();
                      //  listView.setAdapter(adapter);
                        Toast.makeText(OrderList.this, "The Meal Have Been Deleted Successfully", Toast.LENGTH_SHORT).show();
                        getOrders();
                        TotalPaymentText.setText(String.valueOf(totalCost));
                       // getOrders();
                    }
                }
        );
        DeleterBuilder.setNegativeButton(
                "No", (dialog, which) -> dialog.cancel()
        );



        }
    public void getOrders()
    {

        nameAndPriceList.clear();
        name.clear();
        totalCost=0;
        Cursor cursor=db.getAllOrders();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        else {

            while (cursor.moveToNext())
            {
                String val1= cursor.getString(2);
                int val2=cursor.getInt(3);
                int val3=cursor.getInt(4);
               nameAndPriceList.add(val1+"\n \n       \t"+"Quantity: "+String.valueOf(val3)+"          Total:"+String.valueOf(val2));
                name.add(cursor.getString(2));
                totalCost=totalCost+cursor.getInt(3);
            }
        }
    }



}