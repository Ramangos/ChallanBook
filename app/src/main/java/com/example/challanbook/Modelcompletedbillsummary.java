package com.example.challanbook;



public class Modelcompletedbillsummary {

    String 	productname, pprice, ppricebreak,	sprice,	discount,
            tax,	quantity,	tprice;

    public Modelcompletedbillsummary(String productname, String pprice, String ppricebreak,  String sprice, String discount, String tax, String quantity, String tprice) {
        this.productname = productname;
        this.pprice =pprice;
        this.ppricebreak=ppricebreak;
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

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPpricebreak() {
        return ppricebreak;
    }

    public void setPpricebreak(String ppricebreak) {
        this.ppricebreak = ppricebreak;
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
