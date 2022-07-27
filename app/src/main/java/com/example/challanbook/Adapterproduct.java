package com.example.challanbook;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;


public class Adapterproduct extends RecyclerView.Adapter<Adapterproduct.ViewHolder> {


    List<Modelproducts> modelnameList;
    Modelproducts model;
    String rs_symbol = "\u20B9";
    Context mContext;
    String sproduct,spprice,ssprice,sdiscount,dismount,stax,taxamount,stotalamount;
    public String deletproductname;
    SharedPreferences sp;
    String getoutlet;

    double pri,dis,tx;


    public static ClickListener clickListener;




    public class ViewHolder extends RecyclerView.ViewHolder     {




        public TextView productname,sellprice,discount,price,tax;
        TextView edit;


        public ViewHolder(View itemView)  {
            super(itemView);


            productname =  itemView.findViewById(R.id.product);
            sellprice =  itemView.findViewById(R.id.sellprice);
            discount =  itemView.findViewById(R.id.discount);
            price =  itemView.findViewById(R.id.price);
            tax =  itemView.findViewById(R.id.tax);
            edit =  itemView.findViewById(R.id.edit);


            sp =mContext.getSharedPreferences("spfile",0);
            getoutlet =sp.getString("outletspkey", "");





        }

        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(),v);
            return false;
        }







    }


    public Adapterproduct(List<Modelproducts> dataList) {



        this.modelnameList = dataList;


    }








    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inventry_row,viewGroup,false);

        mContext = viewGroup.getContext();

        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position) {
        model = modelnameList.get(position);



        //holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(holder.linearLayout.getContext(),R.anim.fade_transition_animation));

        holder.setIsRecyclable(false);

        holder.productname.setText(model.getProduct());



        holder.sellprice.setText(rs_symbol+" "+ model.sprice);


        if(model.getDiscount().contentEquals("0"))
        {
            holder.discount.setVisibility(View.INVISIBLE);
        }
        else
            {
            holder.discount.setText("Discount " + model.getDiscount() + "%");
        }
        holder.tax.setText("Tax "+model.getTax()+"%");
        holder.price.setText(rs_symbol+" "+ String.format("%.2f", Double.valueOf( model.getTotalamount())));




        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model = modelnameList.get(position);

                Toast.makeText(holder.edit.getContext(), "dsds", Toast.LENGTH_SHORT).show();

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(holder.edit.getContext(),R.style.Bottomsheetdialtheme);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_inventry);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                EditText product=bottomSheetDialog.findViewById(R.id.product);
                EditText purchaseprice=bottomSheetDialog.findViewById(R.id.purchaseprice);
                EditText sellingprice=bottomSheetDialog.findViewById(R.id.sellingprice);
                EditText discount=bottomSheetDialog.findViewById(R.id.discount);
                EditText tax=bottomSheetDialog.findViewById(R.id.tax);
                EditText totalamount=bottomSheetDialog.findViewById(R.id.tamount);
                TextView titleinv=bottomSheetDialog.findViewById(R.id.titleinv);
                titleinv.setText("Update Item");
                Button delete=bottomSheetDialog.findViewById(R.id.addandclose);
                delete.setText("DELETE");
                Button additem = bottomSheetDialog.findViewById(R.id.additem);


                product.setText(model.getProduct());
                purchaseprice.setText(model.getPprice());
                sellingprice.setText(model.getSprice());
                discount.setText(model.getDiscount());
                tax.setText(model.getTax());
                double gtotal = Double.valueOf( model.getTotalamount());
                totalamount.setText(String.format("%.2f",gtotal));
                additem.setText("Update");


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


                        Toast.makeText(mContext, "Total amount automatically calculated", Toast.LENGTH_SHORT).show();
                    }
                });






                additem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(product.getText().toString().isEmpty())
                        {
                            Toast.makeText(mContext, "Please Enter Product Name", Toast.LENGTH_SHORT).show();
                            product.setError("Please Enter Product Name");

                        }

                        if(purchaseprice.getText().toString().isEmpty())
                        {
                            purchaseprice.setText("0");
                            //spprice="0";
                        }


                        if(sellingprice.getText().toString().isEmpty()  )
                        {
                            Toast.makeText(mContext, "Please Enter Selling Price", Toast.LENGTH_SHORT).show();
                            sellingprice.setError("Please Enter Selling Price");


                        }

                        if(sellingprice.getText().toString().contentEquals("0"))
                        {
                            Toast.makeText(mContext, "Selling Price can not be Zero", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(mContext, "Purchase price can not be greater than Selling price", Toast.LENGTH_SHORT).show();
                            }
                            else if( Integer.valueOf(sellingprice.getText().toString()) >= Integer.valueOf(purchaseprice.getText().toString()) )
                            {

                                sproduct= product.getText().toString();
                                spprice= purchaseprice.getText().toString();
                                ssprice= sellingprice.getText().toString();
                                sdiscount= discount.getText().toString();
                                stax= tax.getText().toString();
                                stotalamount= totalamount.getText().toString();

                                updateInventry();

                            }









                        }






                      /*  sproduct= product.getText().toString();
                        spprice= purchaseprice.getText().toString();
                        ssprice= sellingprice.getText().toString();
                        sdiscount= discount.getText().toString();
                        stax= tax.getText().toString();
                        stotalamount= totalamount.getText().toString();*/




                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        model = modelnameList.get(position);

                        deletproductname = model.getProduct();
                        deletInventryItem();

                    }
                });


                bottomSheetDialog.show();

            }
        });












    }




    @Override
    public int getItemCount()
    {
        return modelnameList.size();
    }

    public void filterList(ArrayList<Modelproducts> filterdNames) {
        this.modelnameList = filterdNames;
        notifyDataSetChanged();
    }


    private void addItem(int position,Modelproducts infoData) {

        modelnameList.add(position, infoData);
        notifyItemInserted(position);
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }


    public  void updateInventry()
    {




        RequestQueue rq = Volley.newRequestQueue(mContext);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/update_Inventry_Product.php";
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {




            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "okkkk register adapter"   + response, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent( mContext, InventryActivity.class);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {





                HashMap<String, String> hm = new HashMap<String, String>();


                double disam = pri*dis/100;

                double afterdiscount = pri-disam;

                double taxam = afterdiscount*tx/100;

                dismount = String.valueOf(disam);
                taxamount = String.valueOf(taxam);

                //hm.put("params", json.toString());
                hm.put(Config.KEY_outlet, getoutlet);
                hm.put(Config.KEY_product,sproduct );
                hm.put(Config.KEY_pprice, spprice);
                hm.put(Config.KEY_sprice, ssprice);
                hm.put(Config.KEY_discount, sdiscount);
                hm.put(Config.KEY_discountamount, dismount);
                hm.put(Config.KEY_tax, stax);
                hm.put(Config.KEY_taxamount, taxamount);
                hm.put(Config.KEY_total_amount, stotalamount);




                return hm;
            }

        };


        rq.add(sr);


    }


    public  void deletInventryItem()
    {




        RequestQueue rq = Volley.newRequestQueue(mContext);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/DeleteInventoryItem.php?product=" + deletproductname + "&outlet="+getoutlet;
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {




            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "okkkk register adapter"   + response, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent( mContext, InventryActivity.class);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) ;


        rq.add(sr);


    }





}

