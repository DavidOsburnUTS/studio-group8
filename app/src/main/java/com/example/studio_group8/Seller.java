package com.example.studio_group8;



public class Seller {
    public String name, userid;
    public String email;
    public String phone;
    public String isAdmin;


    public Seller () {

    }


    public Seller(String userid, String email, String name, String phone)
    {
        this.userid = userid;
        this.email = email;
        this.name = name;
        this.phone = phone;

        this.isAdmin = "no";
    }

    public void setUserid (String userid) {

        this.userid = userid;
    }


    public void setName (String name) {

        this.name = name;
    }

    public void setEmail (String name) {

        this.email = email;
    }

    public void setPhone (String phone) {

        this.phone = phone;
    }

    public String getUserid() {

        return userid;
    }



    public String getName() {

        return name;
    }
    public String getEmail() {

        return email;
    }

    public String getPhone() {

        return phone;
    }




}


