package com.example.challanbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignInActivity extends AppCompatActivity {

    TextView textViewsignup,fpass;
    EditText outletedit,passwordedit;
    Button loginbutton;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String out,pass;
    AlertDialog progressbardail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();

        progessbarshowmethod();

        textViewsignup = findViewById(R.id.textviewsignup);
        outletedit=findViewById(R.id.outletedit);
        passwordedit=findViewById(R.id.passwordedit);
        loginbutton=findViewById(R.id.loginbutton);
        fpass=findViewById(R.id.fpass);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        Intent intent  = new Intent(SignInActivity.this, MainActivity.class );

        sp = getSharedPreferences("spfile",0);
        ed = sp.edit();

        if (sp.contains("outletspkey") && sp.contains("outletpassspkey"))
        {

            startActivity(intent);
            finish();
        }



        textViewsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
                startActivity(intent);


            }
        });

        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(SignInActivity.this, ForgetPassActivity.class);
                startActivity(intent1);

            }
        });


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbardail.show();
                login();
            }
        });


    }

    public  void login()
    {
//        idd = sid.getText().toString();



        RequestQueue rq = Volley.newRequestQueue(SignInActivity.this);
        String url  =
                "https://strigiform-attorney.000webhostapp.com/billing/login2.php?o="+outletedit.getText().toString().trim();

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                if(response.equals("nodata"))
                {
                    progressbardail.dismiss();

                    Toast.makeText(SignInActivity.this, "Phone number not registered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    showJson(response);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbardail.dismiss();
                Toast.makeText(SignInActivity.this, "NOt success "+error.toString(), Toast.LENGTH_SHORT).show();

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



                if(jsonObject1.getString("outletpass").equals(passwordedit.getText().toString()))
                {

                    progressbardail.dismiss();
                    ed.putString("outletnamespkey",jsonObject1.getString("outletname"));
                    ed.putString("outletaddspkey",jsonObject1.getString("outletadd"));
                    ed.putString("outletspkey",jsonObject1.getString("outlet"));
                    ed.putString("outletpassspkey",jsonObject1.getString("outletpass"));
                    ed.commit();

                    Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressbardail.dismiss();
                    Toast.makeText(SignInActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }




            }


        }


        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  void progessbarshowmethod()
    {
        progressbardail = new AlertDialog.Builder(SignInActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
        progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressbardail.setCancelable(false);

        progressbardail.setView(dialogView);




    }


}