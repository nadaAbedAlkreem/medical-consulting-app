package com.example.medicalconsultingapplication.model;

public class Doctor {
    String id;
    String doctorName;
    int doctorImage;
    String doctorCategory;

    public Doctor(String id, String doctorName, int doctorImage, String doctorCategory) {
        this.id = id;
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
        this.doctorCategory = doctorCategory;
    }

    public String getId() {
        return id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getDoctorImage() {
        return doctorImage;
    }

    public String getDoctorCategory() {
        return doctorCategory;
    }
}
