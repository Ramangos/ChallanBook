package com.example.challanbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.tasks.TaskExecutors;
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

public class OtpActivity extends AppCompatActivity {

    //private static final Object Context = 123;
    private String mVerificationId;

    //The edittext to input the code
    private EditText editTextCode ;
    FirebaseAuth mAuth;
    Button submitotp,signout;
    String fotp ,otp;

    String soutletname,soutletadd,soutlet,soutletpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();

        editTextCode = findViewById(R.id.otpedittext);




        submitotp=findViewById(R.id.submitotp);


        //getting mobile number from the previous activity
        //and sending the verification code to the number


        Intent intent = getIntent();
        soutletname = intent.getStringExtra("outletname");
        soutletadd = intent.getStringExtra("outletadd");
        soutlet = intent.getStringExtra("outlet");
        soutletpass = intent.getStringExtra("outletpass");

        String mobile = "+91" + soutlet;

       // sendVerificationCode(mobile);




        //if the automatic sms detection did not work, user can also enter the code manually
        //so adding a click listener to the button
        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               otp = editTextCode.getText().toString().trim();

               if(!TextUtils.isEmpty(otp))
               {

                   PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otp );
                   signInWithPhoneCredential(credential);

               }

               else
               {
                   Toast.makeText(OtpActivity.this, "input otp", Toast.LENGTH_SHORT).show();
               }



            }





        });




    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
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

                                Toast.makeText(OtpActivity.this, "code sent "+ s, Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OtpActivity.this, "ver failed "+ e.getMessage(), Toast.LENGTH_SHORT).show();






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
                           // signupOutlet();

                            Toast.makeText(OtpActivity.this, " success", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(OtpActivity.this, "failed ", Toast.LENGTH_SHORT).show();

                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(OtpActivity.this, "Invalid code entered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


    }


    public  void  signupOutlet()
    {

        RequestQueue rq = Volley.newRequestQueue(OtpActivity.this );
        String url = "https://strigiform-attorney.000webhostapp.com/billing/signupoutlet.php";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(OtpActivity.this, "okkkk " +response, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(OtpActivity.this, "success", Toast.LENGTH_SHORT).show();






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OtpActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> hm = new HashMap<String, String>();


                hm.put("outletname", soutletname);
                hm.put("outletadd", soutletadd);
                hm.put("outlet", soutlet);
                hm.put("outletpass", soutletpass);
                hm.put("lastbill","0");



                return hm;


            }

        };


        rq.add(sr);




    }



}