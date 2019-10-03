package com.example.studio_group8;

public class Product {

    public String name;
    public String desc;
    public String image;
//    public String sellerId;
    public String quantity;

    public Product() {

    }


    public Product(String name, String desc, String image, String quantity) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.quantity=quantity;
//        this.sellerId = sellerId;

    }

/*
    public void setSellerId(String sellerId) {

        this.sellerId = sellerId;
    }
*/

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


/*
    public String getSellerID() {

        return sellerId;
    }
*/


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

