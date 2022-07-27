package com.example.challanbook;



public class ModelAutocompleteTV {

    String product,pprice,sprice,discount,discountamount,tax,taxamount,totalamount;


    public ModelAutocompleteTV(String product, String pprice, String sprice, String discount, String discountamount, String tax, String taxamount, String totalamount) {
        this.product = product;
        this.pprice = pprice;
        this.sprice = sprice;
        this.discount = discount;
        this.discountamount = discountamount;
        this.tax = tax;
        this.taxamount = taxamount;
        this.totalamount = totalamount;
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

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }


}
