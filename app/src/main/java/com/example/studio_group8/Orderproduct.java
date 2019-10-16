package com.example.studio_group8;

class Orderproduct {

    String orderid, productid, productname, imageurl, date;
    int quentity;
    public Orderproduct() {

    }
    public Orderproduct(String orderid, String productid, String productname, String imageurl, int quentity, String date){
        this.orderid = orderid;
        this.productid = productid;
        this.productname = productname;
        this.imageurl = imageurl;
        this.quentity = quentity;
        this.date = date;
    }

    public void setorderid(String orderid ) {
        this.orderid = orderid;
    }

    public String getorderid() {

        return orderid ;
    }

    public void setproductid(String productid) {
        this.productid =productid ;
    }

    public String getproductid() {

        return productid ;
    }

    public void setproductname(String productname) {
        this.productname = productname;
    }

    public String getproductname() {

        return productname ;
    }

    public void setimageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getimageurl() {

        return  imageurl;
    }

    public void setquentity(int quentity) {
        this.quentity = quentity;
    }

    public int getquentity() {

        return quentity ;
    }

    public void setdate(String quentity) {
        this.date = date;
    }

    public String getdate() {

        return date ;
    }

}
