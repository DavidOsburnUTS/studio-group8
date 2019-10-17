package com.example.studio_group8;

import java.text.DecimalFormat;

public class Cart {

    public String name;
    public String desc;
    public String image;
    public String category;
    public String sellerid;
    public String quantity;
    public String price;
    public String productid;

    DecimalFormat df = new DecimalFormat(" #.00");


    public Cart() {

    }


    public Cart(String productid, String sellerid, String name, String desc, String image, String quantity, String category, String price) {

        this.productid =productid;
        this.sellerid = sellerid;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.quantity=quantity;
        this.category=category;
        this.price = price;
    }

    public void setprice(String price) {
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

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getprice() {

        return price;
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
    public String getQuantity() {

        return quantity;
    }


}

