package com.example.challanbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.ChangeTransform;
import androidx.transition.TransitionManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private static final int RESULT_LOAD_DOC = 1 ;
    ImageView menubutton;
    AutoCompleteTextView searchtextview;
    Button addproductcart, edclear ;

    TextView nodatatv,billprice,datee,invoice,itemcount, titlecart;

    double ptotal, ppricetotal, gmainprice, gtaxamount, gdisamount,gtotal;
    RelativeLayout relativeLayout,relativeLayout2,backbutton;

    List<ModelAutocompleteTV> modelAutocompleteTVlist = new ArrayList<>();
    AdaptersAutoCompleteTV adaptersAutoCompleteTV;
    ArrayList<Modelcartproducts> cartlist = new ArrayList<>();
    ArrayList<Modelcartproducts> cartproductlist = new ArrayList<>();
    public Adaptercartproduct adaptercartproduct;
    RecyclerView cartrc;
    Modelcartproducts modelcartproducts;
    String rs_symbol = "\u20B9 ";
    EditText cna,cno;
    String productnames,pprice, sprice,discount, disamount,taxes,taxamount,quantity,prodprice, tprice,ppricebreak;
    int size_modelnameList;
    String lastbill,editbillinvoice;
    String custname,custnum;
    AlertDialog progressbardail;
    FileOutputStream output;
    AppBarLayout appBarLayout;
    LinearLayout lin,nodata;
    Intent data;





    private static final int CREATE_FILE = 1;

    int pageHeight = 1120;
    int pagewidth = 792;

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String getoutlet;



    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;


    @RequiresApi(api = Build.VERSION_CODES.M)
    //@SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();




        appBarLayout=findViewById(R.id.appbarLayout);



        menubutton=findViewById(R.id.menubutton);
        searchtextview=findViewById(R.id.searchtextview);

        addproductcart=findViewById(R.id.addproductcart);
        nodata=findViewById(R.id.nodata);
        nodatatv=findViewById(R.id.nodatatv);
        relativeLayout2=findViewById(R.id.relativeLayout2);
        itemcount=findViewById(R.id.itemcount);
        edclear=findViewById(R.id.edclear);

        billprice=findViewById(R.id.billprice);
        relativeLayout=findViewById(R.id.relativeLayout);
        cna=findViewById(R.id.cna);
        cno=findViewById(R.id.cno);
        datee=findViewById(R.id.datee);
        invoice=findViewById(R.id.invoice);
        titlecart=findViewById(R.id.titlecart);
        backbutton=findViewById(R.id.backbutton);





        adaptersAutoCompleteTV = new AdaptersAutoCompleteTV(MainActivity.this, modelAutocompleteTVlist);

        adaptercartproduct=new Adaptercartproduct(cartlist);
        cartrc=findViewById(R.id.cartrc);
        cartrc.setHasFixedSize(true);
        cartrc.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        cartrc.setAdapter(adaptercartproduct);


        sp = getSharedPreferences("spfile",0);
        getoutlet =sp.getString("outletspkey", "");





        data = getIntent();

        if(data.hasExtra("invkey")) {




            editbillinvoice = data.getStringExtra("invkey");

            invoice.setText("Invoice #"+editbillinvoice);
            cna.setText(data.getStringExtra("cname"));
            cno.setText(data.getStringExtra("cnum"));



            getcombiillbyinv();
        }







        Date date = new Date();
        SimpleDateFormat dateformater = new SimpleDateFormat("dd-MM-yyyy");

        datee.setText(dateformater.format(date));


        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();


                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));


        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("Edit_Bill")) {

                    //Toast.makeText(MainActivity.this, "oooooo", Toast.LENGTH_SHORT).show();

                 //   temp();


                }
            }
        };
        registerReceiver(broadcastReceiver2, new IntentFilter("Edit_Bill"));


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {

                    titlecart.setText("My Cart");

                    backbutton.setVisibility(View.VISIBLE);

                    backbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appBarLayout.setExpanded(true, true);
                        }
                    });


                }
                else
                {

                    backbutton.setVisibility(View.GONE);
                    titlecart.setText("ChallanBook");

                }


            }
        });


        searchtextview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                appBarLayout.setExpanded(false,true);



                return false;
            }
        });

        searchtextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


              /*  if(!searchtextview.getText().toString().isEmpty())
                {
                    edclear.setVisibility(View.VISIBLE);

                }

               else if(searchtextview.getText().toString().isEmpty())
                {
                    edclear.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "empty", Toast.LENGTH_SHORT).show();
                }*/

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });



       /* edclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtextview.setText("");
            }
        });*/





        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this,R.style.Bottomsheetdialtheme);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                LinearLayout inventry=bottomSheetDialog.findViewById(R.id.linearLayout3);

                inventry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(MainActivity.this,InventryActivity.class);
                        startActivity(intent);

                    }
                });


                LinearLayout completedbill=bottomSheetDialog.findViewById(R.id.linearLayout);

                completedbill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(MainActivity.this,CompletedbillActivity.class);
                        startActivity(intent);
                    }
                });

                LinearLayout createbill = bottomSheetDialog.findViewById(R.id.linearLayout2);
                createbill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }
                });

                LinearLayout pnl = bottomSheetDialog.findViewById(R.id.linearLayout4);
                pnl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ProfitAndLossActivity.class);
                        startActivity(intent);

                    }
                });

                LinearLayout setting = bottomSheetDialog.findViewById(R.id.linearLayout5);
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       Intent intent = new Intent(MainActivity.this, OutletActivity.class);
                       startActivity(intent);

                    }
                });

                LinearLayout logout = bottomSheetDialog.findViewById(R.id.linearLayout6);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        ed = sp.edit();
                        ed.clear();
                        ed.commit();
                        Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

                bottomSheetDialog.show();

            }
        });


        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* final AlertDialog dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.view_detail, null);

                TextView producttotal=dialogView.findViewById(R.id.producttotal);
                TextView tax=dialogView.findViewById(R.id.tax);
                TextView discount=dialogView.findViewById(R.id.discount);
                TextView total=dialogView.findViewById(R.id.total);


                producttotal.setText(String.valueOf( gmainprice));

                discount.setText(String.valueOf(gdisamount));
                tax.setText(String.valueOf(gtaxamount));
                total.setText(String.valueOf( gtotal));

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();*/





                if(cna.getText().toString().isEmpty())
                {
                    custname = "Undefined";
                }
                else
                {
                    custname = cna.getText().toString();
                }

                if(cno.getText().toString().isEmpty())
                {
                    custnum = "N/A";
                }
                else
                {
                    custnum = cno.getText().toString();
                }




                Intent intent = new Intent(MainActivity.this,PaymentActivity.class);


                if(data.hasExtra("invkey")) {


                    String ikey = data.getStringExtra("invkey");

                    intent.putExtra("invkey", ikey);
                    intent.putExtra("custname",custname);
                    intent.putExtra("custnum",custnum);
                    intent.putExtra("invoice",invoice.getText().toString());
                    intent.putExtra("datee",datee.getText().toString());
                    intent.putExtra("productname",productnames);
                    intent.putExtra("pprice", pprice);
                    intent.putExtra("sprice",sprice);
                    intent.putExtra("discount", discount);
                    intent.putExtra("disamount", disamount);
                    intent.putExtra("tprice", tprice);

                    intent.putExtra("ppricebreak", ppricebreak);
                    intent.putExtra("taxes",taxes);
                    intent.putExtra("taxamount",taxamount);
                    intent.putExtra("quantity",quantity);
                    intent.putExtra("prodprice",prodprice);

                    intent.putExtra("ppricetotal",String.valueOf( ppricetotal));

                    intent.putExtra("gmainprice",String.valueOf( gmainprice));

                    intent.putExtra("gdisamount", String.valueOf(gdisamount));




                    intent.putExtra("gtaxamount", String.valueOf(gtaxamount));
                    intent.putExtra("gtotal",String.valueOf( gtotal));


                    Toast.makeText(MainActivity.this, "editt clcik", Toast.LENGTH_SHORT).show();



                    startActivity(intent);

                }
                else

                {








                    intent.putExtra("custname",custname);
                    intent.putExtra("custnum",custnum);
                    intent.putExtra("invoice",invoice.getText().toString());
                    intent.putExtra("datee",datee.getText().toString());
                    intent.putExtra("productname",productnames);
                    intent.putExtra("pprice", pprice);
                    intent.putExtra("sprice",sprice);
                    intent.putExtra("discount", discount);
                    intent.putExtra("disamount", disamount);
                    intent.putExtra("tprice", tprice);

                    intent.putExtra("ppricebreak", ppricebreak);
                    intent.putExtra("taxes",taxes);
                    intent.putExtra("taxamount",taxamount);
                    intent.putExtra("quantity",quantity);
                    intent.putExtra("prodprice",prodprice);

                    intent.putExtra("ppricetotal",String.valueOf( ppricetotal));

                    intent.putExtra("gmainprice",String.valueOf( gmainprice));

                    intent.putExtra("gdisamount", String.valueOf(gdisamount));



                    intent.putExtra("gtaxamount", String.valueOf(gtaxamount));
                intent.putExtra("gtotal",String.valueOf( gtotal));

                Toast.makeText(MainActivity.this, "not editt clcik", Toast.LENGTH_SHORT).show();



                startActivity(intent);

                }

            }
        });


        addproductcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appBarLayout.setExpanded(false,true);


                if(searchtextview.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please type product name", Toast.LENGTH_SHORT).show();
                    searchtextview.setError("Please type product name");
                    searchtextview.requestFocus();
                }
                else
                {

                    for (ModelAutocompleteTV sp : modelAutocompleteTVlist) {

                        //if the existing elements contains the search input


                        if (sp.getProduct().contentEquals(searchtextview.getText().toString())) {

                            //adding the element to filtered list

                            cartlist.add(new Modelcartproducts(

                                    sp.getProduct(),sp.getPprice(),sp.getSprice()
                                    ,sp.getDiscount(),sp.getDiscountamount(), sp.getTax(),sp.getTaxamount(),"1","0" ,
                                    "0","0" , sp.getTotalamount(),"0","0",
                                    "0","0"


                            ));
                            // searchtextview.setText("");


                            cartrc=findViewById(R.id.cartrc);
                            cartrc.setHasFixedSize(true);
                            cartrc.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            //cartrc.setAdapter(adaptercartproduct);

                            nodata.setVisibility(View.GONE);
                            nodatatv.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            TransitionManager.beginDelayedTransition(relativeLayout2,
                                    new AutoTransition());
                            relativeLayout2.setVisibility(View.VISIBLE);
                            itemcount.setVisibility(View.VISIBLE);
                            cartrc.setAdapter(adaptercartproduct);
                             cartrc.scrollToPosition(cartlist.size()-1);



                            adaptercartproduct.notifyDataSetChanged();
                            // getcount();


                            // billprice.setText(String.valueOf("ptotal"));
                            // adaptercartproduct.notifyDataSetChanged();







                        }
                        else
                        {


                        }
                    }
                }




            }
        });





        adaptercartproduct.setonclicktransfer(new Adaptercartproduct.clicktransfer() {
            @Override
            public void clicktrnfr( String str_productnames,  String str_pprice, String str_sprice,
                                    String str_discount,   String str_disamount,  String str_taxes, String str_taxamount,  String str_quantity, String str_prodprice,
                                    String str_tprice, String str_ppricebreak,

                                    double d_ppricetotal, double gprice, double t, double tt, double ttt, int count) throws IOException {



                productnames= str_productnames;
                pprice =str_pprice;
                sprice=str_sprice;
                discount= str_discount;
                disamount=str_disamount;
                taxes =str_taxes;
                taxamount =str_taxamount;
                quantity=str_quantity;
                prodprice=str_prodprice;
                tprice=str_tprice;
                ppricebreak= str_ppricebreak;

                ppricetotal= d_ppricetotal;
                gmainprice = gprice;
                gdisamount = t;
                gtaxamount = tt;
                gtotal= ttt;





                billprice.setText( rs_symbol+ String.format("%.0f", gtotal));
                itemcount.setText("(Items "+count+")");

/*
                if(size_modelnameList == 0)
                {

                    relativeLayout2.setVisibility(View.GONE);
                    itemcount.setVisibility(View.GONE);
                }*/








            }
        });

        adaptercartproduct.setonclickmodelsizetransfer(new Adaptercartproduct.modelsizetransfer() {
            @Override
            public void sizetrnfr(int sizemodel) throws IOException {


                size_modelnameList= sizemodel;
                Toast.makeText(MainActivity.this, size_modelnameList + " qqq", Toast.LENGTH_SHORT).show();

                if(size_modelnameList == 0)
                {





                    relativeLayout2.setVisibility(View.GONE);

                    relativeLayout.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                    nodatatv.setVisibility(View.VISIBLE);
                    //itemcount.setVisibility(View.GONE);
                    itemcount.setText("(empty)");
                   // cartrc.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_undraw_empty_xct9__2_));
                }




            }
        });










        progessbarshowmethod();
       /* getshopdetail();
        getproducts();
*/




    }

    protected void onResume() {
        super.onResume();

       getshopdetail();
        getproducts();


    }









    public void getshopdetail() {



        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/fetchshopdetail.php?o="+getoutlet;

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {




                showJson2(response2);


                Toast.makeText(MainActivity.this, "dswdwd", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbardail.dismiss();
                Toast.makeText(MainActivity.this, "NOt success => " + error.toString(), Toast.LENGTH_SHORT).show();



            }
        });
        rq.add(sr);

    }

    private void showJson2(String response2) {

        try {



            JSONObject jsonObject = new JSONObject(response2);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);



            for (int i = 0; i < result.length(); i++) {

                JSONObject jsonObject1 = result.getJSONObject(i);

                ed = sp.edit();
                ed.putString("outletnamespkey",jsonObject1.getString("outletname"));
                ed.putString("outletaddspkey",jsonObject1.getString("outletadd"));
                ed.putString("outletspkey",jsonObject1.getString("outlet"));
                ed.putString("outletpassspkey",jsonObject1.getString("outletpass"));
                ed.commit();
                lastbill=(jsonObject1.getString("lastbill"));





                int lb = Integer.valueOf( lastbill)+1;



                if(data.hasExtra("invkey")) {


                    Toast.makeText(this, "i", Toast.LENGTH_SHORT).show();



                }
                else
                {

                    Toast.makeText(this, "lb", Toast.LENGTH_SHORT).show();

                invoice.setText("Invoice #"+lb);

                }

            }


        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }





    public void getproducts() {

        progressbardail.show();
        modelAutocompleteTVlist.clear();

        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/fetchinventry.php?o="+getoutlet;

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Toast.makeText(MainActivity.this, " get.... ", Toast.LENGTH_SHORT).show();
                progressbardail.dismiss();
                showJson(response);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbardail.dismiss();
                Toast.makeText(MainActivity.this, "NOt success => " + error.toString(), Toast.LENGTH_SHORT).show();





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






                modelAutocompleteTVlist.add(new ModelAutocompleteTV(


                        jsonObject1.getString(Config.KEY_product),
                        jsonObject1.getString(Config.KEY_pprice),
                        jsonObject1.getString(Config.KEY_sprice),
                        jsonObject1.getString(Config.KEY_discount),
                        jsonObject1.getString(Config.KEY_discountamount),
                        jsonObject1.getString(Config.KEY_tax),
                        jsonObject1.getString(Config.KEY_taxamount),
                        jsonObject1.getString(Config.KEY_total_amount)
                ));










                adaptersAutoCompleteTV = new AdaptersAutoCompleteTV(MainActivity.this, modelAutocompleteTVlist);



                searchtextview.setAdapter(adaptersAutoCompleteTV);


            }

                if(modelAutocompleteTVlist.isEmpty())
                {
                    final AlertDialog dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
                    LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.empty_box, null);
                    dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBuilder.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;

                    Button intenttoinvntry = dialogView.findViewById(R.id.intenttoinvntry);

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();

                    intenttoinvntry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogBuilder.dismiss();
                            Intent intent= new Intent(MainActivity.this,InventryActivity.class);
                            startActivity(intent);
                        }
                    });


                }
                else
                {
                    Toast.makeText(this, "not empty", Toast.LENGTH_SHORT).show();
                }


        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  void progessbarshowmethod()
    {
        progressbardail = new AlertDialog.Builder(MainActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
        progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressbardail.setCancelable(false);

        progressbardail.setView(dialogView);
        progressbardail.show();



    }


    private void generatePDF2(Uri uri) throws IOException, DocumentException {




        ParcelFileDescriptor pfd = getContentResolver().
                openFileDescriptor(uri, "w");
        FileOutputStream fileOutputStream =
                new FileOutputStream(pfd.getFileDescriptor());






       // output = new FileOutputStream(file);


        Toast.makeText(MainActivity.this, "created doc", Toast.LENGTH_SHORT).show();



        // File docsFolder = new File(mContext.getExternalFilesDir(null),"GFG.pdf");








        /*String pdfname = "GiftItem.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);*/





        Document document = new Document(PageSize.A4);



        Font f1 = new Font();
        f1.setSize(16);
        f1.setStyle(Font.BOLD);
        f1.setColor( BaseColor.BLACK);

        String s = "Californian FB";



        Font f2 = new Font();

        f2.setSize(12);
        f2.setStyle(Font.BOLD);
        f2.setColor( BaseColor.BLACK);

        String FONT1 = "font/arialunicodems.ttf";




        Font fontRupee = FontFactory.getFont(FONT1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);

        // BaseFont baseFont = BaseFont.createFont("font\\robotocondensedbold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        //  Font fn = new Font(baseFont, 18.0f );

        Font fnn = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.NORMAL);



        PdfPTable toptable = new PdfPTable(new float[]{3, 3, 3});
        toptable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        toptable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        toptable.setTotalWidth(PageSize.A4.getWidth());
        toptable.getDefaultCell().setBorder(0);
        toptable.setWidthPercentage(100);

        toptable.getDefaultCell().setBorder(0);
        toptable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);






      /* Drawable rupdrawable = AppCompatResources.getDrawable(mContext, R.drawable.icons8_rupee_48px);
       Bitmap rupbitmap = ((BitmapDrawable)rupdrawable).getBitmap();
       ByteArrayOutputStream rupstream = new ByteArrayOutputStream();
       rupbitmap.compress(Bitmap.CompressFormat.PNG, 100,rupstream);
       //  byte[] bytes = stream.toByteArray();

       Image rupimage = Image.getInstance(rupstream.toByteArray());
       rupimage.scaleAbsolute(10.0f,10.0f);*/













        //  toptable.addCell( new PdfPCell(image));
        toptable.addCell("");
        toptable.addCell("");
        toptable.addCell(new Paragraph( "Invoice #",f1));

        toptable.addCell("");
        toptable.addCell("");
        toptable.addCell(new Paragraph("hhi"));







        PdfPTable table6 = new PdfPTable(new float[]{1});
        table6.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT );
        table6.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table6.getDefaultCell().setFixedHeight(20);
        table6.getDefaultCell().setBorder(0);



        table6.setTotalWidth(PageSize.A4.getWidth());
        table6.setWidthPercentage(100);

        table6.setTotalWidth(PageSize.A4.getWidth());
        table6.setWidthPercentage(100);

        table6.addCell(new Paragraph("TERMS AND CONDITIONS:", f2));

        table6.addCell("1. Goods once sold will not be taken back");
        table6.addCell("2. Interest @ 18% p.a will be charged if the payment is not made with in the stipulated time");
        table6.addCell("3. Subject to 'Delhi' Jurisdiction only");






        PdfWriter.getInstance(document, fileOutputStream);




        document.open();
     /*   Font f = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.NORMAL);
        f.setColor( 60, 205, 204);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.NORMAL, BaseColor.BLACK);
          document.add(new Paragraph("Invoice #"+ model.getInvoice()+"  \n", f));
        document.add(new Paragraph(model.getDate()+ " \n\n", g));*/


        LineSeparator lineSeparator = new LineSeparator();
        //  lineSeparator.setOffset(14);
        lineSeparator.setLineWidth(3);



      /* PdfPCell cellp = new PdfPCell();
        Paragraph p1 = new Paragraph("My fantastic data");
        p1.setSpacingAfter(20);
        cellp.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellp.addElement( new PdfPCell( p1));
        document.add(cellp);*/


        document.add(toptable);


        lineSeparator.setLineColor(new BaseColor(239,239,239));
        document.add(  lineSeparator);

        document.add(new Paragraph(  "\n"));






        //lineSeparator.setLineColor(new BaseColor(102   ,102,102));




        //     document.add(new Paragraph(  "\n"));



        // lineSeparator.setLineColor(new BaseColor(214,215,215));
        document.add(  lineSeparator);








        //  document.add(new Chunk( lineSeparator));

        //   document.add(table3);





        document.add(new Paragraph(  "\n"));




        lineSeparator.setLineColor(new BaseColor(239,239,239));
        document.add(  lineSeparator);

        document.add(new Paragraph(  "\n"));




        document.add(new Paragraph(  "\n"));
        lineSeparator.setLineColor(new BaseColor(239,239,239));
        document.add(  lineSeparator);

        document.add(new Paragraph(  "\n"));

        document.add(table6);



        document.close();

        Toast.makeText(MainActivity.this, "pdf created", Toast.LENGTH_SHORT).show();

        //previewPdf();




    }


    private void    createFile() {



        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "challan.pdf");

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
       // intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, CREATE_FILE);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {

            Toast.makeText(MainActivity.this, "working", Toast.LENGTH_SHORT).show();

            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                // Perform operations on the document using its URI.


                try {
                    generatePDF2(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


            }
        }



    }

    public void getcombiillbyinv() {

      //  progressbardail.show();
        modelAutocompleteTVlist.clear();

        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/fetchcompletedbillbyinvoice.php?o="+getoutlet+"&i="+editbillinvoice;

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(MainActivity.this, " edit", Toast.LENGTH_SHORT).show();
                progressbardail.dismiss();
                showJson3(response);

                adaptercartproduct.notifyDataSetChanged();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbardail.dismiss();
                Toast.makeText(MainActivity.this, "NOt success => " + error.toString(), Toast.LENGTH_SHORT).show();





            }
        });
        rq.add(sr);

    }

    private void showJson3(String response) {


        try {



            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);



            for (int i = 0; i < result.length(); i++) {

                JSONObject jsonObject1 = result.getJSONObject(i);






                ;


                String pro[] = jsonObject1.getString("productname").split(",");


                String pri[] =    jsonObject1.getString("pprice").split(",");
                String spri[] =       jsonObject1.getString("sprice").split(",");
                String diss[] =        jsonObject1.getString("discount").split(",");
                String dissam[] = jsonObject1.getString("discamount").split(",");

                String taxxx[] =    jsonObject1.getString("tax").split(",");
                String taxxxam[] =   jsonObject1.getString("taxamount").split(",");
                String qnt[] = jsonObject1.getString("quantity").split(",");
                String prprice[] =  jsonObject1.getString("proprice").split(",");
                String totprice[] =  jsonObject1.getString("tprice").split(",");


                Toast.makeText(this, "ppp "+ jsonObject1.getString("discount"), Toast.LENGTH_SHORT).show();


                adaptercartproduct=new Adaptercartproduct(cartlist);


                for (int j = 0; j < pro.length; j++) {


                    String prdct = pro[j];

                    String edpri = pri[j];
                    String edspri = spri[j];
                    String eddis = diss[j];
                    String eddisam = dissam[j];
                    String edtax = taxxx[j];
                    String edtaxam = taxxxam[j];
                    String edqnt= qnt[j];
                    String edproprice = prprice[j];
                    String edtpr = totprice[j];





                    cartlist.add(new Modelcartproducts(

                            prdct,edpri,edspri
                            ,eddis ,eddisam, edtax,edtaxam,edqnt,"0" ,
                            "0","0" ,edproprice,"0","0",
                            "0","0"


                    ));


                }


                cartrc=findViewById(R.id.cartrc);
                cartrc.setHasFixedSize(true);
                cartrc.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                //cartrc.setAdapter(adaptercartproduct);

                nodata.setVisibility(View.GONE);
                nodatatv.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                TransitionManager.beginDelayedTransition(relativeLayout2,
                        new AutoTransition());
                relativeLayout2.setVisibility(View.VISIBLE);
                itemcount.setVisibility(View.VISIBLE);
                cartrc.setAdapter(adaptercartproduct);
                cartrc.scrollToPosition(cartlist.size()-1);
                adaptercartproduct.notifyDataSetChanged();

                adaptercartproduct.setonclicktransfer(new Adaptercartproduct.clicktransfer() {
                    @Override
                    public void clicktrnfr( String str_productnames,  String str_pprice, String str_sprice,
                                            String str_discount,   String str_disamount,  String str_taxes, String str_taxamount,  String str_quantity, String str_prodprice,
                                            String str_tprice, String str_ppricebreak,

                                            double d_ppricetotal, double gprice, double t, double tt, double ttt, int count) throws IOException {



                        productnames= str_productnames;
                        pprice =str_pprice;
                        sprice=str_sprice;
                        discount= str_discount;
                        disamount=str_disamount;
                        taxes =str_taxes;
                        taxamount =str_taxamount;
                        quantity=str_quantity;
                        prodprice=str_prodprice;
                        tprice=str_tprice;
                        ppricebreak= str_ppricebreak;

                        ppricetotal= d_ppricetotal;
                        gmainprice = gprice;
                        gdisamount = t;
                        gtaxamount = tt;
                        gtotal= ttt;






                        billprice.setText( rs_symbol+ String.format("%.0f", gtotal));
                        itemcount.setText("(Items "+count+")");

/*
                if(size_modelnameList == 0)
                {

                    relativeLayout2.setVisibility(View.GONE);
                    itemcount.setVisibility(View.GONE);
                }*/








                    }
                });



            }


        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }





}