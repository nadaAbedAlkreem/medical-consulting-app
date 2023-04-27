package com.example.medicalconsultingapplication.SpalshActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.medicalconsultingapplication.Authentication.ChoseActivity;
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
        Button btnRegsister = (Button) findViewById(R.id.btn_Login);
        TextView skipTextView = (TextView) findViewById(R.id.skipbtn);


        SliderBorderAdapter sliderAdapter = new SliderBorderAdapter(this);
        mslidePagerLayout.setRotationY(180);
        mslidePagerLayout.setAdapter(sliderAdapter);
        addDotsIndictor(0);
//        mslidePagerLayout.addOnPageChangeListener(viewListener);
        skipTextView.setOnClickListener(view -> openActivity());
        btnRegsister.setOnClickListener(view -> openActivity());
    }

    public  void openActivity( ) {
        Intent intent = new Intent(MainActivity.this   ,  ChoseActivity.class  );
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this ).toBundle());
    }

    public void addDotsIndictor(int position) {
        TextView[] mDots = new TextView[3];

        mdotesLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(25);
            mDots[i].setTextColor(ContextCompat.getColor(this, R.color.green));

            mdotesLayout.addView(mDots[i]);
        }
        mDots[position].setTextColor(ContextCompat.getColor(this,R.color.white));

    }


  }