package com.example.studio_group8;

public class Product {

    public String name;
    public String desc;
    public String image;
    public String sellerId;

    public Product() {

    }


    public Product(String name, String desc, String image, String sellerId) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.sellerId = sellerId;

    }


    public void setName(String name) {

        this.name = name;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public String getDesc() {

        return desc;
    }

    public String getName() {

        return name;
    }

    public String getImage() {

        return image;
    }
}

