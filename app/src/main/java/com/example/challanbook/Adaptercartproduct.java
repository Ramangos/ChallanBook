package com.example.challanbook;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptercartproduct extends RecyclerView.Adapter<Adaptercartproduct.ViewHolder> {


    List<Modelcartproducts> modelnameList;
    ArrayList<Modelcartproducts> cartlistbillprice = new ArrayList<>();
    Modelcartproducts model;
    String rs_symbol = "\u20B9 ";
    View v;
    StringBuilder productname = new StringBuilder();
    StringBuilder pprice = new StringBuilder();
    StringBuilder sprice = new StringBuilder();
    StringBuilder discount = new StringBuilder();
    StringBuilder disamount = new StringBuilder();
    StringBuilder tax = new StringBuilder();
    StringBuilder taxamount = new StringBuilder();
    StringBuilder quantity = new StringBuilder();
    StringBuilder prodprice = new StringBuilder();
    StringBuilder tprice = new StringBuilder();
    StringBuilder ppricebreak = new StringBuilder();
    Adaptercartproduct adaptercartproduct;
    Context mContext;
    View view;


    public static ClickListener clickListener;

    double afterdiscount,tper,sp,total;

    double ppricetotal, gmainprice,gdiscount,gtax, gtotal;
    int itemcount, size_modelnameList;

    private clicktransfer clicktransfer;

    public interface clicktransfer {
        void clicktrnfr( String productname, String pprice, String sprice,String discount,String disamount,String tax,String taxamount,
                         String quantity,String prodprice, String tprice, String ppricebreak,

                        double ppricetotal, double mprice, double t, double tt, double ttt, int countt)

                throws IOException;
    }
    public void setonclicktransfer(final clicktransfer clicktransfer) {
        this.clicktransfer = clicktransfer;
    }










    private modelsizetransfer modelsizetransfer;

    public interface modelsizetransfer {
        void sizetrnfr(  int sizemodel)

                throws IOException;
    }
    public void setonclickmodelsizetransfer(final modelsizetransfer modelsizetransfer) {
        this.modelsizetransfer = modelsizetransfer;
    }






    public class ViewHolder extends RecyclerView.ViewHolder   {




        public TextView productname,price,discount,tax,totalamount;
        ElegantNumberButton numbr;

        TextView b1;

        public ViewHolder(View itemView)  {
            super(itemView);

            productname =  itemView.findViewById(R.id.product);
            price =  itemView.findViewById(R.id.sellprice);
            discount =  itemView.findViewById(R.id.discount);
            tax = itemView.findViewById(R.id.tax);
            totalamount =  itemView.findViewById(R.id.price);
            numbr = itemView.findViewById(R.id.numbr);



        }


        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(),v);
            return false;
        }
    }


    public Adaptercartproduct(List<Modelcartproducts> dataList) {



        this.modelnameList = dataList;


    }







    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cart_row,viewGroup,false);

        mContext = viewGroup.getContext();

        return new ViewHolder(view);

    }





    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        model = modelnameList.get(position);

        holder.setIsRecyclable(false);

        holder.numbr.setNumber(model.getQuantity());
        holder.productname.setText(model.getProduct());



        double dper = Double.parseDouble(model.getSprice())* Double.parseDouble(model.getDiscount())/100 ;
        afterdiscount =  Double.parseDouble(model.getSprice())-dper;

        tper = afterdiscount*Double.parseDouble(model.getTax()) /100;

        holder.price.setText(model.getSprice());
        if(model.getDiscount().equals("0"))
        {
            holder.discount.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.discount.setText("Discount "+model.getDiscount()+"%");
        }
        holder.tax.setText(model.getTax()+ "%" );



        double t = Double.valueOf(model.getTotalamount()) * Double.valueOf(model.getQuantity());
        double purpricebreak = Double.valueOf(model.getPprice()) * Double.valueOf(model.getQuantity());

        model.setGmainprice(String.valueOf(Double.valueOf(model.getSprice()) * Double.valueOf(model.getQuantity())));
        model.setGdiscount(String.valueOf(Double.valueOf(model.getDiscountamount()) * Double.valueOf(model.getQuantity())));
        model.setGtax(String.valueOf(Double.valueOf(model.getTaxamount()) * Double.valueOf(model.getQuantity())));
        model.setGtotal(String.valueOf( t));
      //  holder.totalamount.setText(rs_symbol+ String.format("%.0f", t));
        holder.totalamount.setText(rs_symbol+ String.format("%.2f", t));
        model.setTprice(String.valueOf(t));
        model.setPpricebreak(String.valueOf( purpricebreak));
        model.setPpricetotal(String.valueOf( purpricebreak));









        cartlistbillprice.clear();
        ppricetotal=0;
        gmainprice=0;
        gdiscount=0;
        gtax=0;
        gtotal=0;
        itemcount=0;
        productname.setLength(0);
        pprice.setLength(0);
        sprice.setLength(0);
        discount.setLength(0);
        disamount.setLength(0);
        tax.setLength(0);
        taxamount.setLength(0);
        quantity.setLength(0);
        prodprice.setLength(0);
        tprice.setLength(0);
        ppricebreak.setLength(0);




        for (Modelcartproducts spp : modelnameList) {

            cartlistbillprice.add(spp);


        }




        for (int i = 0; i < cartlistbillprice.size(); i++)
        {


                productname.append(cartlistbillprice.get(i).getProduct());
                productname.append( ",");
                pprice.append(cartlistbillprice.get(i).getPprice());
                pprice.append(",");
                sprice.append(cartlistbillprice.get(i).getSprice());
                sprice.append( ",");
                discount.append(cartlistbillprice.get(i).getDiscount());
                discount.append( ",");
                disamount.append(cartlistbillprice.get(i).getDiscountamount());
                disamount.append(",");
                tax.append(cartlistbillprice.get(i).getTax());
                tax.append(",");
                taxamount.append(cartlistbillprice.get(i).getTaxamount());
                taxamount.append(",");
                quantity.append(cartlistbillprice.get(i).getQuantity());
                quantity.append(",");
                prodprice.append(cartlistbillprice.get(i).getTotalamount());
                prodprice.append(",");
                tprice.append(cartlistbillprice.get(i).getTprice());
                tprice.append(",");
                ppricebreak.append(cartlistbillprice.get(i).getPpricebreak());
                ppricebreak.append(",");
                ppricetotal+=Double.valueOf( cartlistbillprice.get(i).getPpricetotal()) ;
                gmainprice+=Double.valueOf( cartlistbillprice.get(i).getGmainprice()) ;
                gdiscount+=Double.valueOf( cartlistbillprice.get(i).getGdiscount()) ;
                gtax+=Double.valueOf( cartlistbillprice.get(i).getGtax()) ;
                gtotal+= Double.valueOf( cartlistbillprice.get(i).getGtotal()) ;
                itemcount+= Integer.valueOf(cartlistbillprice.get(i).getQuantity());

        }




        holder.numbr.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                model = modelnameList.get(position);


                model.setQuantity(String.valueOf(newValue));

                notifyDataSetChanged();

                Toast.makeText(mContext, "tt "+model.getDiscountamount(), Toast.LENGTH_SHORT).show();

                trnsfr();




                if (holder.numbr.getNumber().toString().equals("0"))
                {

                    modelnameList.remove(position);

                    notifyItemRemoved(position);

                    size_modelnameList = modelnameList.size();

                    notifyDataSetChanged();


                    Toast.makeText(holder.numbr.getContext(), size_modelnameList + " cartlistbillprice", Toast.LENGTH_SHORT).show();



                    sizetrnsfr();



                }
            }
        });


        trnsfr();

    }

    public void trnsfr()
    {
          if(clicktransfer!=null) {


              Toast.makeText(mContext, "working "+ Double.valueOf( model.getDiscountamount())* Double.valueOf( model.getQuantity()), Toast.LENGTH_SHORT).show();




                    try {
                        clicktransfer.clicktrnfr(productname.deleteCharAt(productname.length()-1).toString(),
                                pprice.deleteCharAt(pprice.length()-1).toString(),
                                sprice.deleteCharAt(sprice.length()-1).toString(),
                                discount.deleteCharAt(discount.length()-1).toString(),
                                disamount.deleteCharAt(disamount.length()-1).toString(),
                                tax.deleteCharAt(tax.length()-1).toString(),
                                taxamount.deleteCharAt(taxamount.length()-1).toString(),
                                quantity.deleteCharAt(quantity.length()-1).toString(),
                                prodprice.deleteCharAt(prodprice.length()-1).toString(),
                                tprice.deleteCharAt(tprice.length()-1).toString(),
                                ppricebreak.deleteCharAt(ppricebreak.length()-1).toString(),



                              ppricetotal,  gmainprice, gdiscount,gtax, gtotal,itemcount);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

    }


    public void sizetrnsfr()
    {
        if(modelsizetransfer!=null) {


            try {
                modelsizetransfer.sizetrnfr( size_modelnameList);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    @Override
    public int getItemCount()
    {
        return modelnameList.size();
    }


    private void addItem(int position,Modelcartproducts infoData) {

        modelnameList.add(position, infoData);
        notifyItemInserted(position);
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }






}

