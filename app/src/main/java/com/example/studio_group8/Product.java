package com.example.studio_group8;

import java.text.DecimalFormat;

public class Product {

    public String name;
    public String desc;
    public String image;
    public String category;
    public String sellerid;
    public int quantity;
    public double price;
    public String productid;

    DecimalFormat df = new DecimalFormat(" #.00");


    public Product() {

    }


    public Product(String productid, String sellerid, String name, String desc, String image, int quantity, String category, double price) {
        this.productid =productid;
        this.sellerid = sellerid;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.quantity=quantity;
        this.category=category;
        this.price = price;


    }

    public void setprice(double price) {
        this.price = price;
    }

    public void setproductid(String productid) {

        this.productid = productid;
    }

    public void setSellerid(String sellerid) {

        this.sellerid = sellerid;
    }

public void setcategory(String category) {

    this.category = category;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getprice() {

        return Double.parseDouble(df.format(price));
    }

    public String getproductid() {

        return productid;
    }


    public String getSellerid() {

        return sellerid;
    }


    public String getcategory() {

        return category;
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
    public int getQuantity() {

        return quantity;
    }


}

