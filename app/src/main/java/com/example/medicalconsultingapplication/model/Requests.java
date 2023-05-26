package com.example.medicalconsultingapplication.model;

public class Requests {
    String id;
    String idRecievd;
    String idSend;

    String status;
    String username;
    String image;
    int imageActive;
    String idAuth ;


    public Requests(       String idSend, String idRecievd, String status, String username, String imageReciver    ) {
         this.idRecievd = idRecievd;
        this.status = status;
        this.idSend = idSend;
        this.username = username;
        this.image = imageReciver;
    }

    public Requests(String id, int imageActive, String nameActive) {
        this.id = id;
        this.imageActive = imageActive;
        this.username = nameActive;
    }

    public Requests(  String idSend, String idRecievd, String username, String imageReciver ) {
        this.idRecievd = idRecievd;
        this.idSend = idSend;
        this.username = username;
        this.image = imageReciver;
     }

    public Requests(String id, String idRecievd, String idSend, String status, String imageReciver, String NameReviver
            ) {
         this.id = id;
        this.idRecievd = idRecievd;
        this.status = status;
        this.idSend = idSend;
        this.image = imageReciver;
        this.username = NameReviver;




    }


    public String getImageReciver() {
        return image;
    }

    public String getIdRecievd() {
        return idRecievd;
    }

    public String getId() {
        return id;
    }

    public String getNameReviver() {
        return username;
    }

    public String getIdSend() {
        return idSend;
    }

    public String getStatus() {
        return status;
    }

    public int getImageActive() {
        return imageActive;
    }

}