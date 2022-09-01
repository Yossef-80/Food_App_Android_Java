package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    LayoutInflater layoutInflater;
    Context context;
    public  SliderAdapter(Context context)
    {
        this.context=context;
    }

    int images[]={
            R.drawable.cookie,
            R.drawable.undraw_donut_love_kau1 ,
            R.drawable.chef,
    };
    int Headings[]={
            R.string.first_slide_heading,
            R.string.second_slide_heading,
            R.string.third_slide_heading
    };

    @Override
    public int getCount() {
        return Headings.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slides_layout,container,false);


        ImageView imageView=view.findViewById(R.id.slider_image);
        TextView textView=view.findViewById(R.id.slider_heading);
        imageView.setImageResource(images[position]);
        textView.setText(Headings[position]);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout) object;
    }
}
