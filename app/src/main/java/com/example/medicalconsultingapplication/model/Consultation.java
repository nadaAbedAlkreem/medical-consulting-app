package com.example.medicalconsultingapplication.model;

public class Consultation {
    String id ="";
    String doctorName;
    int doctorImage;
    String consultation;
    String consultationHeader;
    String consultationLogo;


    public Consultation(String id, String doctorName, int doctorImage, String consultation) {
        this.id = id;
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
        this.consultation = consultation;
    }

    public Consultation(String id, String consultationHeader   , String consultationLogo ) {
        this.id = id;
        this.consultationHeader = consultationHeader ;
        this.consultationLogo = consultationLogo ;

    }

    public String getId() {
        return id;
    }

    public String getDoctorName() {
        return doctorName;
    }
    public String getConsultationHeader() {
        return consultationHeader;
    }
    public String getConsultationImage() {
        return consultationLogo;
    }

    public int getDoctorImage() {

        return doctorImage;
    }

    public String getConsultation() {
        return consultation;
    }
}
