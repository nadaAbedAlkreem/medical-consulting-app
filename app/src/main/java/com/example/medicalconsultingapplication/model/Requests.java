package com.example.medicalconsultingapplication.model;

public class Requests {
    String idRecievd  ;
    String idSend;
    String Statous;
    String username;
    String image;

    public Requests(String idRecievd,String idSend,String Statous,String username,String image ) {
        this.idRecievd = idRecievd;
        this.Statous=Statous;
        this.idSend=idSend;
        this.username=username;
        this.image=image;
    }
    public String getIdRecievd() {
        return idRecievd;
    }

    public String getIdSend() {
        return idSend;
    }
    public String getStatous() {
        return Statous;
    }
    public String getUserName() {
        return username;
    }
    public String getImage() {
        return image;
    }
}
