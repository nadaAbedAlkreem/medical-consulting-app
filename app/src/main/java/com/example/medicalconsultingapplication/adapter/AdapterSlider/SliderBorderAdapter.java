package com.example.medicalconsultingapplication.adapter.AdapterSlider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.medicalconsultingapplication.R;

public class SliderBorderAdapter extends RecyclerView.Adapter<SliderBorderAdapter.ViewHolder> {

    Context context;

    public SliderBorderAdapter(Context context) {
        this.context = context;
    }


    public int[] slide_image = {
            R.raw.pharmacist,
            R.raw.medical_icon,
            R.raw.medical_assistance_animation

    };

    String name = "ندى ";
    public String[] slide_heading = {
            name,
            "ffff ",
            " ",
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView slideImage;
        TextView slideHeading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slideImage = itemView.findViewById(R.id.slide_imge);
            slideHeading = itemView.findViewById(R.id.slide_heading);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slide_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.slideImage.setAnimation(slide_image[position]);
        holder.slideHeading.setText(slide_heading[position]);
    }

    @Override
    public int getItemCount() {
        return slide_image.length;
    }

}
