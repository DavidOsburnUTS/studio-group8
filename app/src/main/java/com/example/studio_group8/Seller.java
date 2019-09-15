package com.example.studio_group8;



public class Seller {
    public String name;
    public String email;
    public String phone;
    public String isAdmin;


    public Seller () {

    }


    public Seller(String name, String email, String phone)
    {
        this.email = email;
        this.name = name;
        this.phone = phone;

        this.isAdmin = "no";
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


