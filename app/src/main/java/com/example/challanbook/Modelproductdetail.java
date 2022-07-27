package com.example.challanbook;



public class Modelproductdetail {

    String 	productname,	sprice,	discount,
            tax,	quantity,	tprice;




    public Modelproductdetail(String productname, String sprice, String discount, String tax, String quantity, String tprice) {
        this.productname = productname;
        this.sprice = sprice;
        this.discount = discount;
        this.tax = tax;
        this.quantity = quantity;
        this.tprice = tprice;
    }



    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }
}
