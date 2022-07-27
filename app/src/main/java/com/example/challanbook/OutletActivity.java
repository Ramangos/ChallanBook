package com.example.challanbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class OutletActivity extends AppCompatActivity {

    TextView outname,outadd,outlet,outpass;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String getoutletname,getoutletadd, getoutlet,getoutletpass;
    CardView lay1,lay2,lay3,lay4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        getSupportActionBar().hide();


        lay1=findViewById(R.id.lay1);
        lay2=findViewById(R.id.lay2);
        lay3=findViewById(R.id.lay3);
        lay4=findViewById(R.id.lay4);



        sp = getSharedPreferences("spfile",0);

        getoutletname =sp.getString("outletnamespkey","");
        getoutletadd =sp.getString("outletaddspkey","");
        getoutlet =sp.getString("outletspkey", "");
        getoutletpass=sp.getString("outletpassspkey","");

        ed = sp.edit();

        outname=findViewById(R.id.outname);
        outadd=findViewById(R.id.outadd);
        outlet=findViewById(R.id.outlet);
        outpass=findViewById(R.id.outpass);

        outname.setText(getoutletname);
        outadd.setText(getoutletadd);
        outlet.setText(getoutlet);
        outpass.setText(getoutletpass);


        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog updatebottomSheet = new BottomSheetDialog(OutletActivity.this, R.style.Bottomsheetdialtheme);
                updatebottomSheet.setContentView(R.layout.bottom_sheet_update_info);
                updatebottomSheet.setCanceledOnTouchOutside(false);

                updatebottomSheet.show();


                TextView titleupdate= updatebottomSheet.findViewById(R.id.titleupdate);
                EditText edupdate=updatebottomSheet.findViewById(R.id.edupdate);
                Button updatebt=updatebottomSheet.findViewById(R.id.updatebt);


                titleupdate.setText("Business Name");
                edupdate.setHint("Add Business Name");

                edupdate.setText(getoutletname);


                updatebt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getoutletname = edupdate.getText().toString();
                        update();
                        Toast.makeText(OutletActivity.this, "working", Toast.LENGTH_SHORT).show();
                        updatebottomSheet.dismiss();

                    }
                });




            }
        });

        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog updatebottomSheet = new BottomSheetDialog(OutletActivity.this, R.style.Bottomsheetdialtheme);
                updatebottomSheet.setContentView(R.layout.bottom_sheet_update_info);
                updatebottomSheet.setCanceledOnTouchOutside(false);
                updatebottomSheet.show();

                TextView titleupdate= updatebottomSheet.findViewById(R.id.titleupdate);
                EditText edupdate=updatebottomSheet.findViewById(R.id.edupdate);
                Button updatebt=updatebottomSheet.findViewById(R.id.updatebt);

                titleupdate.setText("Business Address");
                edupdate.setHint("Add Business Address");

                edupdate.setText(getoutletadd);

                updatebt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getoutletadd = edupdate.getText().toString();
                        update();
                        updatebottomSheet.dismiss();

                    }
                });





            }
        });

        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog updatebottomSheet = new BottomSheetDialog(OutletActivity.this, R.style.Bottomsheetdialtheme);
                updatebottomSheet.setContentView(R.layout.bottom_sheet_update_info);
                updatebottomSheet.setCanceledOnTouchOutside(false);
                updatebottomSheet.show();

                TextView titleupdate= updatebottomSheet.findViewById(R.id.titleupdate);
                EditText edupdate=updatebottomSheet.findViewById(R.id.edupdate);
                Button updatebt=updatebottomSheet.findViewById(R.id.updatebt);

                titleupdate.setText("Business Phone");
                edupdate.setHint("Add Business Phone");


                edupdate.setText(getoutlet);


            }
        });

        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog updatebottomSheet = new BottomSheetDialog(OutletActivity.this, R.style.Bottomsheetdialtheme);
                updatebottomSheet.setContentView(R.layout.bottom_sheet_update_pass);
                updatebottomSheet.setCanceledOnTouchOutside(false);
                 updatebottomSheet.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
                updatebottomSheet.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                updatebottomSheet.show();






                EditText edcurrpass=updatebottomSheet.findViewById(R.id.edcurrpass);
                EditText edpass=updatebottomSheet.findViewById(R.id.edpass);
                EditText edconfirmpass=updatebottomSheet.findViewById(R.id.edconfirmpass);

                Button savepass=updatebottomSheet.findViewById(R.id.savepass);



                edcurrpass.setText(getoutletpass);


                savepass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        
                        if(edcurrpass.getText().toString().isEmpty())
                        {
                            Toast.makeText(OutletActivity.this, "Please entenr your current password", Toast.LENGTH_SHORT).show();
                            edcurrpass.requestFocus();
                        }
                        else
                        {
                            if(edpass.getText().toString().isEmpty() ||edconfirmpass.getText().toString().isEmpty() )
                            {
                                Toast.makeText(OutletActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if(TextUtils.equals(edcurrpass.getText().toString().trim(),getoutletpass))
                                {
                                    if (TextUtils.equals(edpass.getText().toString().trim(),edconfirmpass.getText().toString().trim()))
                                    {
                                        getoutletpass = edconfirmpass.getText().toString().trim();
                                        update();
                                        updatebottomSheet.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(OutletActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(OutletActivity.this, "Your old password was entered incorrectly", Toast.LENGTH_SHORT).show();
                                }



                            }
                        }



                    }
                });



            }
        });

    }





    public void  update()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(OutletActivity.this);
        String url = "https://strigiform-attorney.000webhostapp.com/billing/updateoutletdetail.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(OutletActivity.this, "res "+response, Toast.LENGTH_SHORT).show();

                if(response.equals("success"))
                {
                    ed.putString("outletnamespkey", getoutletname);
                    ed.putString("outletaddspkey",getoutletadd);

                    ed.putString("outletpassspkey",getoutletpass);
                    ed.commit();

                    Toast.makeText(OutletActivity.this, "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OutletActivity.this, OutletActivity.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(OutletActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringStringMap = new HashMap<String, String>();



                stringStringMap.put("outletname", getoutletname);
                stringStringMap.put("outletadd", getoutletadd );
                stringStringMap.put("outletpass", getoutletpass);
                stringStringMap.put("outlet", getoutlet);


                return stringStringMap;
            }
        };

        requestQueue.add(stringRequest);
    }
}