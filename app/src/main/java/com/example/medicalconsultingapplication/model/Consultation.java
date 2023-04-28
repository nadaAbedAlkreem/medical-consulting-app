package com.example.medicalconsultingapplication.model;

public class Consultation {
    String id;
    String doctorId;
    String doctorAuth;
    String doctorName;
    String doctorImage;
    String consultation; // content
    String consultationHeader; //title
    //    int consultationLogo;
    String consultionLogo;
    String consultionInfo;
    String video;

    public Consultation(String id, String doctorName, String consultationHeader, String doctorImage) {
        this.id = id;
        this.doctorName = doctorName;
        this.consultationHeader = consultationHeader;
        this.doctorImage = doctorImage;
    }

    public Consultation(String id, String consultionLogo, String consultationHeader    ) {
        this.id = id;
        this.consultionLogo = consultionLogo;
        this.consultationHeader = consultationHeader;
     }


    public String getId() {
        return id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDoctorAuth() {
        return doctorAuth;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public String getConsultationHeader() {
        return consultationHeader;
    }

    public String getConsultation() {
        return consultation;
    }

    public String getConsultionLogo() {
        return consultionLogo;
    }

    public String getConsultionInfo() {
        return consultionInfo;
    }

    public String getVideo() {
        return video;
    }

}