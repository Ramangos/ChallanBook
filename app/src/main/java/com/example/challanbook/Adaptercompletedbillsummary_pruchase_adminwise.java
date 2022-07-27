package com.example.challanbook;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class Adaptercompletedbillsummary_pruchase_adminwise extends RecyclerView.Adapter<Adaptercompletedbillsummary_pruchase_adminwise.ViewHolder> {


    List<Modelcompletedbillsummary> modellist;
    Modelcompletedbillsummary model;
    String rs_symbol = "\u20B9";


    private OnItemClickListener omOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TextView b , View view, Modelcompletedbill obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.omOnItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {




        public TextView  purchaseproduct,purchaseprice,tpurchaseprice;
        TextView edit;
        RelativeLayout layout_balancestatus,relativeLayout4;
        LinearLayout linearlayout;
        CardView cardView;
        ImageView arrowbutton;
        RecyclerView rcv;
        ImageView menubutton;


        public ViewHolder(View itemView)  {
            super(itemView);



            purchaseproduct =  itemView.findViewById(R.id.purchaseproduct);
            purchaseprice =  itemView.findViewById(R.id.purchaseprice);
            tpurchaseprice =  itemView.findViewById(R.id.tpurchaseprice);






        }







    }


    public Adaptercompletedbillsummary_pruchase_adminwise(List<Modelcompletedbillsummary> dataList) {



        this.modellist = dataList;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.completedbill_summary_adminwise_purchase_row,viewGroup,false);
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position) {
        model = modellist.get(position);
        holder.setIsRecyclable(false);

/*

        String pro[] = model.getProductname().split(",");
        String spri[] = model.getSprice().split(",");


        String quan[] = model.getQuantity().split(",");

        String dis[] = model.getDiscount().split(",");

        String tx[] = model.getTax().split(",");


        String pri[] = model.getTprice().split(",");



        for (int j = 0; j < pro.length; j++) {


            String prdct = pro[j];
            String sprice = spri[j];

            String quantity = quan[j];

            String discount = dis[j];
            String tax = tx[j];

            String price = pri[j];


            holder.productname.setText(prdct + " x "+ quantity);
            holder.sprice.setText(sprice+ "/-");
            holder.discount.setText(discount+"%");
            holder.tax.setText(tax+"%");
            holder.price.setText(rs_symbol+ price+"/-");


        }


*/

       /* holder.productname.setText(model.getProductname() + " x "+model.getQuantity());
        holder.sprice.setText(model.getSprice()+ "/-");
        holder.discount.setText(model.getDiscount()+"%");
        holder.tax.setText(model.getTax()+"%");
        holder.price.setText(rs_symbol+ model.getTprice()+"/-");
*/
        holder.purchaseproduct.setText(model.getProductname() + " x "+model.getQuantity());
        holder.purchaseprice.setText(rs_symbol+ model.getPprice());
        holder.tpurchaseprice.setText(rs_symbol+ model.getPpricebreak());

    }




    @Override
    public int getItemCount()
    {
        return modellist.size();
    }







}

