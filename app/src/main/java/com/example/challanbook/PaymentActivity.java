package com.example.challanbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

        TextView customername,customernum, productamtotal,discounttotal,taxtotal,grandtotal,
                balanceamount,bottombalance,invoicenum,billdate, Shopname,Shopaddress,Shopphone;
        EditText paymentreceived;
        Chip pifchips;
        double preceived ;
        String rs_symbol = "\u20B9";
        double d_productamtotal,d_discounttotal,d_taxtotal,gtotal;
        String invoice,datee,productnames,pprice, sprice,discount,disamount ,taxes, taxamount,quantity,prodprice,tprice,ppricebreak ,  ppricetotal;
        RelativeLayout complete;
        Spinner spinner;
        String pmethod[] ={"Cash", "Card","Online", "Pm", "Phone", "Gp"};
        ArrayAdapter<String> adapter;
        ImageView menubutton;
        AlertDialog progressbardail;
        MainActivity mainActivity;
        SharedPreferences sp;
        String getoutletname,getoutletadd, getoutlet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();


        menubutton=findViewById(R.id.menubutton);
        menubutton.setVisibility(View.GONE);
        customername=findViewById(R.id.customername);
        customernum=findViewById(R.id.customernum);
        productamtotal=findViewById(R.id.productamtotal);
        discounttotal=findViewById(R.id.discounttotal);
        taxtotal=findViewById(R.id.taxtotal);
        grandtotal=findViewById(R.id.grandtotal);
        balanceamount=findViewById(R.id.balanceamount);
        paymentreceived=findViewById(R.id.paymentreceived);
        pifchips =findViewById(R.id.pifchips);
        bottombalance=findViewById(R.id.bottombalance);
        complete=findViewById(R.id.complete);
        invoicenum=findViewById(R.id.invoicenum);
        billdate=findViewById(R.id.billdate);

        Shopname=findViewById(R.id.Shopname);
        Shopaddress=findViewById(R.id.Shopaddress);
        Shopphone=findViewById(R.id.Shopphone);

        spinner = findViewById(R.id.pmspinn);
        adapter = new ArrayAdapter<String>(PaymentActivity.this,R.layout.spinner_design,pmethod);
        spinner.setAdapter(adapter);


        sp = getSharedPreferences("spfile",0);
        getoutletname =sp.getString("outletnamespkey","");
        getoutletadd =sp.getString("outletaddspkey","");
        getoutlet =sp.getString("outletspkey", "");

        progessbarshowmethod();


        Intent data = getIntent();

        invoice= data.getStringExtra("invoice");
        datee= data.getStringExtra("datee");
        customername.setText(data.getStringExtra("custname"));
        customernum.setText(data.getStringExtra("custnum"));
        productnames = data.getStringExtra("productname");
        pprice = data.getStringExtra("pprice");
        sprice = data.getStringExtra("sprice");
        discount = data.getStringExtra("discount");
        disamount= data.getStringExtra("disamount");
        taxes = data.getStringExtra("taxes");
        taxamount = data.getStringExtra("taxamount");
        quantity = data.getStringExtra("quantity");
        prodprice = data.getStringExtra("prodprice");
        tprice = data.getStringExtra("tprice");
        ppricebreak = data.getStringExtra("ppricebreak");
        ppricetotal =  data.getStringExtra("ppricetotal");

       if(data.hasExtra("invkey")) {

            String ikey = data.getStringExtra("invkey");

            Toast.makeText(PaymentActivity.this, "key "+ikey, Toast.LENGTH_SHORT).show();
        }


        //Toast.makeText(mainActivity, "key "+data.getStringExtra("invkey"), Toast.LENGTH_SHORT).show();


        Shopname.setText(getoutletname);
        Shopaddress.setText(getoutletadd);
        Shopphone.setText(getoutlet);



        d_productamtotal = Double.valueOf(data.getStringExtra("gmainprice"));
        productamtotal.setText(rs_symbol+String.format("%.2f", d_productamtotal));

        d_discounttotal = Double.valueOf(data.getStringExtra("gdisamount"));
        discounttotal.setText(rs_symbol+ String.format("%.2f",d_discounttotal));

        d_taxtotal = Double.valueOf(data.getStringExtra("gtaxamount"));
        taxtotal.setText(rs_symbol+String.format("%.2f", d_taxtotal));


        gtotal =Double.valueOf( data.getStringExtra("gtotal"));
        grandtotal.setText(rs_symbol+ String.format("%.0f", gtotal));

        Toast.makeText(PaymentActivity.this, d_discounttotal+ " ", Toast.LENGTH_SHORT).show();


        invoicenum.setText(invoice);
        billdate.setText(datee);

        preceived = Double.valueOf( grandtotal.getText().toString().replace(rs_symbol,""));
        pifchips.setAnimation(AnimationUtils.loadAnimation(PaymentActivity.this,R.anim.slide_in_right));

        pifchips.setText("Paid in full - " + grandtotal.getText().toString());

        pifchips.setOnCloseIconClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                pifchips.setAnimation(AnimationUtils.loadAnimation(PaymentActivity.this,R.anim.slide_out_right));



                pifchips.setVisibility(View.GONE);

                paymentreceived.setVisibility(View.VISIBLE);

                paymentreceived.setAnimation(AnimationUtils.loadAnimation(PaymentActivity.this,R.anim.slide_up));
                paymentreceived.setText("");
              
               // balanceamount.setText(rs_symbol+ String.format("%.0f", gtotal - preceived  )  );
                //bottombalance.setText(rs_symbol+ String.format("%.0f",  gtotal - preceived    ) + " /- Balance");

            }
        });


        paymentreceived.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




                if(paymentreceived.getText().toString().isEmpty())
                {
                    preceived =0;
                }

                else
                {
                    preceived =   Double.valueOf(paymentreceived.getText().toString());
                }



                double balamount = gtotal-preceived;


                balanceamount.setText(rs_symbol+ String.format("%.0f", balamount));
                bottombalance.setText(rs_symbol+ String.format("%.0f", balamount) + " Balance");

                if  (Double.valueOf( balanceamount.getText().toString().replace(rs_symbol,"") ) <= 0 )
                {
                    paymentreceived.setText("");
                    pifchips.setAnimation(AnimationUtils.loadAnimation(PaymentActivity.this,R.anim.slide_in_right));
                    paymentreceived.setAnimation(AnimationUtils.loadAnimation(PaymentActivity.this,R.anim.slide_down));
                    paymentreceived.setVisibility(View.GONE);
                    pifchips.setVisibility(View.VISIBLE);
                    preceived = Double.valueOf( grandtotal.getText().toString().replace(rs_symbol,""));
                    balanceamount.setText("0");
                    bottombalance.setText("Fully Paid ");
                    Toast.makeText(PaymentActivity.this, preceived+" received", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.hasExtra("invkey")) {
                    progressbardail.show();
                    updatecompletedbill();
                }

                else
                {

                    progressbardail.show();
                    insercompletedbill();
                }

            }
        });
    }


    public void insercompletedbill() {



        RequestQueue rq = Volley.newRequestQueue(PaymentActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/insertCompletedBill.php";
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {




            @Override
            public void onResponse(String response) {
                Toast.makeText(PaymentActivity.this, "okkkk register"   + balanceamount.getText().toString(), Toast.LENGTH_SHORT).show();

                updateoutletlogin();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {





                HashMap<String, String> hm = new HashMap<String, String>();


                String paidamount =  String.format("%.0f",preceived);
                double profit = preceived- Double.valueOf( ppricetotal);


                Date getdate = new Date();
                SimpleDateFormat fulldateformater = new SimpleDateFormat("dd-MM-yyyy" );
                SimpleDateFormat sqldateformat = new SimpleDateFormat("yyyy-MM-dd" );

                try {
                    getdate = fulldateformater.parse(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String sqldate = sqldateformat.format(getdate);



                hm.put("outlet",getoutlet);
                hm.put("Invoice",invoice.replace("Invoice #",""));
                hm.put("date",sqldate);
                hm.put("customer_name",customername.getText().toString());
                hm.put("customer_num",customernum.getText().toString());
                hm.put("productname",productnames);
                hm.put("pprice",pprice);
                hm.put("sprice", sprice);
                hm.put("discount",discount);
                hm.put("discamount",disamount);
                hm.put("tax",taxes);
                hm.put("taxamount",taxamount);
                hm.put("quantity",quantity);
                hm.put("prodprice",prodprice);
                hm.put("tprice", tprice);
                hm.put("ppricebreak",ppricebreak);
                hm.put("ppricetotal", ppricetotal);
                hm.put("mainpricetotal",String.valueOf(  d_productamtotal));
                hm.put("discounttotal", String.valueOf(  d_discounttotal));
                hm.put("taxtotal",String.valueOf(  d_taxtotal));
                hm.put("gpricetotal",String.valueOf(  gtotal));
                hm.put("paidamount",paidamount);
                hm.put("paymethod", spinner.getSelectedItem().toString());
                hm.put("balanceamount",balanceamount.getText().toString().replace(rs_symbol,""));
                hm.put("profit",String.valueOf(profit));




                return hm;
            }

        };

        Toast.makeText(PaymentActivity.this, "sp select "+ spinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
        rq.add(sr);
    }

    public  void  updateoutletlogin()
    {

        RequestQueue rq = Volley.newRequestQueue(PaymentActivity.this );
        String url = "https://strigiform-attorney.000webhostapp.com/billing/updateoutletlogin.php";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PaymentActivity.this, "okkkk", Toast.LENGTH_SHORT).show();

                progressbardail.dismiss();

                final AlertDialog dialogBuilder = new AlertDialog.Builder(PaymentActivity.this).create();
                LayoutInflater inflater = PaymentActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.successfull, null);
                dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBuilder.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                dialogBuilder.setCancelable(false);



                dialogBuilder.setView(dialogView);
                dialogBuilder.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialogBuilder.dismiss();

                        Intent intent = new Intent("finish_activity");
                        sendBroadcast(intent);

                    Intent intent2 = new Intent( PaymentActivity.this,MainActivity.class);
                    startActivity(intent2);
                    finish();



                    }
                }, 2000);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> hm = new HashMap<String, String>();

                hm.put("lb",invoice.replace("Invoice #",""));
                hm.put("o", getoutlet);


                return hm;


            }

        };


        rq.add(sr);




    }

    public void updatecompletedbill() {



        RequestQueue rq = Volley.newRequestQueue(PaymentActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/updateCompletedBill.php";
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {




            @Override
            public void onResponse(String response) {
                Toast.makeText(PaymentActivity.this, "update"+ response, Toast.LENGTH_SHORT).show();

                progressbardail.dismiss();

                final AlertDialog dialogBuilder = new AlertDialog.Builder(PaymentActivity.this).create();
                LayoutInflater inflater = PaymentActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.successfull, null);
                dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBuilder.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                dialogBuilder.setCancelable(false);



                dialogBuilder.setView(dialogView);
                dialogBuilder.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialogBuilder.dismiss();

                        Intent intent = new Intent("finish_activity");
                        sendBroadcast(intent);

                        Intent intent2 = new Intent( PaymentActivity.this,MainActivity.class);
                        startActivity(intent2);
                        finish();



                    }
                }, 2000);






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {





                HashMap<String, String> hm = new HashMap<String, String>();


                String paidamount =  String.format("%.0f",preceived);
                double profit = preceived- Double.valueOf( ppricetotal);


                Date getdate = new Date();
                SimpleDateFormat fulldateformater = new SimpleDateFormat("dd-MM-yyyy" );
                SimpleDateFormat sqldateformat = new SimpleDateFormat("yyyy-MM-dd" );

                try {
                    getdate = fulldateformater.parse(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String sqldate = sqldateformat.format(getdate);



                hm.put("outlet",getoutlet);
                hm.put("Invoice",invoice.replace("Invoice #",""));
                hm.put("date",sqldate);
                hm.put("customer_name",customername.getText().toString());
                hm.put("customer_num",customernum.getText().toString());
                hm.put("productname",productnames);
                hm.put("pprice",pprice);
                hm.put("sprice", sprice);
                hm.put("discount",discount);
                hm.put("discamount",disamount);
                hm.put("tax",taxes);
                hm.put("taxamount",taxamount);
                hm.put("quantity",quantity);
                hm.put("prodprice",prodprice);
                hm.put("tprice", tprice);
                hm.put("ppricebreak",ppricebreak);
                hm.put("ppricetotal", ppricetotal);
                hm.put("mainpricetotal",String.valueOf(  d_productamtotal));
                hm.put("discounttotal", String.valueOf(  d_discounttotal));
                hm.put("taxtotal",String.valueOf(  d_taxtotal));
                hm.put("gpricetotal",String.valueOf(  gtotal));
                hm.put("paidamount",paidamount);
                hm.put("paymethod", spinner.getSelectedItem().toString());
                hm.put("balanceamount",balanceamount.getText().toString().replace(rs_symbol,""));
                hm.put("profit",String.valueOf(profit));




                return hm;
            }

        };

        Toast.makeText(PaymentActivity.this, "sp select "+ spinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
        rq.add(sr);
    }




    public  void progessbarshowmethod()
    {
        progressbardail = new AlertDialog.Builder(PaymentActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
        progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressbardail.setView(dialogView);




    }




}