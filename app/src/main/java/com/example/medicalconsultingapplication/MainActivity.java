package com.example.medicalconsultingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicalconsultingapplication.adapter.AdapterSlider.SliderAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mslidePagerLayout;
    private LinearLayout mdotesLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button nextBtn  ;
    private int currentPage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mslidePagerLayout = findViewById(R.id.slidePagerlayout);
        mdotesLayout = findViewById(R.id.dotslayout);
        //nextBtn = findViewById(R.id.nextbtn);

        sliderAdapter = new SliderAdapter(this);
        mslidePagerLayout.setAdapter(sliderAdapter);


        addDotsIndictor(0);
        mslidePagerLayout.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndictor(int position) {
        mDots = new TextView[3];
        mdotesLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(25);
            mDots[i].setTextColor(getResources().getColor(R.color.gold));

            mdotesLayout.addView(mDots[i]);

        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.purple_200));

        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndictor(position);
            currentPage =  position  ;
            if(position == 0 ){
             //   nextBtn.setEnabled(true);


            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };
}