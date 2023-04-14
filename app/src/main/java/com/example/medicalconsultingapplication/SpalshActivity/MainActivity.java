package com.example.medicalconsultingapplication.SpalshActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.AdapterSlider.SliderBorderAdapter;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mdotesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 mslidePagerLayout = findViewById(R.id.slidePagerlayout);
        mdotesLayout = findViewById(R.id.dotslayout);
        SliderBorderAdapter sliderAdapter = new SliderBorderAdapter(this);
        mslidePagerLayout.setAdapter(sliderAdapter);


        addDotsIndictor(0);

        mslidePagerLayout.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                addDotsIndictor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    public void addDotsIndictor(int position) {
        TextView[] mDots = new TextView[3];

        mdotesLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(25);
            mDots[i].setTextColor(ContextCompat.getColor(this,R.color.gold));

            mdotesLayout.addView(mDots[i]);
        }
        mDots[position].setTextColor(ContextCompat.getColor(this,R.color.white));

    }
}