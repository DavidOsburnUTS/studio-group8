package com.example.studio_group8;

import java.text.DecimalFormat;

public class adminProduct {

    public String name;
    public String desc;
    public double price;
    public String productid;

    DecimalFormat df = new DecimalFormat(" #.00");


    public adminProduct() {

    }


    public adminProduct(String productid, String name, String desc, double price) {

        this.productid =productid;
        this.name = name;
        this.desc = desc;
        this.price = price;


    }

    public void setprice(double price) {
        this.price = price;
    }

    public void setproductid(String productid) {

        this.productid = productid;
    }




    public void setName(String name) {

        this.name = name;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }


    public double getprice() {

        return Double.parseDouble(df.format(price));
    }

    public String getproductid() {

        return productid;
    }






    public String getDesc() {

        return desc;
    }

    public String getName() {

        return name;
    }




}

