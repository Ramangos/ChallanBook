package com.example.challanbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
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
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CompletedbillActivity extends AppCompatActivity  {


    ArrayList<Modelcompletedbill> completedbilllist = new ArrayList<>();
    public Adaptercompletedbill adaptercompletedbill;
    RecyclerView recyclerView;
    TextView fromdate, todate;

    String s_from_date, s_to_date;
    Button getdata;
    RelativeLayout sortlayout;
    EditText completedbillsearcheditext;
    TextView datetextview;
    ImageView menubutton;
    SharedPreferences sp;
    String getoutlet;
    AlertDialog progressbardail;

    private static final int CREATE_FILE = 1;
    private static final int PICK_PDF_FILE = 2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedbill);
        getSupportActionBar().hide();

        progessbarshowmethod();

        menubutton=findViewById(R.id.menubutton);
        menubutton.setVisibility(View.GONE);

        adaptercompletedbill=new Adaptercompletedbill(completedbilllist);
        recyclerView=findViewById(R.id.completedbillrc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CompletedbillActivity.this));
        recyclerView.setAdapter(adaptercompletedbill);



        sortlayout=findViewById(R.id.sortlayout);
        completedbillsearcheditext=findViewById(R.id.completedbillsearcheditext);

        datetextview=findViewById(R.id.datetextview);

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



       /* s_from_date =sqldateformat.format(date) ;
        s_to_date = sqldateformat.format(date) ;*/


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

        datetextview.setText(fdate+ " To "+ tdate);


        sortlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CompletedbillActivity.this,R.style.Bottomsheetdialtheme);
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


                        DatePickerDialog datePickerDialog = new DatePickerDialog(CompletedbillActivity.this,R.style.datepicker,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        SimpleDateFormat dateformater = new SimpleDateFormat("dd-MM-yyyy");

                                        fromdate.setText (dateformater.format( new Date(year -1900, monthOfYear ,dayOfMonth  )));




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


                        DatePickerDialog datePickerDialog = new DatePickerDialog(CompletedbillActivity.this,R.style.datepicker,
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








                        Toast.makeText(CompletedbillActivity.this, s_from_date+" ff "+s_to_date, Toast.LENGTH_SHORT).show();

                        datetextview.setText(fromdate.getText()+ " To "+ todate.getText());
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






        getcompleted();


        adaptercompletedbill.setonclicktransfer(new Adaptercompletedbill.clicktransfer() {
            @Override
            public void clicktrnfr(int pos, String inv) throws IOException {

                createFile(pos, inv);

            }
        });




    }





    public void getcompleted() {



        RequestQueue rq = Volley.newRequestQueue(CompletedbillActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/fetchcompletedbillbydatee.php?sda="+s_from_date+"&eda="+s_to_date+"&o="+getoutlet;

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(CompletedbillActivity.this, " get.... "+s_from_date, Toast.LENGTH_SHORT).show();

                progressbardail.dismiss();

                showJson(response);

                Toast.makeText(CompletedbillActivity.this, s_from_date+ " this "+s_to_date, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressbardail.dismiss();
                Toast.makeText(CompletedbillActivity.this, "NOt success => " + error.toString(), Toast.LENGTH_SHORT).show();





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
                recyclerView.setAnimation(AnimationUtils.loadAnimation(CompletedbillActivity.this,R.anim.slide_up));


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


    private void generatePDF2(Uri uri) throws IOException, DocumentException {




        ParcelFileDescriptor pfd = getContentResolver().
                openFileDescriptor(uri, "w");
        FileOutputStream fileOutputStream =
                new FileOutputStream(pfd.getFileDescriptor());






        // output = new FileOutputStream(file);


        Toast.makeText(CompletedbillActivity.this, "created doc", Toast.LENGTH_SHORT).show();



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

        Toast.makeText(CompletedbillActivity.this, "pdf created", Toast.LENGTH_SHORT).show();

        //previewPdf();




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

        Toast.makeText(CompletedbillActivity.this, "creat file "+ pos, Toast.LENGTH_SHORT).show();

        startActivityForResult(intent, CREATE_FILE);



    }

    private void openFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, PICK_PDF_FILE);
    }

    public  void viewpdf(Uri uri)  {




        Intent sharingIntent = new Intent(Intent.ACTION_VIEW);



        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.setDataAndType(uri, "application/pdf");
        startActivity(Intent.createChooser(sharingIntent, "Share using"));



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==PICK_PDF_FILE && resultCode == Activity.RESULT_OK)
            {
               if(data!=null)
               {

                   Toast.makeText(CompletedbillActivity.this, "openFile", Toast.LENGTH_SHORT).show();
                   Uri uri = data.getData();


               }
           }

        if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {


            SharedPreferences settings = getSharedPreferences("sppos", MODE_PRIVATE);
            String pos = settings.getString("pos", "");





            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                // Perform operations on the document using its URI.
                Toast.makeText(CompletedbillActivity.this, "working "+ data, Toast.LENGTH_SHORT).show();


                SharedPreferences set = getSharedPreferences("suri", MODE_PRIVATE);
                SharedPreferences.Editor edi = set.edit();
                edi.clear();



                edi.putString("ssuri", uri.toString());

                edi.commit();

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
        progressbardail = new AlertDialog.Builder(CompletedbillActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
        progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressbardail.setCancelable(false);

        progressbardail.setView(dialogView);
        progressbardail.show();




    }



}