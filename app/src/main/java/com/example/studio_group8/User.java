package com.example.studio_group8;
import android.graphics.Bitmap;

import java.util.LinkedList;

public class User
{
    public String email;
    public String firstname;
    public String username, userid, image;



    public String isAdmin;


    public User() {


    }

    public User(String userid, String email, String firstname, String username, String image)
    {
        this.userid = userid;
        this.email = email;
        this.firstname = firstname;
        this.username = username;
        this.image = image;
    }

    public void setImage (String image) {

        this.image = image;
    }

    public void setUserid (String userid) {

        this.userid = userid;
    }




    public void setName (String name) {

        this.firstname = name;
    }

    public void setUsername (String name) {

        this.username = name;
    }

    public String getImage() {

        return image;
    }

    public String getUserid() {

        return userid;
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

