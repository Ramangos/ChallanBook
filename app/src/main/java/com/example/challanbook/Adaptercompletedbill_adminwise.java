package com.example.challanbook;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.material.chip.Chip;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Adaptercompletedbill_adminwise extends RecyclerView.Adapter<Adaptercompletedbill_adminwise.ViewHolder> {


    List<Modelcompletedbill> modellist;
    Modelcompletedbill model;
    String rs_symbol = "\u20B9";
    Adapterproductdetail adapterproductdetail;
    ArrayList<Modelproductdetail> modelproductdetaillist = new ArrayList<>();
    double gtotal,preceived;
    File pdfFile;

    Context mContext;
    Modelcompletedbillsummary nname,qty, pprice,ddis,ttax,amount;
    int itemcount;
    ArrayList<Modelcompletedbillsummary> completedbilllsummaryist;
    Adaptercompletedbillsummary adaptercompletedbillsummary;
    SharedPreferences sp;
    String getoutletname,getoutletadd, getoutlet;

    private OnItemClickListener omOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TextView b , View view, Modelcompletedbill obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.omOnItemClickListener = mItemClickListener;
    }

    public static ClickListener clickListener;

    private clicktransfer clicktransfer;

    public interface clicktransfer {
        void clicktrnfr( int position, String inv )

                throws IOException;
    }
    public void setonclicktransfer(final clicktransfer clicktransfer) {
        this.clicktransfer = clicktransfer;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {




        public TextView  invoice, datee, customername,customernum,productname,productprice,balancestatus;
        TextView edit;
        RelativeLayout layout_balancestatus,relativeLayout4;
        LinearLayout linearlayout;
        RelativeLayout cardView;
        ImageView arrowbutton;
        RecyclerView rcv;
        ImageView menubutton;


        public ViewHolder(View itemView)  {
            super(itemView);

            itemView.setOnClickListener(this);

            invoice =  itemView.findViewById(R.id.invoice);
            datee =  itemView.findViewById(R.id.datee);
            customername =  itemView.findViewById(R.id.customername);
            customernum =  itemView.findViewById(R.id.customernum);
            productname =  itemView.findViewById(R.id.productname);
            productprice =  itemView.findViewById(R.id.productprice);
            layout_balancestatus=itemView.findViewById(R.id.layout_balancestatus);
            balancestatus= itemView.findViewById(R.id.balancestatus);
            relativeLayout4= itemView.findViewById(R.id.relativeLayout4);
            linearlayout =itemView.findViewById(R.id.linearlayout);
            arrowbutton=itemView.findViewById(R.id.arrowbutton);
            cardView= itemView.findViewById(R.id.cardView);

            rcv= itemView.findViewById(R.id.productdetailrc);
            menubutton=itemView.findViewById(R.id.menubutton_moreinfo);


            sp = mContext.getSharedPreferences("spfile",0);
            getoutletname =sp.getString("outletnamespkey","");
            getoutletadd =sp.getString("outletaddspkey","");
            getoutlet =sp.getString("outletspkey", "");





        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getBindingAdapterPosition(),v);


        }

        @Override
        public boolean onLongClick(View v) {

            clickListener.onItemLongClick(getBindingAdapterPosition(),v);
            return false;
        }


        public void setOnItemClickListener(ClickListener clickListener) {
          Adaptercompletedbill_adminwise.clickListener = clickListener;
        }
    }


    public Adaptercompletedbill_adminwise(List<Modelcompletedbill> dataList) {



        this.modellist = dataList;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.completedbill_row,viewGroup,false);


        mContext = viewGroup.getContext();

        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position) {
        model = modellist.get(position);

        holder.setIsRecyclable(false);

        final Modelcompletedbill modelcompletedbill = modellist.get(position);



        //holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(holder.linearLayout.getContext(),R.anim.fade_transition_animation));

        holder.invoice.setText("Invoice #"+model.getInvoice());
        holder.datee.setText(model.getDate());
        holder.customername.setText(model.getCustomer_name());
        holder.customernum.setText(model.getCustomer_num());
       // holder.productname.setText(model.getProductname());
        holder.productprice.setText(rs_symbol+String.format("%.0f", Double.valueOf( model.getGpricetotal())));
        holder.arrowbutton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);


        if(model.getBalanceamount().toString().contentEquals("0"))
        {
            holder.layout_balancestatus.setBackgroundColor(ContextCompat.getColor(holder.productname.getContext(), R.color.green));
            holder.balancestatus.setText("Fully Paid");

        }
        else
        {
            holder.layout_balancestatus.setBackgroundColor(ContextCompat.getColor(holder.productname.getContext(), R.color.red));
            holder.balancestatus.setText("Balance " + rs_symbol+ model.getBalanceamount());
        }

        adapterproductdetail=new Adapterproductdetail(modelproductdetaillist);
        holder.rcv.setHasFixedSize(true);
        holder.rcv.setLayoutManager(new LinearLayoutManager(holder.productname.getContext()));
        holder.rcv.setAdapter(adapterproductdetail);


        holder.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                model = modellist.get(position);

                detailedbilladminwise(position);

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });


        holder.relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modelproductdetaillist.clear();

                if(holder.linearlayout.getVisibility() == View.GONE)
                {
                    model = modellist.get(position);

                    String pro[] = model.getProductname().split(",");

                    String pri[] = model.getTprice().split(",");

                    String quan[] = model.getQuantity().split(",");


                        for (int j = 0; j < pro.length; j++) {


                            String prdct = pro[j];
                            String price = pri[j];
                            String quantity = quan[j];



                            modelproductdetaillist.add(new Modelproductdetail(prdct,prdct,prdct,prdct,quantity,price));


                    }
                    holder. rcv.setAdapter(adapterproductdetail);


                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.linearlayout.setVisibility(View.VISIBLE);
                    holder.arrowbutton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else
                {
                    holder.linearlayout.setVisibility(View.INVISIBLE);
                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.linearlayout.setVisibility(View.GONE);
                    holder.arrowbutton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }

            }
        });

        holder.menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model = modellist.get(position);

                //creating a popup menu
                PopupMenu popup = new PopupMenu(holder.menubutton.getContext(), holder.menubutton);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu);

                if(model.getBalanceamount().toString().contentEquals("0"))
                {
                    popup.getMenu().findItem(R.id.paybalance).setVisible(false);
                }


                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item1:


                                model = modellist.get(position);

                                detailedbilladminwise(position);

                                break;



                            case R.id.item2:


                                if(clicktransfer!=null) {


                                    Toast.makeText(mContext, "pos "+position, Toast.LENGTH_SHORT).show();

                                    try {
                                        clicktransfer.clicktrnfr(position, model.getInvoice().toString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }


                                break;


                            case R.id.paybalance:

                                alertdialbalpay(position);

                                break;

                            case R.id.call:

                                if(model.getCustomer_num().contentEquals("N/A"))
                                {
                                    Toast.makeText(mContext, "Phone number not available", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {


                                    String phone = "+91"+model.getCustomer_num();
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                                    mContext.startActivity(intent);

                                }


                                break;


                        }
                        
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });


    }




    @Override
    public int getItemCount()
    {
        return modellist.size();
    }

    public void filterList(ArrayList<Modelcompletedbill> filterdNames) {
        this.modellist = filterdNames;
        notifyDataSetChanged();
    }


    public void alertdialbalpay( int position)
    {


        final BottomSheetDialog bsheetdialogBuilder = new BottomSheetDialog(mContext,R.style.Bottomsheetdialtheme);
        bsheetdialogBuilder.setContentView(R.layout.dialogue_pay_balance);
        bsheetdialogBuilder.setCanceledOnTouchOutside(false);
        bsheetdialogBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        bsheetdialogBuilder.show();



        model = modellist.get(position);

        TextView custname=bsheetdialogBuilder.findViewById(R.id.custname);
        TextView custnum=bsheetdialogBuilder.findViewById(R.id.custnum);
        TextView invoice=bsheetdialogBuilder.findViewById(R.id.invoice);
        TextView datee=bsheetdialogBuilder.findViewById(R.id.datee);
        TextView balanceamount=bsheetdialogBuilder.findViewById(R.id.balanceamount);
        EditText paymentreceived=bsheetdialogBuilder.findViewById(R.id.paymentreceived);
        Button balancepay=bsheetdialogBuilder.findViewById(R.id.pymt);
        Chip pifchips=bsheetdialogBuilder.findViewById(R.id.pifchips);

        custname.setText(model.getCustomer_name());
        custnum.setText(model.getCustomer_num());
        invoice.setText("Invoice #"+model.getInvoice());
        datee.setText(model.getDate());
        balanceamount.setText( "0");



        gtotal =Double.valueOf( model.getBalanceamount());
        preceived = Double.valueOf( model.getBalanceamount());


        pifchips.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_in_right));

        pifchips.setText("Pay full balance " + model.getBalanceamount());

        pifchips.setOnCloseIconClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                pifchips.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_out_right));



                pifchips.setVisibility(View.GONE);



                paymentreceived.setVisibility(View.VISIBLE);

                paymentreceived.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_up));
                paymentreceived.setText("");




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


                if  (Double.valueOf( balanceamount.getText().toString().replace(rs_symbol,"") ) <= 0 )
                {
                    paymentreceived.setText("");
                    pifchips.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_in_right));
                    paymentreceived.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_down));
                    paymentreceived.setVisibility(View.GONE);
                    pifchips.setVisibility(View.VISIBLE);
                    preceived = Double.valueOf( model.getBalanceamount());
                    balanceamount.setText("0");

                    Toast.makeText(mContext, preceived+" received", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        balancepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insertbalanceamount();

                model = modellist.get(position);

                double balrcvd = Double.valueOf(model.getPaidamount())+preceived;
                double revisedprofit =  balrcvd - Double.valueOf(model.getPpricetotal());


                Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();





                RequestQueue rq = Volley.newRequestQueue(v.getContext()
                );
                String url = "https://strigiform-attorney.000webhostapp.com/billing/insertbalanceamount.php";
                StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(v.getContext(), "okkkk", Toast.LENGTH_SHORT).show();


                       // bsheetdialogBuilder.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_down));



                        bsheetdialogBuilder.dismiss();


                        Intent intent = new Intent(mContext,ProfitAndLossActivity.class);
                        mContext.startActivity(intent);

                        ((Activity)mContext).finish();




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(v.getContext(), "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> hm = new HashMap<String, String>();

                        hm.put("p", String.valueOf(balrcvd));
                        hm.put("b", balanceamount.getText().toString().replace(rs_symbol,""));
                        hm.put("prof", String.valueOf(revisedprofit));
                        hm.put("i", invoice.getText().toString().replace("Invoice #", ""));
                        hm.put("o", getoutlet);


                        return hm;


                    }

                };


                rq.add(sr);





            }
        });

    }



    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }


    public  void detailedbilladminwise(int position)
    {

        model = modellist.get(position);

        final AlertDialog dialogBuilder = new AlertDialog.Builder(mContext,R.style.FullscreenDialog).create();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.completed_bill_summary_adminwise, null);








        dialogBuilder.setView(dialogView);
        dialogBuilder.show();


        TextView Shopname=dialogBuilder.findViewById(R.id.Shopname);
        TextView Shopaddress=dialogBuilder.findViewById(R.id.Shopaddress);
        TextView Shopphone=dialogBuilder.findViewById(R.id.Shopphone);


        TextView invoice=dialogBuilder.findViewById(R.id.invoicenum);
        TextView datee=dialogBuilder.findViewById(R.id.billdate);
        TextView customername=dialogBuilder.findViewById(R.id.customername);
        TextView customernum=dialogBuilder.findViewById(R.id.customernum);
        TextView producttotal=dialogBuilder.findViewById(R.id.productamtotal);
        TextView discountotal=dialogBuilder.findViewById(R.id.discounttotal);
        TextView taxttotal=dialogBuilder.findViewById(R.id.taxtotal);
        TextView grandtotal=dialogBuilder.findViewById(R.id.grandtotal);

        TextView purchasetotal=dialogBuilder.findViewById(R.id.purchasetotal);
        TextView selltotal=dialogBuilder.findViewById(R.id.selltotal);
        TextView profit=dialogBuilder.findViewById(R.id.profit);
        TextView paidamount=dialogBuilder.findViewById(R.id.paidamount);
        TextView balanceamount=dialogBuilder.findViewById(R.id.balanceamount);


        TextView balancestatus=dialogBuilder.findViewById(R.id.balancestatus);
        RelativeLayout balancestatus_layout=dialogBuilder.findViewById(R.id.balancestatus_layout);


        RelativeLayout calllayout=dialogBuilder.findViewById(R.id.calllayout);

        calllayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(model.getCustomer_num().contentEquals("N/A"))
                {
                    Toast.makeText(mContext, "Phone number not available", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    String phone = "+91"+model.getCustomer_num();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    mContext.startActivity(intent);

                }
            }
        });

        ImageView menubutton=dialogBuilder.findViewById(R.id.menubutton);


        Shopname.setText(getoutletname);
        Shopaddress.setText(getoutletadd);
        Shopphone.setText(getoutlet);


        invoice.setText("Invoice #"+ model.getInvoice());
        datee.setText(model.getDate());
        customername.setText(model.getCustomer_name());
        customernum.setText(model.getCustomer_num());
        producttotal.setText(rs_symbol+ model.getMainpricetotal());
        discountotal.setText(rs_symbol+String.format("%.2f",Double.valueOf( model.getDiscounttotal())));
        taxttotal.setText(rs_symbol+ String.format("%.2f",Double.valueOf(model.getTaxtotal())));
        grandtotal.setText(rs_symbol+String.format("%.0f",Double.valueOf( model.getGpricetotal())));

        purchasetotal.setText(rs_symbol+ String.format("%.0f",Double.valueOf( model.getPpricetotal())));
        selltotal.setText(rs_symbol+String.format("%.0f",Double.valueOf( model.getGpricetotal())));
        profit.setText(rs_symbol+String.format("%.0f",Double.valueOf( model.getProfit())));
        paidamount.setText(rs_symbol+String.format("%.0f",Double.valueOf( model.getPaidamount())));
        balanceamount.setText(rs_symbol+ String.format("%.0f",Double.valueOf(model.getBalanceamount())));


        if(model.getBalanceamount().toString().contentEquals("0"))
        {
            balancestatus_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green2));
            balancestatus.setTextColor(ContextCompat.getColor(mContext, R.color.green1));
            balancestatus.setText("Fully Paid");
        }
        else
        {
            balancestatus_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red2));
            balancestatus.setTextColor(ContextCompat.getColor(mContext, R.color.red1));
            balancestatus.setText("Balance " + rs_symbol+ model.getBalanceamount());

            balancestatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialbalpay(position);
                }
            });

        }




        final RecyclerView recyclerView,recyclerViewpruchase;


        ArrayList<Modelcompletedbillsummary> completedbilllsummaryist_purchase = new ArrayList<>();
        Adaptercompletedbillsummary_pruchase_adminwise
                adaptercompletedbillsummary_pruchase_adminwise;

        adaptercompletedbillsummary_pruchase_adminwise=new Adaptercompletedbillsummary_pruchase_adminwise(completedbilllsummaryist_purchase);
        recyclerViewpruchase=dialogBuilder.findViewById(R.id.adminwise_purchase_rc);
        recyclerViewpruchase.setNestedScrollingEnabled(false);
        recyclerViewpruchase.setHasFixedSize(true);
        recyclerViewpruchase.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewpruchase.setAdapter(adaptercompletedbillsummary_pruchase_adminwise);
        // recyclerViewpruchase.setNestedScrollingEnabled(false);

        completedbilllsummaryist = new ArrayList<>();
        adaptercompletedbillsummary=new Adaptercompletedbillsummary(completedbilllsummaryist);
        recyclerView=dialogBuilder.findViewById(R.id.completedbillsummary_rc);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adaptercompletedbillsummary);
        //recyclerView.setNestedScrollingEnabled(false);





        String pro[] = model.getProductname().split(",");

        String ppri[] = model.getPprice().split(",");

        String ppribreak[] = model.getPpricebreak().split(",");

        String spri[] = model.getSprice().split(",");


        String quan[] = model.getQuantity().split(",");

        String dis[] = model.getDiscount().split(",");

        String tx[] = model.getTax().split(",");


        String pri[] = model.getTprice().split(",");

        completedbilllsummaryist.clear();

        completedbilllsummaryist_purchase.clear();


        for (int j = 0; j < pro.length; j++) {


            String prdct = pro[j];

            String pprice = ppri[j];

            String ppricebreak = ppribreak[j];

            String sprice = spri[j];

            String quantity = quan[j];

            String discount = dis[j];
            String tax = tx[j];

            String price = pri[j];




            completedbilllsummaryist_purchase.add(new Modelcompletedbillsummary(prdct,pprice, ppricebreak, sprice,
                    discount,tax,quantity,price));

            completedbilllsummaryist.add(new Modelcompletedbillsummary(prdct,pprice, ppricebreak, sprice,
                    discount,tax,quantity,price));





        }

        recyclerView.setAdapter(adaptercompletedbillsummary);
        recyclerViewpruchase.setAdapter(adaptercompletedbillsummary_pruchase_adminwise);



        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model = modellist.get(position);

                //creating a popup menu.,,
                PopupMenu popup = new PopupMenu(mContext, menubutton);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu2);


                if(model.getBalanceamount().toString().contentEquals("0"))
                {
                    popup.getMenu().findItem(R.id.paybalance).setVisible(false);
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.printbill:

                                if(clicktransfer!=null) {


                                    Toast.makeText(mContext, "pos "+position, Toast.LENGTH_SHORT).show();

                                    try {
                                        clicktransfer.clicktrnfr(position, model.getInvoice().toString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }

                                break;


                            case R.id.paybalance:


                                alertdialbalpay(position);

                                break;


                        }

                        return false;
                    }
                });
                popup.show();
            }
        });


    }




    public void generatePDF2( Uri uri, int position  ) throws IOException, DocumentException {

        model = modellist.get(position);

        ParcelFileDescriptor pfd =mContext.getContentResolver().
                openFileDescriptor(uri, "w");
        FileOutputStream fileOutputStream =
                new FileOutputStream(pfd.getFileDescriptor());


        itemcount=0;

        completedbilllsummaryist = new ArrayList<>();


        String pro[] = model.getProductname().split(",");

        String ppri[] = model.getPprice().split(",");

        String ppribreak[] = model.getPpricebreak().split(",");

        String spri[] = model.getSprice().split(",");


        String quan[] = model.getQuantity().split(",");

        String dis[] = model.getDiscount().split(",");

        String tx[] = model.getTax().split(",");


        String pri[] = model.getTprice().split(",");

        completedbilllsummaryist.clear();



        for (int j = 0; j < pro.length; j++) {


            String prdct = pro[j];

            String pprice = ppri[j];

            String ppricebreak = ppribreak[j];

            String sprice = spri[j];

            String quantity = quan[j];

            String discount = dis[j];
            String tax = tx[j];

            String price = pri[j];


            completedbilllsummaryist.add(new Modelcompletedbillsummary(prdct,pprice, ppricebreak, sprice,
                    discount,tax,quantity,price));

        }










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

        Drawable drawable = AppCompatResources.getDrawable(mContext, R.drawable.cb3);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        //  byte[] bytes = stream.toByteArray();

        Image image = Image.getInstance(stream.toByteArray());
        image.scaleAbsolute(180.0f,40.0f);


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
        toptable.addCell(new Paragraph( "Invoice #"+ model.getInvoice(),f1));

        toptable.addCell("");
        toptable.addCell("");
        toptable.addCell(new Paragraph( model.getDate()));





        PdfPTable toptable2 = new PdfPTable(new float[]{3, 3, 3});
        toptable2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        toptable2.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        toptable2.setTotalWidth(PageSize.A4.getWidth());
        toptable2.getDefaultCell().setBorder(0);
        toptable2.setWidthPercentage(100);
        toptable2.setSpacingBefore(20);


        toptable2.addCell("" );
        toptable2.addCell("");
        toptable2.addCell("");





        PdfPTable table1 = new PdfPTable(new float[]{3, 3, 3, 3});
        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        table1.setTotalWidth(PageSize.A4.getWidth());
        table1.getDefaultCell().setBorder(0);
        table1.setWidthPercentage(100);
        table1.setSpacingAfter(10);

        table1.getDefaultCell().setBorder(0);


        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell( new Paragraph( rs_symbol+ "Bill to",f1));
        table1.addCell("");
        table1.addCell("");
        table1.addCell(new Paragraph( "From",f1));



        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell(new Paragraph( model.getCustomer_name(), f2));
        table1.addCell("");
        table1.addCell("");
        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell(new Paragraph( getoutletname, f2));

        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell( model.getCustomer_num());
        table1.addCell("");
        table1.addCell("");
        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table1.addCell(getoutletadd);

        table1.addCell( "");
        table1.addCell("");
        table1.addCell("");
        table1.getDefaultCell().setFixedHeight(20);

        table1.addCell(getoutlet);









        PdfPTable table2 = new PdfPTable(new float[]{2, 2, 2, 2,2,2});
        table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.getDefaultCell().setFixedHeight(30);
        table2.setTotalWidth(PageSize.A4.getWidth());
        table2.setWidthPercentage(100);
        table2.getDefaultCell().setBorder(0);
        //  table2.getDefaultCell().setBackgroundColor(new BaseColor(239,239,239));







        table2.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(new Paragraph( "ITEMS",f2));
        table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table2.addCell(new Paragraph( "QTY",f2  ));
        table2.addCell(new Paragraph( "RATE",f2));
        table2.addCell(new Paragraph( "DISC",f2));
        table2.addCell(new Paragraph( "TAX",f2));
        table2.addCell(new Paragraph( "AMOUNT",f2));



        table2.setHeaderRows(1);

        PdfPCell[] cells = table2.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(new BaseColor(239,239,239));


        }






        for (int i = 0; i < completedbilllsummaryist.size(); i++) {

            nname = completedbilllsummaryist.get(i);
            qty = completedbilllsummaryist.get(i);
            pprice = completedbilllsummaryist.get(i);
            ttax = completedbilllsummaryist.get(i);
            ddis = completedbilllsummaryist.get(i);
            itemcount+= Integer.valueOf(completedbilllsummaryist.get(i).getQuantity());

            String n = nname.getProductname();
            String q = nname.getQuantity();
            String sp = pprice.getSprice();
            String d = ddis.getDiscount();
            String t = ttax.getTax();
            String a = ttax.getTprice();




            table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table2.addCell( String.valueOf(n));
            table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table2.addCell(String.valueOf(q));
            table2.addCell( sp );
            table2.addCell( (d)+"%");
            table2.addCell(String.valueOf(t)+"%");

            table2.addCell(String.valueOf(a)+ rs_symbol);


        }



        PdfPTable bordertable4 = new PdfPTable(new float[]{1});
        bordertable4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        bordertable4.getDefaultCell().setFixedHeight(30);
        bordertable4.setSpacingBefore(30);


        bordertable4.setTotalWidth(PageSize.A4.getWidth());
        bordertable4.setWidthPercentage(100);
        bordertable4.getDefaultCell().setBorder(0);

        bordertable4.addCell("");


        PdfPTable table4 = new PdfPTable(new float[]{2, 2, 2, 2,2,2});
        table4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table4.getDefaultCell().setFixedHeight(30);



        table4.setTotalWidth(PageSize.A4.getWidth());
        table4.setWidthPercentage(100);
        table4.getDefaultCell().setBorder(0);
        table4.getDefaultCell().setBackgroundColor(new BaseColor(239,239,239));




        table4.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table4.addCell(new Paragraph("SUB",f2));
        table4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);


        table4.addCell(new Paragraph(String.valueOf(itemcount),f2));
        table4.addCell(new Paragraph(rs_symbol+ model.getMainpricetotal(),f2));
        table4.addCell(new Paragraph(rs_symbol+String.format("%.2f",Double.valueOf( Double.valueOf( model.getDiscounttotal()))),f2));
        table4.addCell(new Paragraph(rs_symbol+ String.format("%.2f",Double.valueOf( model.getTaxtotal())),f2));
        table4.addCell(new Paragraph(  model.getGpricetotal(), f2));




     /*  PdfPTable rupeestab = new PdfPTable(1);

       rupeestab.getDefaultCell().setFixedHeight(30);
       rupeestab.setTotalWidth(PageSize.A4.getWidth());
       rupeestab.setWidthPercentage(100 );
       rupeestab.getDefaultCell().setBorderWidth(0);





       rupeestab.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT );

       //rupeestab.addCell(new PdfPCell( rupimage)).setHorizontalAlignment(Element.ALIGN_RIGHT);


       rupeestab.addCell(  pdfPCell).setHorizontalAlignment(Element.ALIGN_RIGHT);*/



      /* rupeestab.getDefaultCell().setNoWrap(true);
       rupeestab.getDefaultCell().setBorderColor(BaseColor.WHITE);
       rupeestab.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
       rupeestab.addCell( new PdfPCell( pdfPCell2));*/






        PdfPTable table5 = new PdfPTable(new float[]{6, 1, 3, 2});
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table5.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table5.getDefaultCell().setFixedHeight(30);
        table5.getDefaultCell().setBorder(2);
        table5.setTotalWidth(PageSize.A4.getWidth());
        table5.setWidthPercentage(100);






        table5.getDefaultCell().setBorderColor(BaseColor.WHITE);
        table5.addCell("");
        table5.addCell("");
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table5.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table5.addCell(new Paragraph( "Product Total :",f2));
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table5.addCell(rs_symbol+  model.getMainpricetotal());


        table5.getDefaultCell().setBorderColor(BaseColor.WHITE);
        table5.addCell("");
        table5.addCell("");
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        table5.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table5.addCell(new Paragraph( "Discount :",f2));

        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table5.addCell(rs_symbol+ String.format("%.2f",Double.valueOf( model.getDiscounttotal())));


        table5.getDefaultCell().setBorderColor(BaseColor.WHITE);
        table5.addCell("");
        table5.addCell("");
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table5.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table5.addCell(new Paragraph( "Tax :",f2));
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table5.addCell(rs_symbol+String.format("%.2f",Double.valueOf( model.getTaxtotal())));


        table5.getDefaultCell().setBorderColor(BaseColor.WHITE);
        table5.addCell("");
        table5.addCell("");
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);


        //BaseFont baseFont = BaseFont.createFont("font/arialunicodems.ttf", "UTF-8", BaseFont.EMBEDDED);
        // BaseFont baseFont2 = BaseFont.createFont("arialunicodems.ttf","UTF-8", BaseFont.EMBEDDED);
        // Font ff = FontFactory.getFont("font/arial.ttf", 12 ,BaseColor.BLUE);



        table5.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table5.addCell(new Paragraph("Grand Total :",f2));
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

        table5.addCell(new Paragraph(    String.format("%.0f",Double.valueOf( model.getGpricetotal()))));


        table5.getDefaultCell().setBorderColor(BaseColor.WHITE);
        table5.addCell("");
        table5.addCell("");
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table5.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table5.addCell(new Paragraph("Payment Method :",f2));
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table5.addCell(model.getPaymethod());



        table5.getDefaultCell().setBorderColor(BaseColor.WHITE);
        table5.addCell("");
        table5.addCell("");
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table5.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table5.addCell(new Paragraph("Received Amount :",f2));
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table5.addCell(rs_symbol+ model.getPaidamount());







        table5.getDefaultCell().setBorderColor(BaseColor.WHITE);
        table5.addCell("");
        table5.addCell("");
        table5.getDefaultCell().setBorderColor(BaseColor.BLACK);
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table5.addCell(new Paragraph("Balance :", f2));
        table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table5.addCell(model.getBalanceamount());




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
        document.add(toptable2);

        lineSeparator.setLineColor(new BaseColor(239,239,239));
        document.add(  lineSeparator);

        document.add(new Paragraph(  "\n"));



        document.add(table1);


        //lineSeparator.setLineColor(new BaseColor(102   ,102,102));




        //     document.add(new Paragraph(  "\n"));

        document.add(table2);

        // lineSeparator.setLineColor(new BaseColor(214,215,215));
        document.add(  lineSeparator);








        //  document.add(new Chunk( lineSeparator));

        //   document.add(table3);



        document.add(bordertable4);

        document.add(table4);


        document.add(new Paragraph(  "\n"));




        lineSeparator.setLineColor(new BaseColor(239,239,239));
        document.add(  lineSeparator);

        document.add(new Paragraph(  "\n"));


        document.add(table5);

        document.add(new Paragraph(  "\n"));
        lineSeparator.setLineColor(new BaseColor(239,239,239));
        document.add(  lineSeparator);

        document.add(new Paragraph(  "\n"));

        document.add(table6);




        document.close();
        Toast.makeText(mContext, "pdf created", Toast.LENGTH_SHORT).show();

        bottomsheetpdfoption(uri);



    }





    private void previewPdf() {


        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(pdfFile.toString());

        sharingIntent.setType("application/pdf");
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));



       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(file.toString()), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        mContext.startActivity(intent);*/


    }


    public void bottomsheetpdfoption(Uri uri)
    {
        final BottomSheetDialog pdfoption = new BottomSheetDialog(mContext,R.style.Bottomsheetdialtheme);
        pdfoption.setContentView(R.layout.bottom_sheet_pdf_option);
        pdfoption.setCanceledOnTouchOutside(false);
        pdfoption.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        pdfoption.show();


        LinearLayout  viewpdf = pdfoption.findViewById(R.id.viewpdf);
        LinearLayout  sharepdf = pdfoption.findViewById(R.id.sharepdf);

        viewpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpdf(uri);
            }
        });

        sharepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePdf(uri);
            }
        });



    }

    private void sharePdf(Uri uri) {


        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        //   Uri screenshotUri = Uri.parse(file.toString());

        sharingIntent.setType("application/pdf");
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share using"));



       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(file.toString()), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        mContext.startActivity(intent);*/


    }

    public  void viewpdf(Uri uri)  {

        Intent viewpdfIntent = new Intent(Intent.ACTION_VIEW);



        viewpdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        viewpdfIntent.setDataAndType(uri, "application/pdf");
        mContext. startActivity(viewpdfIntent);
    }









}

