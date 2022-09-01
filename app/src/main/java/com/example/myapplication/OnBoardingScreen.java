package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingScreen extends AppCompatActivity {
    private ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted,nxt_button;
    Animation animation;
    SharedPreferences onBoardingScreen;
    int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding_screen);

        viewPager=findViewById(R.id.slider);
        dotsLayout=findViewById(R.id.dots);
        letsGetStarted=findViewById(R.id.startbtn);
        sliderAdapter=new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
        nxt_button=findViewById(R.id.next_btn);
         onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
         nxt_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 viewPager.setCurrentItem(currentPosition++);
             }
         });
    }

    public void skip(View view)
    {
        SharedPreferences.Editor editor=onBoardingScreen.edit();
        editor.putBoolean("firstTime",false);
        editor.commit();
        Intent intent=new Intent(OnBoardingScreen.this,Login.class);
        startActivity(intent);
        finish();
    }

    private void addDots(int position)
    {
        dots=new TextView[3];
        dotsLayout.removeAllViews();
        for (int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length>0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.Orange));
        }
    }
    ViewPager.OnPageChangeListener changeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPosition=position;
            if (position==2)
            {
                animation= AnimationUtils.loadAnimation(OnBoardingScreen.this,R.anim.fading);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
                nxt_button.setVisibility(View.INVISIBLE);
            }
            else if(position==0) {
                letsGetStarted.setAnimation(null);
                letsGetStarted.setVisibility(View.INVISIBLE);
                nxt_button.setVisibility(View.VISIBLE);
            }
            else if(position==1) {
                letsGetStarted.setAnimation(null);
                letsGetStarted.setVisibility(View.INVISIBLE);
                nxt_button.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}