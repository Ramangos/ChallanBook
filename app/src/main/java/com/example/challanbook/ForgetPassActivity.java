package com.example.challanbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ForgetPassActivity extends AppCompatActivity {

    EditText forgetoutletpass;
    Button conti;
    String sforgetoutletpass;
    private String mVerificationId;
    EditText editTextCode;
    Button submitotp;
    FirebaseAuth mAuth;
    String fotp ,otp;
    TextView timerr,resendotp;
    ProgressBar otpProgressBar;
    AlertDialog progressbardail;
    TextView   closebsheet;
    CountDownTimer timer;

    String newpass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        mAuth = FirebaseAuth.getInstance();

        forgetoutletpass = findViewById(R.id.forgetoutletpass);
        conti =findViewById(R.id.conti);
        progessbarshowmethod();


        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sforgetoutletpass = forgetoutletpass.getText().toString();


                if(sforgetoutletpass.isEmpty() || sforgetoutletpass.length() < 10){
                    forgetoutletpass.setError("Enter a valid mobile");
                    forgetoutletpass.requestFocus();

                }
                else
                {
                    checkdata();
                }

            }
        });


    }







    public  void checkdata()
    {

        String url = "https://strigiform-attorney.000webhostapp.com/billing/checkDuplicateData.php";
        StringRequest sr = new StringRequest(1 ,url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //   response = response.trim();


                if(response.contentEquals("dupli"))
                {

                    String mobile = "+91" + sforgetoutletpass;




                    bsheet();

                    sendVerificationCode(mobile);

                    Toast.makeText(ForgetPassActivity.this, "ok to proceed", Toast.LENGTH_SHORT).show();



                }


                else if(response.contentEquals("ok"))
                {

                    forgetoutletpass.setError("Phone No not registered");
                    forgetoutletpass.requestFocus();



                    Snackbar snackbar = Snackbar
                            .make( forgetoutletpass, "Phone No not registered", Snackbar.LENGTH_LONG);


                    snackbar.show();


                }

                else
                {
                    Toast.makeText(ForgetPassActivity.this, "Something went wrong \n"+ response, Toast.LENGTH_SHORT).show();



                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPassActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {





                Map<String, String> hm = new HashMap<String, String>();




                //hm.put("params", json.toString());
                hm.put("o", sforgetoutletpass);




                return hm;
            }

        };
        RequestQueue rq = Volley.newRequestQueue(ForgetPassActivity.this);
        rq.add(sr);


    }

    public void bsheet()
    {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ForgetPassActivity.this,R.style.Bottomsheetdialtheme);
        bottomSheetDialog.setContentView(R.layout.activity_otp);
        bottomSheetDialog.setCanceledOnTouchOutside(false);


        submitotp =bottomSheetDialog.findViewById(R.id. submitotp);
        editTextCode =bottomSheetDialog.findViewById(R.id.otpedittext);
        TextView helplogin =bottomSheetDialog.findViewById(R.id.helplogin);
        closebsheet=bottomSheetDialog.findViewById(R.id.closebsheet);
        TextView numdisplay=bottomSheetDialog.findViewById(R.id.numdisplay);
        timerr=bottomSheetDialog.findViewById(R.id.timerr);
        resendotp =bottomSheetDialog.findViewById(R.id.resendotp);
        otpProgressBar=bottomSheetDialog.findViewById(R.id.otpProgressBar);

        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(false);

        numdisplay.setText(sforgetoutletpass);

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendotp.setVisibility(View.GONE);
                otpProgressBar.setVisibility(View.VISIBLE);
                editTextCode.setText("");

                String mobile = "+91" + sforgetoutletpass;

                sendVerificationCode(mobile);
            }
        });



        closebsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.cancel();
                bottomSheetDialog.dismiss();
            }
        });

        helplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whapmethod();
            }
        });



        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = editTextCode.getText().toString().trim();



                if(!TextUtils.isEmpty(otp))
                {
                    progressbardail.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otp );
                    signInWithPhoneCredential(credential);

                    bottomSheetDialog.dismiss();


                }

                else
                {
                    Toast.makeText(ForgetPassActivity.this, "input otp", Toast.LENGTH_SHORT).show();
                }



            }

        });

    }



    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(10L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                super.onCodeAutoRetrievalTimeOut(s);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);

                                mVerificationId = s;

                                Toast.makeText(ForgetPassActivity.this, "code sent ", Toast.LENGTH_SHORT).show();



                                timer =  new CountDownTimer(30000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        otpProgressBar.setVisibility(View.GONE);
                                        timerr.setVisibility(View.VISIBLE);
                                        closebsheet.setEnabled(true);

                                        timerr.setText("Resend otp in 00 : " + millisUntilFinished / 1000);
                                        //here you can have your logic to set text to edittext
                                    }

                                    public void onFinish() {
                                        timerr.setVisibility(View.GONE);
                                        resendotp.setVisibility(View.VISIBLE);
                                    }

                                }.start();




                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                String code = phoneAuthCredential.getSmsCode();

                                //sometime the code is not detected automatically
                                //in this case the code will be null
                                //so user has to manually enter the code
                                if (code != null) {
                                    editTextCode.setText(code);
                                    //verifying the code

                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(ForgetPassActivity.this, "ver failed "+ e.getMessage(), Toast.LENGTH_SHORT).show();






                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }




    private void signInWithPhoneCredential(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgetPassActivity.this, "task succesfull", Toast.LENGTH_SHORT).show();
                            bsheetforfpass();

                        }
                        else
                        {
                            progressbardail.dismiss();


                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(ForgetPassActivity.this, "Invalid code entered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(ForgetPassActivity.this, "failed ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


    }

    public void whapmethod()
    {

        boolean installed = appInstalledOrNot("com.whatsapp");

        if(installed)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);


            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+917011493509 &text=i am having trouble logging into the app"));
            startActivity(intent);
        }
        if(!installed)
        {
            Toast.makeText(ForgetPassActivity.this, "Application not available", Toast.LENGTH_SHORT).show();
        }


    }

    private  boolean appInstalledOrNot(String url)
    {
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, packageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }

        return app_installed;

    }

    public void bsheetforfpass()
    {
        BottomSheetDialog bsheetforfpass = new BottomSheetDialog(ForgetPassActivity.this,R.style.Bottomsheetdialtheme);
        bsheetforfpass.setContentView(R.layout.bottom_sheet_frgtpass);
        bsheetforfpass.setCanceledOnTouchOutside(false);


        TextView edpass = bsheetforfpass.findViewById(R.id.edpass);
        TextView edconfirmpass = bsheetforfpass.findViewById(R.id.edconfirmpass);
        Button savepass =bsheetforfpass.findViewById(R.id.savepass);


        savepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressbardail.show();

                if (TextUtils.equals(edpass.getText().toString().trim(),edconfirmpass.getText().toString().trim()))
                {

                    newpass = edconfirmpass.getText().toString().trim();

                    updateoutletlogin();
                }
                else
                {
                    progressbardail.dismiss();
                    Toast.makeText(ForgetPassActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bsheetforfpass.show();
        bsheetforfpass.setCancelable(false);

    }


    public  void  updateoutletlogin()
    {

        RequestQueue rq = Volley.newRequestQueue(ForgetPassActivity.this );
        String url = "https://strigiform-attorney.000webhostapp.com/billing/updateoutletloginpass.php";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ForgetPassActivity.this, "okkkk", Toast.LENGTH_SHORT).show();

                progressbardail.dismiss();

                final AlertDialog dialogBuilder = new AlertDialog.Builder(ForgetPassActivity.this).create();
                LayoutInflater inflater = ForgetPassActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.successfull2, null);
                dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBuilder.setCancelable(false);

                TextView txt = dialogView.findViewById(R.id.txt);

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();

                txt.setText("Password Changed Succesfully");



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialogBuilder.dismiss();




                        Intent intent2 = new Intent( ForgetPassActivity.this,SignInActivity.class);
                        startActivity(intent2);
                        finish();

                    }
                }, 2000);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbardail.dismiss();
                Toast.makeText(ForgetPassActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> hm = new HashMap<String, String>();

                hm.put("op",newpass);
                hm.put("o", sforgetoutletpass);


                return hm;


            }

        };


        rq.add(sr);




    }

    public  void progessbarshowmethod()
    {
        progressbardail = new AlertDialog.Builder(ForgetPassActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
        progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressbardail.setCancelable(false);

        progressbardail.setView(dialogView);




    }





}