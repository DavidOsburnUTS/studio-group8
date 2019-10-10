package com.example.studio_group8;
import android.graphics.Bitmap;

import java.util.LinkedList;

public class User
{
    public String email;
    public String firstname;
    public String username;



    public String isAdmin;


    public User() {


    }

    public User(String email, String firstname, String username)
    {
        this.email = email;
        this.firstname = firstname;
        this.username = username;
    }



    public void setName (String name) {

        this.firstname = name;
    }

    public void setUsername (String name) {

        this.username = name;
    }




    public String getUsername() {

        return username;
    }
    public String getName() {

        return firstname;
    }

    public String getEmail() {

        return email;
    }



}

