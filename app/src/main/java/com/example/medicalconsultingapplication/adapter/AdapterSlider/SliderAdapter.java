package com.example.medicalconsultingapplication.adapter.AdapterSlider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.medicalconsultingapplication.R;

public class SliderAdapter extends PagerAdapter {
    Context context ;
    LayoutInflater layoutInflater ;
    public  SliderAdapter(Context context){
        this.context = context  ;


    }
    public  int[] slide_image  = {
            R.drawable.doctor ,
            R.drawable.brain ,
            R.drawable.doc_docto

    };
    public String[] slide_heading ={
      "In this application, medicines and sweats are suggested for several medical conditions " ,
      "And also provide a discussion service between you and your preferred doctor ",
            "Daily status reports and crisis procedures for your illness are displayed ",
    };


    @Override
    public int getCount() {
        return slide_heading.length ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == (RelativeLayout) object  ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
        View view  = layoutInflater.inflate(R.layout.slide_layout  , container  , false) ;
        ImageView slideImage = (ImageView) view.findViewById(R.id.slide_imge) ;
        TextView slideHeading= (TextView) view.findViewById(R.id.slide_heading) ;

        slideImage.setImageResource(slide_image[position]);
        slideHeading.setText(slide_heading[position]);
         container.addView(view);
        return  view ;

     }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
         container.removeView((RelativeLayout)object);
    }
}
