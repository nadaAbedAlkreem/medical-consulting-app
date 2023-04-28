package com.example.medicalconsultingapplication.SpalshActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
<<<<<<< HEAD
import android.util.Log;
import android.view.View;
=======
>>>>>>> 5bfe048b9cb4d5e3d8c5f603ade89141d01c10b0
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
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
        mslidePagerLayout =(ViewPager2) findViewById(R.id.slidePagerlayout);
        mdotesLayout = findViewById(R.id.dotslayout);
        Button btnRegsister = (Button) findViewById(R.id.btn_Login);
        TextView skipTextView = (TextView) findViewById(R.id.skipbtn);


        SliderBorderAdapter sliderAdapter = new SliderBorderAdapter(this);
        mslidePagerLayout.setRotationY(180);
        mslidePagerLayout.setAdapter(sliderAdapter);
        addDotsIndictor(0);
<<<<<<< HEAD
        mslidePagerLayout.registerOnPageChangeCallback (viewListener);


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
=======
//        mslidePagerLayout.addOnPageChangeListener(viewListener);
        skipTextView.setOnClickListener(view -> openActivity());
        btnRegsister.setOnClickListener(view -> openActivity());
    }
>>>>>>> 5bfe048b9cb4d5e3d8c5f603ade89141d01c10b0

    public  void openActivity( ) {
        Intent intent = new Intent(MainActivity.this   ,  ChoseActivity.class  );
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this ).toBundle());
    }

<<<<<<< HEAD
    public void addDotsIndictor(int position)
    {
         mDots = new TextView[3];
=======
    public void addDotsIndictor(int position) {
        TextView[] mDots = new TextView[3];
>>>>>>> 5bfe048b9cb4d5e3d8c5f603ade89141d01c10b0

        mdotesLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++)
        {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(25);
            mDots[i].setTextColor(ContextCompat.getColor(this, R.color.gold));

            mdotesLayout.addView(mDots[i]);
        }
<<<<<<< HEAD
//        if(){
//
//        }
        mDots[position].setTextColor(ContextCompat.getColor(this, R.color.white));
=======
        mDots[position].setTextColor(ContextCompat.getColor(this,R.color.white));
>>>>>>> 5bfe048b9cb4d5e3d8c5f603ade89141d01c10b0

    }

    ViewPager2.OnPageChangeCallback viewListener  = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndictor(position) ;
            currentPage = position ;
            Log.e("nda"  , String.valueOf(mDots.length));
            if(position == mDots.length-1){
                btnRegsister.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

  }
