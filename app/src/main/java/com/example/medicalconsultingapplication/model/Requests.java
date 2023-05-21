package com.example.medicalconsultingapplication.model;

import android.widget.ImageView;

public class Requests {
    String id ;
    String idRecievd  ;
    String idSend;
    String status;
    String ImageReciver ;
    String NameReviver ;

    public Requests(String idRecievd,String idSend,String status ) {
        this.idRecievd = idRecievd;
        this.status=status;
        this.idSend=idSend;
    }
    public Requests( String id  , String idRecievd, String idSend, String status , String imageReciver , String NameReviver) {
        this.id = id ;
        this.idRecievd = idRecievd;
        this.status=status;
        this.idSend=idSend;
        this.ImageReciver=imageReciver;
        this.NameReviver=NameReviver;

    }



   public  String getImageReciver()
   {
       return  ImageReciver ;
   }
    public String getIdRecievd() {
        return idRecievd;
    }
    public String getId()
    {
        return  id ;
    }
    public String getNameReviver() {
        return NameReviver;
    }

    public String getIdSend() {
        return idSend;
    }
    public String getStatus() {
        return status;
    }

}
