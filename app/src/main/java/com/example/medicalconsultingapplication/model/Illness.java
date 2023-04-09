package com.example.medicalconsultingapplication.model;

import android.widget.ImageView;

public class Illness {
    String id;
    int imageIllness;
    String nameIllness;

    public Illness(String id, int imageIllness, String nameIllness) {
        this.id = id;
        this.imageIllness = imageIllness;
        this.nameIllness = nameIllness;
    }

    public String getId() {
        return id;
    }

    public int getImageIllness() {
        return imageIllness;
    }

    public String getNameIllness() {
        return nameIllness;
    }

}
