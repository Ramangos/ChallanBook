  package com.example.challanbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

  public class SignupActivity extends AppCompatActivity {

      EditText outletname,outletadd,outlet,outletpass;
      String soutletname,soutletadd,soutlet,soutletpass;
      Button signup;
      private String mVerificationId;
      EditText editTextCode;
      FirebaseAuth mAuth;
      String fotp ,otp;
      Button submitotp;
      TextView timerr,resendotp;
      BottomSheetDialog bottomSheetDialog;
      ProgressBar otpProgressBar;
      CountDownTimer timer;
      TextView closebsheet;
      AlertDialog progressbardail;
      String mobile;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        outletname=findViewById(R.id.outletname);
        outletadd=findViewById(R.id.outletadd);
        outlet=findViewById(R.id.outlet);
        outletpass=findViewById(R.id.outletpass);
        signup=findViewById(R.id.signup);


        progessbarshowmethod();







        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soutletname= outletname.getText().toString();
                soutletadd= outletadd.getText().toString();
                soutlet= outlet.getText().toString();
                soutletpass= outletpass.getText().toString();

                //signupOutlet();

              if(soutlet.isEmpty() || soutlet.length() < 10){
                    outlet.setError("Enter a valid mobile");
                    outlet.requestFocus();

                }

              else if(soutletpass.trim().isEmpty() )
              {
                  outletpass.setError("Please enter password");
                  outletpass.requestFocus();
              }

                else
                {
                  /* Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                    intent.putExtra("outletname", soutletname);
                    intent.putExtra("outletadd", soutletadd);
                    intent.putExtra("outlet", soutlet);
                    intent.putExtra("outletpass", soutletpass);
                    startActivity(intent);
                    Toast.makeText(SignupActivity.this, "otp send", Toast.LENGTH_SHORT).show();*/

                    checkdata();





                }

            }
        });


    }

      //the method is sending verification code
      //the country id is concatenated
      //you can take the country id as user input as well







      public  void checkdata()
      {





                String url = "https://strigiform-attorney.000webhostapp.com/billing/checkDuplicateData.php";
                StringRequest sr = new StringRequest(1 ,url, new Response.Listener<String>() {

              @Override
              public void onResponse(String response) {

                 //   response = response.trim();


                  if(response.contentEquals("dupli"))
                  {




                      outlet.setError("Phone No Already registered");
                      outlet.requestFocus();



                      Snackbar snackbar = Snackbar
                              .make( signup, "Phone No Already registered", Snackbar.LENGTH_LONG);
                      snackbar.setAction("Forget Password", new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                              Intent intent = new Intent(SignupActivity.this,SignInActivity.class);
                              startActivity(intent);

                          }
                      });

                      snackbar.show();
                  }


                  else if(response.contentEquals("ok"))
                  {
                      String mobile = "+91" + soutlet;

                      sendVerificationCode(mobile);


                      bsheet();








                  }

                  else
                  {
                      Toast.makeText(SignupActivity.this, "Something went wrong \n"+ response, Toast.LENGTH_SHORT).show();



                  }


              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Toast.makeText(SignupActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

              }
          }) {

              @Override
              protected Map<String, String> getParams() throws AuthFailureError {





                  Map<String, String> hm = new HashMap<String, String>();




                  //hm.put("params", json.toString());
                  hm.put("o", soutlet);




                  return hm;
              }

          };
          RequestQueue rq = Volley.newRequestQueue(SignupActivity.this);
          rq.add(sr);


      }




      public void bsheet()
      {

          bottomSheetDialog = new BottomSheetDialog(SignupActivity.this,R.style.Bottomsheetdialtheme);
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

          numdisplay.setText(soutlet);

          resendotp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  resendotp.setVisibility(View.GONE);
                  otpProgressBar.setVisibility(View.VISIBLE);
                  editTextCode.setText("");

                  String mobile = "+91" + soutlet;

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
                  whapmethod(v);
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

                  }

                  else
                  {
                      Toast.makeText(SignupActivity.this, "input otp", Toast.LENGTH_SHORT).show();
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

                                  Toast.makeText(SignupActivity.this, "code sent ", Toast.LENGTH_SHORT).show();



                                  timer = new CountDownTimer(30000, 1000) {

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

                                  Toast.makeText(SignupActivity.this, "ver failed "+ e.getMessage(), Toast.LENGTH_SHORT).show();






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

                              signupOutlet();

                          }
                          else
                          {

                              progressbardail.dismiss();

                              if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                              {
                                  Toast.makeText(SignupActivity.this, "Invalid code entered", Toast.LENGTH_SHORT).show();
                              }
                              else
                              {
                                  Toast.makeText(SignupActivity.this, "failed ", Toast.LENGTH_SHORT).show();
                              }
                          }
                      }
                  });


      }



      public  void  signupOutlet()
      {

          RequestQueue rq = Volley.newRequestQueue(SignupActivity.this );
          String url = "https://strigiform-attorney.000webhostapp.com/billing/signupoutlet.php";
          StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {

                  if(response.contentEquals("dupli"))
                  {
                      progressbardail.dismiss();


                      Snackbar snackbar = Snackbar
                              .make( signup, "Phone No Already registered", Snackbar.LENGTH_LONG);
                      snackbar.setAction("Forget Password", new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                            Intent intent = new Intent(SignupActivity.this,SignInActivity.class);
                            startActivity(intent);



                          }
                      });

                      snackbar.show();
                  }


                 else if(response.contentEquals("done"))
                  {

                      progressbardail.dismiss();
                      bottomSheetDialog.dismiss();

                      final AlertDialog dialogBuilder = new AlertDialog.Builder(SignupActivity.this).create();
                      LayoutInflater inflater = SignupActivity.this.getLayoutInflater();
                      View dialogView = inflater.inflate(R.layout.successfull2, null);
                      dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                      dialogBuilder.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                      dialogBuilder.setCancelable(false);
                      dialogBuilder.setView(dialogView);
                      dialogBuilder.show();

                      Snackbar snackbar = Snackbar
                              .make( signup, "Account created Succesfully", Snackbar.LENGTH_LONG);

                      snackbar.show();

                      Toast.makeText(SignupActivity.this, "Account created Succesfully", Toast.LENGTH_SHORT).show();

                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {

                              dialogBuilder.dismiss();
                              Intent intent = new Intent(SignupActivity.this,SignInActivity.class);
                             startActivity(intent);
                              finish();


                          }
                      },3000);






                  }

                  else
                  {
                      Toast.makeText(SignupActivity.this, "Something went wrong \n"+ response, Toast.LENGTH_SHORT).show();

                  }






                





              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Toast.makeText(SignupActivity.this, "NOt success" + error.toString(), Toast.LENGTH_SHORT).show();

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

      public void whapmethod(final View view)
      {

          boolean installed = appInstalledOrNot("com.whatsapp");

          if(installed)
          {
              Intent intent = new Intent(Intent.ACTION_VIEW);


              intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+917860959600&text=i am having trouble logging into the app"));
              startActivity(intent);
          }
          if(!installed)
          {
              Toast.makeText(SignupActivity.this, "Application not available signup", Toast.LENGTH_SHORT).show();
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



      public  void progessbarshowmethod()
      {
          progressbardail = new AlertDialog.Builder(SignupActivity.this).create();
          LayoutInflater inflater = this.getLayoutInflater();
          View dialogView = inflater.inflate(R.layout.progressbar_layout, null);
          progressbardail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
          progressbardail.setCancelable(false);

          progressbardail.setView(dialogView);




      }







  }