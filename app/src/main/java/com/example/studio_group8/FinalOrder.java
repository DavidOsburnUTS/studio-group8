package com.example.studio_group8;

public class FinalOrder {
    String currentuser, orderid,total,cardnumber, postalcode,mobilenumber;

    public FinalOrder(){

    }

    public FinalOrder(String currentuser, String orderid, String total, String cardnumber,
                      String postalcode,
                      String mobilenumber){

        this.currentuser =currentuser;
        this.orderid =orderid;
        this.total =total;
        this.cardnumber =cardnumber;
        this.postalcode =postalcode;
        this.mobilenumber =mobilenumber;
    }

    public void setordercurrentuser(String currentuser ) {
        this.currentuser = currentuser;
    }

    public String getcurrentuser() {

        return currentuser ;
    }

    public void setorderid(String orderid ) {
        this.orderid = orderid;
    }

    public String getorderid() {

        return orderid ;
    }

    public void settotal (String total ) {
        this.total = total;
    }

    public String gettotal() {

        return total ;
    }
    public void setcardnumber (String cardnumber ) {
        this.cardnumber = cardnumber;
    }

    public String getcardnumber() {

        return  cardnumber;
    }

    public String getpostalcode() {

        return  postalcode;
    }
    public void setpostalcode (String postalcode ) {
        this.postalcode =postalcode ;
    }

    public String getmobilenumber() {

        return mobilenumber ;
    }
    public void setmobilenumber (String mobilenumber ) {
        this.mobilenumber = mobilenumber;
    }



}
