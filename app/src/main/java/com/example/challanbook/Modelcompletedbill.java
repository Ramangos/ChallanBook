package com.example.challanbook;



public class Modelcompletedbill {

    String outlet,	invoice,	date,	customer_name,	customer_num,	productname,	pprice,	sprice,	discount,
            tax,	quantity, proprice,	tprice,	ppricebreak,	ppricetotal,	mainpricetotal,	discounttotal,	taxtotal,
            gpricetotal,	paidamount, paymethod,	balanceamount,	profit;



    public Modelcompletedbill(String outlet, String invoice, String date, String customer_name, String customer_num,
                              String productname, String pprice, String sprice, String discount, String tax, String quantity,
                              String proprice, String tprice, String ppricebreak, String ppricetotal, String mainpricetotal, String discounttotal,
                              String taxtotal, String gpricetotal, String paidamount, String paymethod, String balanceamount, String profit)

    {
        this.outlet = outlet;
        this.invoice = invoice;
        this.date = date;
        this.customer_name = customer_name;
        this.customer_num = customer_num;
        this.productname = productname;
        this.pprice = pprice;
        this.sprice = sprice;
        this.discount = discount;
        this.tax = tax;
        this.quantity = quantity;
        this.proprice =proprice;
        this.tprice = tprice;
        this.ppricebreak = ppricebreak;
        this.ppricetotal = ppricetotal;
        this.mainpricetotal = mainpricetotal;
        this.discounttotal = discounttotal;
        this.taxtotal = taxtotal;
        this.gpricetotal = gpricetotal;
        this.paidamount = paidamount;
        this.paymethod= paymethod;
        this.balanceamount = balanceamount;
        this.profit = profit;
    }


    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_num() {
        return customer_num;
    }

    public void setCustomer_num(String customer_num) {
        this.customer_num = customer_num;
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

    public String getProprice() {
        return proprice;
    }

    public void setProprice(String proprice) {
        this.proprice = proprice;
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

    public String getMainpricetotal() {
        return mainpricetotal;
    }

    public void setMainpricetotal(String mainpricetotal) {
        this.mainpricetotal = mainpricetotal;
    }

    public String getDiscounttotal() {
        return discounttotal;
    }

    public void setDiscounttotal(String discounttotal) {
        this.discounttotal = discounttotal;
    }

    public String getTaxtotal() {
        return taxtotal;
    }

    public void setTaxtotal(String taxtotal) {
        this.taxtotal = taxtotal;
    }

    public String getGpricetotal() {
        return gpricetotal;
    }

    public void setGpricetotal(String gpricetotal) {
        this.gpricetotal = gpricetotal;
    }

    public String getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(String paidamount) {
        this.paidamount = paidamount;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getBalanceamount() {
        return balanceamount;
    }

    public void setBalanceamount(String balanceamount) {
        this.balanceamount = balanceamount;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
