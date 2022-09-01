package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DessertAdapter extends RecyclerView.Adapter<DessertAdapter.MyViewHolder> {
    private Context context;
   private ArrayList DessertId,DessertName,DessertPrice;
   private ArrayList image;

    DessertAdapter(Context context, ArrayList dessertId, ArrayList dessertName, ArrayList dessertPrice,ArrayList image)
    {
     DessertId=dessertId;
     DessertName=dessertName;
     DessertPrice=dessertPrice;
     this.context=context;
     this.image=image;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
       View view= inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dessert_id_text.setText(String.valueOf(DessertId.get(position)));
        holder.dessert_name_text.setText(String.valueOf(DessertName.get(position)));
        holder.dessert_price_text.setText(String.valueOf(DessertPrice.get(position)));
        holder.image.setImageResource((Integer) image.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Product.class);
                intent.putExtra("image",((Integer) image.get(position)).intValue());
                intent.putExtra("name",String.valueOf(DessertName.get(position)));
                intent.putExtra("price",((Integer)DessertPrice.get(position)).intValue());
                intent.putExtra("id",((Integer)DessertId.get(position)).intValue());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return DessertId.size();
    }

    public void filter(Context search, ArrayList filteredId, ArrayList filteredName, ArrayList filteredPrice, ArrayList filteredImage) {
        context=search;
        DessertName=filteredName;
        DessertPrice=filteredPrice;
        DessertId=filteredId;
        image=filteredImage;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         ImageView image;
        TextView dessert_id_text,dessert_name_text,dessert_price_text;
     //   CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dessert_id_text=itemView.findViewById(R.id.dessert_id_text);
            dessert_name_text=itemView.findViewById(R.id.dessert_name_text);
            dessert_price_text=itemView.findViewById(R.id.dessert_price_text);
            image=itemView.findViewById(R.id.imageViewer);
           // cardView=itemView.findViewById(R.id.RecyclerLayout);
        }
/*
        @Override
        public void onClick(View v) {
            int adapterPosition=getAdapterPosition();
            cardView.click;
        }*/
    }
}
