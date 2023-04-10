package com.example.medicalconsultingapplication.SpalshActivity;

import static android.view.View.FOCUS_RIGHT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.AdapterSlider.SliderAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mslidePagerLayout;
    private LinearLayout mdotesLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button btnFinisded  , btnDesign ;
    private int currentPage ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mslidePagerLayout = findViewById(R.id.slidePagerlayout);
        mdotesLayout = findViewById(R.id.dotslayout);
//        btnFinisded =(Button) findViewById(R.id.btn);
//        btnDesign =(Button) findViewById(R.id.designbtn);

//        LottieAnimationView animationView = findViewById(R.id.anim);
//         animationView.setAnimation(R.raw.doctor_icon);
//        animationView.loop(true);
//        animationView.playAnimation();
        sliderAdapter = new SliderAdapter(this);
        mslidePagerLayout.setRotationY(180);
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
             mDots[position].setTextColor(getResources().getColor(R.color.white));

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
//            Log.e("nadapo" , String.valueOf(position));
//            Log.e("nadapo" , String.valueOf(mDots.length ));
//
//            if(position == mDots.length-1 ){
//                btnFinisded.setEnabled(true);
//                btnDesign.setEnabled(true);
//                btnFinisded.setVisibility(View.VISIBLE);
//                btnDesign.setVisibility(View.VISIBLE);
//                Log.e("nadapo" , String.valueOf(position));
//
//
//            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };
}