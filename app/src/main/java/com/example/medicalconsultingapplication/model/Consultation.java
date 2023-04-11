package com.example.medicalconsultingapplication.model;

public class Consultation {
    String id;
    String doctorName;
    int doctorImage;
    String consultation;

    public Consultation(String id, String doctorName, int doctorImage, String consultation) {
        this.id = id;
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
        this.consultation = consultation;
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

    public String getConsultation() {
        return consultation;
    }
}
