package com.example.studio_group8;

import android.media.Image;

import java.io.File;

public class BuyerProfile {

    private String name;
    private Image image;

    public BuyerProfile (String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public String getName () {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage () {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
