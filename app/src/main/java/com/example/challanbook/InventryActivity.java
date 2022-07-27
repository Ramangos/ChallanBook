package com.example.challanbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventryActivity extends AppCompatActivity {


    ArrayList<Modelproducts> productnamelist = new ArrayList<>();
    public Adapterproduct adapterproduct;
    RecyclerView recyclerView;
    Button addItem;
    double pri,dis,tx;
    String sproduct,spprice,ssprice,sdiscount,sdiscountamount,stax,staxamount,stotalamount;
    AlertDialog progressbardail;
    EditText inventorysearcheditext;
    SharedPreferences sp;
    String getoutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventry);

        adapterproduct=new Adapterproduct(productnamelist);
        recyclerView=findViewById(R.id.inventryrc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(InventryActivity.this));
        recyclerView.setAdapter(adapterproduct);

        sp = getSharedPreferences("spfile",0);
        getoutlet =sp.getString("outletspkey", "");

        inventorysearcheditext =findViewById(R.id.inventorysearcheditext);

        inventorysearcheditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });



        addItem=findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(InventryActivity.this,R.style.Bottomsheetdialtheme);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_inventry);
                bottomSheetDialog.setCanceledOnTouchOutside(false);



                EditText product=bottomSheetDialog.findViewById(R.id.product);
                EditText purchaseprice=bottomSheetDialog.findViewById(R.id.purchaseprice);
                EditText sellingprice=bottomSheetDialog.findViewById(R.id.sellingprice);
                EditText discount=bottomSheetDialog.findViewById(R.id.discount);
                EditText tax=bottomSheetDialog.findViewById(R.id.tax);
                EditText totalamount=bottomSheetDialog.findViewById(R.id.tamount);
                Button additem = bottomSheetDialog.findViewById(R.id.additem);
                Button addandclose =bottomSheetDialog.findViewById(R.id.addandclose);

                sellingprice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(sellingprice.getText().toString().isEmpty())
                        {
                            pri=0;
                        }
                        else
                        {
                            pri = Double.parseDouble(sellingprice.getText().toString());
                        }

                        if(discount.getText().toString().isEmpty())
                        {
                            dis=0;
                        }
                        else
                        {
                            dis = Double.parseDouble(discount.getText().toString());
                        }
                        if(tax.getText().toString().isEmpty())
                        {
                            tx=0;
                        }
                        else
                        {
                            tx = Double.parseDouble(tax.getText().toString());
                        }


                        double dper = pri*dis/100;
                        double afterdiscount = pri-dper;

                        double tper = afterdiscount*tx/100;
                        double aftertax = afterdiscount+tper;

                        totalamount.setText(String.format("%.2f",aftertax));


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                discount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {




                        if(sellingprice.getText().toString().isEmpty())
                        {
                            pri=0;
                        }
                        else
                        {
                            pri = Double.parseDouble(sellingprice.getText().toString());
                        }

                        if(discount.getText().toString().isEmpty())
                        {
                            dis=0;
                        }
                        else
                        {
                            dis = Double.parseDouble(discount.getText().toString().replaceAll("%",""));
                        }
                        if(tax.getText().toString().isEmpty())
                        {
                            tx=0;
                        }
                        else
                        {
                            tx = Double.parseDouble(tax.getText().toString());
                        }


                        double dper = pri*dis/100;
                        double afterdiscount = pri-dper;

                        double tper = afterdiscount*tx/100;
                        double aftertax = afterdiscount+tper;

                        totalamount.setText(String.format("%.2f",aftertax));



                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                tax.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(sellingprice.getText().toString().isEmpty())
                        {
                            pri=0;
                        }
                        else
                        {
                            pri = Double.parseDouble(sellingprice.getText().toString());
                        }

                        if(discount.getText().toString().isEmpty())
                        {
                            dis=0;
                        }
                        else
                        {
                            dis = Double.parseDouble(discount.getText().toString());
                        }
                        if(tax.getText().toString().isEmpty())
                        {
                            tx=0;
                        }
                        else
                        {
                            tx = Double.parseDouble(tax.getText().toString());
                        }


                        double dper = pri*dis/100;
                        double afterdiscount = pri-dper;

                        double tper = afterdiscount*tx/100;
                        double aftertax = afterdiscount+tper;

                        totalamount.setText(String.format("%.2f",aftertax));


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                totalamount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast,
                                (ViewGroup) findViewById(R.id.toast_layout_root));
                        TextView text = (TextView) layout.findViewById(R.id.text);
                        text.setText("Total amount automatically calculated");


                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM, 0, 40);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                });



                additem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        if(product.getText().toString().isEmpty())
                        {
                            Toast.makeText(InventryActivity.this, "Please Enter Product Name", Toast.LENGTH_SHORT).show();
                            product.setError("Please Enter Product Name");
                            product.requestFocus();

                        }

                        if(purchaseprice.getText().toString().isEmpty())
                        {
                            purchaseprice.setText("0");
                            //spprice="0";
                        }


                        if(sellingprice.getText().toString().isEmpty()  )
                        {
                            Toast.makeText(InventryActivity.this, "Please Enter Selling Price", Toast.LENGTH_SHORT).show();
                            sellingprice.setError("Please Enter Selling Price");
                            sellingprice.requestFocus();


                        }

                        if(sellingprice.getText().toString().contentEquals("0"))
                        {
                            Toast.makeText(InventryActivity.this, "Selling Price can not be Zero", Toast.LENGTH_SHORT).show();
                        }


                        if(discount.getText().toString().isEmpty())
                        {
                            discount.setText("0");
                            //sdiscount= "0";
                        }


                        if(tax.getText().toString().isEmpty())
                        {
                            tax.setText("0");
                            //stax="0";
                        }


                        if(!product.getText().toString().isEmpty() && !sellingprice.getText().toString().isEmpty()
                                && !sellingprice.getText().toString().contentEquals("0"))
                        {

                            if(Integer.valueOf(purchaseprice.getText().toString()) > Integer.valueOf(sellingprice.getText().toString()))
                            {
                                Toast.makeText(InventryActivity.this, "Purchase price can not be greater than Selling price", Toast.LENGTH_SHORT).show();
                            }
                            else if( Integer.valueOf(sellingprice.getText().toString()) >= Integer.valueOf(purchaseprice.getText().toString()) )
                            {



                            sproduct= product.getText().toString();
                            spprice= purchaseprice.getText().toString();
                            ssprice= sellingprice.getText().toString();
                            sdiscount= discount.getText().toString();
                            stax= tax.getText().toString();
                            stotalamount= totalamount.getText().toString();

                            insertinventryitem();

                           /* product.setText("");
                            purchaseprice.setText("");
                            sellingprice.setText("");
                            discount.setText("");
                            tax.setText("");
                            totalamount.setText("");
                            product.requestFocus();*/

                            }









                        }













                    }
                });

                addandclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(product.getText().toString().isEmpty())
                        {
                            Toast.makeText(InventryActivity.this, "Please Enter Product Name", Toast.LENGTH_SHORT).show();
                            product.setError("Please Enter Product Name");
                            product.requestFocus();

                        }

                        if(purchaseprice.getText().toString().isEmpty())
                        {
                            purchaseprice.setText("0");
                            //spprice="0";
                        }


                        if(sellingprice.getText().toString().isEmpty()  )
                        {
                            Toast.makeText(InventryActivity.this, "Please Enter Selling Price", Toast.LENGTH_SHORT).show();
                            sellingprice.setError("Please Enter Selling Price");
                            sellingprice.requestFocus();


                        }

                        if(sellingprice.getText().toString().contentEquals("0"))
                        {
                            Toast.makeText(InventryActivity.this, "Selling Price can not be Zero", Toast.LENGTH_SHORT).show();
                        }


                        if(discount.getText().toString().isEmpty())
                        {
                            discount.setText("0");
                            //sdiscount= "0";
                        }


                        if(tax.getText().toString().isEmpty())
                        {
                            tax.setText("0");
                            //stax="0";
                        }


                        if(!product.getText().toString().isEmpty() && !sellingprice.getText().toString().isEmpty()
                                && !sellingprice.getText().toString().contentEquals("0"))
                        {

                            if(Integer.valueOf(purchaseprice.getText().toString()) > Integer.valueOf(sellingprice.getText().toString()))
                            {
                                Toast.makeText(InventryActivity.this, "Purchase price can not be greater than Selling price", Toast.LENGTH_SHORT).show();
                            }
                            else if( Integer.valueOf(sellingprice.getText().toString()) >= Integer.valueOf(purchaseprice.getText().toString()) )
                            {



                                sproduct= product.getText().toString();
                                spprice= purchaseprice.getText().toString();
                                ssprice= sellingprice.getText().toString();
                                sdiscount= discount.getText().toString();
                                stax= tax.getText().toString();
                                stotalamount= totalamount.getText().toString();

                                insertinventryitem();

                                bottomSheetDialog.dismiss();

                            }

                        }









                    }
                });




                bottomSheetDialog.show();

            }
        });


        progessbarshowmethod();



    }

    @Override
    protected void onResume() {
        super.onResume();

        getinventry();
    }

    public void getinventry() {



        recyclerView.setVisibility(View.INVISIBLE);
        productnamelist.clear();
        progressbardail.show();

        RequestQueue rq = Volley.newRequestQueue(InventryActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/fetchinventry.php?o="+getoutlet;

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressbardail.dismiss();



                showJson(response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InventryActivity.this, "NOt success => " + error.toString(), Toast.LENGTH_SHORT).show();





            }
        });
        rq.add(sr);

    }

    private void showJson(String response) {

        try {


            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {

                JSONObject jsonObject1 = result.getJSONObject(i);


                productnamelist.add(new Modelproducts(

                        jsonObject1.getString(Config.KEY_product),
                        jsonObject1.getString(Config.KEY_pprice),
                        jsonObject1.getString(Config.KEY_sprice),
                        jsonObject1.getString(Config.KEY_discount),
                        jsonObject1.getString(Config.KEY_discountamount),
                        jsonObject1.getString(Config.KEY_tax),
                        jsonObject1.getString(Config.KEY_taxamount),
                        jsonObject1.getString(Config.KEY_total_amount)

                ));

                recyclerView.setAdapter(adapterproduct);
                recyclerView.setVisibility(View.VISIBLE);
                adapterproduct.notifyDataSetChanged();



            }


        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void insertinventryitem() {

        progressbardail.show();



        RequestQueue rq = Volley.newRequestQueue(InventryActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/insertInventryProduct.php";
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {




            @Override
            public void onResponse(String response) {
                Toast.makeText(InventryActivity.this, "okkkk register "   + response, Toast.LENGTH_SHORT).show();
                progressbardail.hide();

                onResume();
                /*showJson2(response);*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InventryActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {





                HashMap<String, String> hm = new HashMap<String, String>();

                double disam = pri*dis/100;

                double afterdiscount = pri-disam;

                double taxam = afterdiscount*tx/100;

                sdiscountamount = String.valueOf(disam);
                staxamount = String.valueOf(taxam);


                //hm.put("params", json.toString());
                hm.put(Config.KEY_outlet, getoutlet);
                hm.put(Config.KEY_product, sproduct);
                hm.put(Config.KEY_pprice, spprice);
                hm.put(Config.KEY_sprice, ssprice);
                hm.put(Config.KEY_discount, sdiscount);
                hm.put(Config.KEY_discountamount, sdiscountamount);
                hm.put(Config.KEY_tax, stax);
                hm.put(Config.KEY_taxamount, staxamount);
                hm.put(Config.KEY_total_amount, stotalamount);




                return hm;
            }

        };


        rq.add(sr);
    }




    public  void progessbarshowmethod()
    {
        progressbardail = new AlertDialog.Builder(InventryActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
        progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressbardail.setView(dialogView);
        progressbardail.show();





    }



    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Modelproducts> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Modelproducts s : productnamelist) {
            //if the existing elements contains the search input
            if (s.getProduct().toString().toLowerCase().contains(text.toLowerCase())


            ) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapterproduct.filterList(filterdNames);
    }



}