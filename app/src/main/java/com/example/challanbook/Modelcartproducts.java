package com.example.challanbook;



public class Modelcartproducts {

    String product,pprice,sprice,discount,discountamount,tax, taxamount, quantity, tprice, ppricebreak, ppricetotal,
            totalamount,  gmainprice, gdiscount, gtax, gtotal;




    public Modelcartproducts(String product, String pprice, String sprice, String discount, String discountamount, String tax, String taxamount, String quantity,
                             String tprice, String ppricebreak, String ppricetotal, String totalamount, String gmainprice, String gdiscount, String gtax, String gtotal) {
        this.product = product;
        this.pprice = pprice;
        this.sprice = sprice;
        this.discount = discount;
        this.discountamount = discountamount;
        this.tax = tax;
        this.taxamount = taxamount;
        this.quantity = quantity;
        this.tprice= tprice;
        this.ppricebreak= ppricebreak;
        this.ppricetotal=ppricetotal;
        this.totalamount = totalamount;
        this.gmainprice= gmainprice;
        this.gdiscount= gdiscount;
        this.gtax = gtax;
        this.gtotal= gtotal;
    }

    public String getProduct() {
        return product;
    }



    public void setProduct(String product) {
        this.product = product;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
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

    public String getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(String discountamount) {
        this.discountamount = discountamount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(String taxamount) {
        this.taxamount = taxamount;
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

    public String getPpricebreak() {
        return ppricebreak;
    }

    public void setPpricebreak(String ppricebreak) {
        this.ppricebreak = ppricebreak;
    }

    public String getPpricetotal() {
        return ppricetotal;
    }

    public void setPpricetotal(String ppricetotal) {
        this.ppricetotal = ppricetotal;
    }

    public String getTotalamount() {
        return totalamount;
    }



    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }


    public String getGmainprice() {
        return gmainprice;
    }

    public void setGmainprice(String gmainprice) {
        this.gmainprice = gmainprice;
    }

    public String getGdiscount() {
        return gdiscount;
    }

    public void setGdiscount(String gdiscount) {
        this.gdiscount = gdiscount;
    }

    public String getGtax() {
        return gtax;
    }

    public void setGtax(String gtax) {
        this.gtax = gtax;
    }

    public String getGtotal() {
        return gtotal;
    }

    public void setGtotal(String gtotal) {
        this.gtotal = gtotal;
    }
}
