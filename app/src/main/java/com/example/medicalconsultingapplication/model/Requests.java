package com.example.medicalconsultingapplication.model;

public class Requests {
    String idRecievd  ;
    String idSend;
    String process;

    public Requests(String idRecievd,String idSend,String process ) {
        this.idRecievd = idRecievd;
        this.process=process;
        this.idSend=idSend;
    }
    public String getIdRecievd() {
        return idRecievd;
    }

    public String getIdSend() {
        return idSend;
    }
    public String getProcess() {
        return process;
    }

}
