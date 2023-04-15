package com.example.medicalconsultingapplication.SpalshActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.medicalconsultingapplication.Authentication.ChoseActivity;
import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.AdapterSlider.SliderAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mslidePagerLayout;
    private LinearLayout mdotesLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button   btnRegsister ;
    private TextView skipTextView ;
    private int currentPage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mslidePagerLayout = findViewById(R.id.slidePagerlayout);
        mdotesLayout = findViewById(R.id.dotslayout);
         btnRegsister =(Button) findViewById(R.id.btn_Login);
         skipTextView =(TextView) findViewById(R.id.skipbtn);


        sliderAdapter = new SliderAdapter(this);
        mslidePagerLayout.setRotationY(180);
         mslidePagerLayout.setAdapter(sliderAdapter);
        addDotsIndictor(0);
        mslidePagerLayout.addOnPageChangeListener(viewListener);
        skipTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });
        btnRegsister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity() ;
            }
        });
    }
    public  void openActivity( )
    {
        Intent intent = new Intent(MainActivity.this   ,  ChoseActivity.class  );
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this ).toBundle());

    }

    public void addDotsIndictor(int position)
    {
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
             mDots[position].setTextColor(getResources().getColor(R.color.white));

        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            addDotsIndictor(position);
            currentPage =  position  ;


            if(position == mDots.length-1 ){
                btnRegsister.setEnabled(true);
                btnRegsister.setVisibility(View.VISIBLE);


            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };
}