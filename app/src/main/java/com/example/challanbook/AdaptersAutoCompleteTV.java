
package com.example.challanbook;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdaptersAutoCompleteTV extends ArrayAdapter<ModelAutocompleteTV> {



    private List<ModelAutocompleteTV> ModelAutocompleteproductsListfull;
    String rs_symbol = "\u20B9";
    Context mContext;
    String p;









    public AdaptersAutoCompleteTV( Context context, List<ModelAutocompleteTV> objects) {
        super(context, 0, objects);


        ModelAutocompleteproductsListfull = new ArrayList<>(objects);
        this.mContext=context;

    }


    @Override
    public Filter getFilter() {
        return productFilter;
    }






    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autotextcomplete_row, parent, false);





        }


        final ModelAutocompleteTV model = getItem(position);


        mContext = convertView.getContext();

        TextView product =  convertView.findViewById(R.id.product);
        TextView sellprice =  convertView.findViewById(R.id.sellprice);
        TextView discount =  convertView.findViewById(R.id.discount);
        TextView tax =  convertView.findViewById(R.id.tax);
        TextView price =  convertView.findViewById(R.id.price);






        if (model != null) {










            product.setText(model.getProduct());
            sellprice.setText(rs_symbol+" "+ model.sprice);
            tax.setText("Tax "+model.getTax()+"%");
            discount.setText("Discount "+model.getDiscount()+"%");
            double gtotal = Double.valueOf( model.getTotalamount());
            price.setText(rs_symbol+" "+ String.format("%.2f", gtotal));

            p = product.getText().toString();






        }




        /* return createItemView(position, convertView, parent);*/




        /*



         *//*TextView productname =  convertView.findViewById(R.id.productname);
        TextView price =  convertView.findViewById(R.id.price);
        TextView  discount =  convertView.findViewById(R.id.discount);
        TextView totalamount =  convertView.findViewById(R.id.totalamount);
*//*

        ModelAutocompleteproducts Item = ModelAutocompleteproductsListfull.get(position);
    *//*    ModelAutocompleteproducts Item = getItem(position);*//*

        if (Item != null) {
           *//* productname.setText(Item.getProduct());
            price.setText(Item.getPrice());
            discount.setText(Item.getDiscount());
            totalamount.setText(Item.getTotalamount());*//*


           tv.setText(Item.getProduct());


        }
        Toast.makeText(convertView.getContext(), Item.getProduct()+" fdwed", Toast.LENGTH_SHORT).show();
*/
        return convertView;
    }





    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ModelAutocompleteTV> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(ModelAutocompleteproductsListfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelAutocompleteTV item : ModelAutocompleteproductsListfull) {


                    if (item.getProduct().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ModelAutocompleteTV) resultValue).getProduct();
        }


    };

}

