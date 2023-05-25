package com.example.medicalconsultingapplication.model;
public class Users {
    String id = "";
    String idUserAuth;
    String UserName;
    String UserImage;
    String Mobile;
    String Address;
    String Birthday;
    String TypeUser;
    String doctorCategory = "";

    public Users(String id, String idUserAuth , String doctorCategory,String userName,String image ) {
        this.id = id;
        this.doctorCategory=doctorCategory;
        this.UserImage = image;
        this.UserName = userName;
        this.idUserAuth = idUserAuth;

    }
    public Users(String id) {
        this.id = id;
    }

    public Users(String id, String userName) {
        this.id = id;
        this.UserName = userName;
    }
    public Users(String id, String userName,String UserImage, String TypeUser ) {
        this.id = id;
        this.UserName = userName;
        this.UserImage=UserImage;
        this.TypeUser=TypeUser;

    }

    public Users(String id, String userName ,  String UserImage) {
        this.id = id;
        this.UserName = userName;
        this.UserImage = UserImage;

    }

    public Users(String id, String idUserAuth, String UserName, String UserImage, String Mobile
            , String Address, String Birthday, String typeUser, String doctorCategory) {
        this.id = id;
        this.idUserAuth = idUserAuth;
        this.UserName = UserName;
        this.UserImage = UserImage;
        this.Mobile = Mobile;
        this.Address = Address;
        this.Birthday = Birthday;
        this.TypeUser = typeUser;
        this.doctorCategory = doctorCategory;
    }


    public String getId() {
        return id;
    }

    public String getIdUserAuth() {
        return idUserAuth;
    }

    public String getUserName() {
        return UserName;
    }
    public String getUserImage() {
        return UserImage;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getAddress() {
        return Address;
    }

    public String getTypeUser() {
        return TypeUser;
    }


    public String getDoctorCategory() {
        return doctorCategory;
    }
}