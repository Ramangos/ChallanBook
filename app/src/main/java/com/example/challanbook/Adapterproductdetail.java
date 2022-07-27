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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;


public class Adapterproductdetail extends RecyclerView.Adapter<Adapterproductdetail.ViewHolder> {


    List<Modelproductdetail> modellist;
    Modelproductdetail model;
    String rs_symbol = "\u20B9";

    private OnItemClickListener omOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TextView b , View view, Modelcompletedbill obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.omOnItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {




        public TextView  product,price,quantity;



        public ViewHolder(View itemView)  {
            super(itemView);


            product =  itemView.findViewById(R.id.product);
            price =  itemView.findViewById(R.id.price);
            quantity =  itemView.findViewById(R.id.quantity);





        }







    }


    public Adapterproductdetail(List<Modelproductdetail> dataList) {



        this.modellist = dataList;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.productdetailrow,viewGroup,false);
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position) {
        model = modellist.get(position);

        holder.setIsRecyclable(false);


        holder.product.setText(model.getProductname());
        holder.price.setText(rs_symbol+" "+ model.getTprice());
        holder.quantity.setText(" x " +model.getQuantity());



    }




    @Override
    public int getItemCount()
    {
        return modellist.size();
    }







}

