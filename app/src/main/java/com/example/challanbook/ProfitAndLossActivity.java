package com.example.challanbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.itextpdf.text.DocumentException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProfitAndLossActivity extends AppCompatActivity {

    double purchase,sell,payments,balance,profit,tax;
    TextView tpurchase,tsell,tpayments,tbalance,tprofit;
    TextView fromdate, todate;
    String s_from_date, s_to_date;
    Button getdata;
    RelativeLayout sortlayout;
    EditText completedbillsearcheditext;
    TextView datetextview;
    ImageView menubutton;
    String rs_symbol = "\u20B9";
    SharedPreferences sp;
    String getoutlet;
    private static final int CREATE_FILE = 1;
    AlertDialog progressbardail;


    ArrayList<Modelcompletedbill> completedbilllist = new ArrayList<>();
    public Adaptercompletedbill_adminwise adaptercompletedbill;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_and_loss);

        adaptercompletedbill=new Adaptercompletedbill_adminwise(completedbilllist);
        recyclerView=findViewById(R.id.profitlossrc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProfitAndLossActivity.this));
        recyclerView.setAdapter(adaptercompletedbill);

        progessbarshowmethod();

        sortlayout=findViewById(R.id.sortlayout);
        completedbillsearcheditext=findViewById(R.id.completedbillsearcheditext);

        datetextview=findViewById(R.id.datetextview);

        tpurchase=findViewById(R.id.purchase);
        tsell=findViewById(R.id.sell);
        tpayments=findViewById(R.id.payments);
        tbalance=findViewById(R.id.balance);
        tprofit=findViewById(R.id.profit);

        sp = getSharedPreferences("spfile",0);
        getoutlet =sp.getString("outletspkey", "");




        Date date = new Date();

        SimpleDateFormat sqldateformat = new SimpleDateFormat("yyyy-MM-dd" );
        SimpleDateFormat fulldateformater = new SimpleDateFormat("dd-MM-yyyy" );

        SimpleDateFormat dateformater = new SimpleDateFormat("dd");
        SimpleDateFormat monthformater = new SimpleDateFormat("MM");
        SimpleDateFormat yearformater = new SimpleDateFormat("yyyy");


        String update_date = dateformater.format(date);
        String month = monthformater.format(date);
        String year = yearformater.format(date);


        s_from_date = year+"-"+month+"-01";
        s_to_date = year+"-"+month+"-"+update_date;

        datetextview.setText(s_from_date+ " To "+ s_to_date);




        Date fdateforparse = new Date();
        Date tdateforparse = new Date();

        try {
            fdateforparse = sqldateformat.parse(s_from_date);
            tdateforparse = sqldateformat.parse(s_to_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        String fdate = fulldateformater.format(fdateforparse);
        String tdate = fulldateformater.format(tdateforparse);

        sortlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProfitAndLossActivity.this,R.style.Bottomsheetdialtheme);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_sortfield);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                fromdate=bottomSheetDialog.findViewById(R.id.fromdate);
                todate=bottomSheetDialog.findViewById(R.id.todate);
                getdata=bottomSheetDialog.findViewById(R.id.getdata);





             /*   fromdate.setText(fdate);
                todate.setText(tdate);*/




                fromdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int mYear, mMonth, mDay, mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DATE );


                        DatePickerDialog datePickerDialog = new DatePickerDialog(ProfitAndLossActivity.this,R.style.datepicker,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        SimpleDateFormat dateformater = new SimpleDateFormat("dd-MM-yyyy");

                                        fromdate.setText(dateformater.format( new Date(year -1900, monthOfYear ,dayOfMonth  )));


                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();

                    }
                });


                todate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int mYear, mMonth, mDay, mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(ProfitAndLossActivity.this,R.style.datepicker,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        SimpleDateFormat dateformater = new SimpleDateFormat("dd-MM-yyyy");

                                        todate.setText(dateformater.format( new Date(year -1900, monthOfYear ,dayOfMonth  )));

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();


                    }
                });

                getdata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        completedbilllist.clear();
                        recyclerView.setVisibility(View.INVISIBLE);


                        Date getfromdate = new Date();
                        Date gettodate = new Date();







                        try {
                            getfromdate = fulldateformater.parse(fromdate.getText().toString());
                            gettodate = fulldateformater.parse(todate.getText().toString());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        s_from_date = sqldateformat.format(getfromdate);
                        s_to_date = sqldateformat.format(gettodate);







                        Toast.makeText(ProfitAndLossActivity.this, s_from_date+" ff "+s_to_date, Toast.LENGTH_SHORT).show();

                        datetextview.setText(s_from_date+ " To "+ s_to_date);
                        bottomSheetDialog.dismiss();
                        getcompleted();
                    }
                });


                bottomSheetDialog.show();
            }
        });

        completedbillsearcheditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });

        adaptercompletedbill.setonclicktransfer(new Adaptercompletedbill_adminwise.clicktransfer() {
            @Override
            public void clicktrnfr(int pos, String inv) throws IOException {

                createFile(pos, inv);
            }
        });




        getcompleted();
    }




    public void getcompleted() {

        tpurchase.setText("");
        tsell.setText("");
        tpayments.setText("");
        tbalance.setText("");
        tprofit.setText("");

        purchase=0;
        sell=0;
        payments=0;
        balance=0;
        profit=0;



        RequestQueue rq = Volley.newRequestQueue(ProfitAndLossActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/fetchcompletedbillbydatee.php?sda="+s_from_date+"&eda="+s_to_date+"&o="+getoutlet;

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ProfitAndLossActivity.this, " get.... ", Toast.LENGTH_SHORT).show();

                progressbardail.dismiss();
                showJson(response);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbardail.dismiss();
                Toast.makeText(ProfitAndLossActivity.this, "NOt success => " + error.toString(), Toast.LENGTH_SHORT).show();





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

                Toast.makeText(ProfitAndLossActivity.this,     jsonObject1.getString("profit")+ "dsdw", Toast.LENGTH_SHORT).show();



                purchase+=Double.valueOf (jsonObject1.getString("ppricetotal"));
                sell+=Double.valueOf (jsonObject1.getString("gpricetotal"));
                payments+=Double.valueOf (jsonObject1.getString("paidamount"));
                balance+=Double.valueOf (jsonObject1.getString("balanceamount"));
                profit+=Double.valueOf (jsonObject1.getString("profit"));


                Date fdateforparse = new Date();

                SimpleDateFormat fulldateformater = new SimpleDateFormat("dd-MM-yyyy" );

                SimpleDateFormat sqldateformat = new SimpleDateFormat("yyyy-MM-dd" );


                try {
                    fdateforparse = sqldateformat.parse(  jsonObject1.getString("datee"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String ind_date = fulldateformater.format(fdateforparse);


                completedbilllist.add(new Modelcompletedbill(

                        jsonObject1.getString("outlet"),
                        jsonObject1.getString("Invoice"),
                        ind_date,
                        jsonObject1.getString("customer_name"),
                        jsonObject1.getString("customer_num"),
                        jsonObject1.getString("productname"),
                        jsonObject1.getString("pprice"),
                        jsonObject1.getString("sprice"),
                        jsonObject1.getString("discount"),
                        jsonObject1.getString("tax"),
                        jsonObject1.getString("quantity"),
                        jsonObject1.getString("proprice"),
                        jsonObject1.getString("tprice"),
                        jsonObject1.getString("ppricebreak"),
                        jsonObject1.getString("ppricetotal"),
                        jsonObject1.getString("mainpricetotal"),
                        jsonObject1.getString("discounttotal"),
                        jsonObject1.getString("taxtotal"),
                        jsonObject1.getString("gpricetotal"),
                        jsonObject1.getString("paidamount"),
                        jsonObject1.getString("paymethod"),
                        jsonObject1.getString("balanceamount"),
                        jsonObject1.getString("profit")


                ));


                recyclerView.setAdapter(adaptercompletedbill);
                recyclerView.setVisibility(View.VISIBLE);

                tpurchase.setText(rs_symbol+ String.valueOf(purchase));
                tsell.setText( rs_symbol+new DecimalFormat("##.##").format(sell));
                tpayments.setText(rs_symbol+String.valueOf(payments));
                tbalance.setText(rs_symbol+String.valueOf(balance));
                tprofit.setText(rs_symbol+String.valueOf(profit));

                Toast.makeText(ProfitAndLossActivity.this, "bal "+balance , Toast.LENGTH_SHORT).show();





            }


        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Modelcompletedbill> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Modelcompletedbill s : completedbilllist) {
            //if the existing elements contains the search input
            if (s.getInvoice().toString().toLowerCase().contains(text.toLowerCase()) ||
                    s.getCustomer_name().toString().toLowerCase().contains(text.toLowerCase()) ||
                    s.getCustomer_num().toString().toLowerCase().contains(text.toLowerCase())

            ) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adaptercompletedbill.filterList(filterdNames);
    }

    private void createFile(int pos, String inv) {



        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "Invoice #"+inv+".pdf");
        intent.putExtra("posi", "hello");

        SharedPreferences settings = getSharedPreferences("sppos", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();

        editor.putString("pos", Integer.toString( pos));

        editor.commit();

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        Toast.makeText(ProfitAndLossActivity.this, "creat file "+ pos, Toast.LENGTH_SHORT).show();

        startActivityForResult(intent, CREATE_FILE);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {


            SharedPreferences settings = getSharedPreferences("sppos", MODE_PRIVATE);
            String pos = settings.getString("pos", "");





            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                // Perform operations on the document using its URI.
                Toast.makeText(ProfitAndLossActivity.this, "working "+pos, Toast.LENGTH_SHORT).show();



                /*try {
                    adaptercompletedbill.generatePDF2(uri, Integer.valueOf(pos));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
*/

                try {
                    adaptercompletedbill.generatePDF2(uri, Integer.parseInt(pos));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

            }
        }



    }


    public  void progessbarshowmethod()
    {
        progressbardail = new AlertDialog.Builder(ProfitAndLossActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
        progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressbardail.setCancelable(false);

        progressbardail.setView(dialogView);
        progressbardail.show();




    }

}