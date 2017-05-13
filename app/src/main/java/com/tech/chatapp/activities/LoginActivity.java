package com.tech.chatapp.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tech.chatapp.R;
import com.tech.chatapp.global.AppDialog;
import com.tech.chatapp.global.Global;
import com.tech.chatapp.model.UserDetails;
import com.tech.chatapp.util.AnimUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static android.os.Build.VERSION_CODES.M;

public class LoginActivity extends AppCompatActivity {

    private android.widget.EditText etEmail;
    private android.support.design.widget.TextInputLayout tilEmail;
    private android.widget.EditText etPassword;
    private android.support.design.widget.TextInputLayout tilPass;
    private android.widget.Button btnLogin;
    private android.widget.Button btnRegister;
    private android.widget.LinearLayout llLR;
    private static final String TAG = "Login Screen";

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //For Animation
        AnimUtils.activitySlideUpAnim(LoginActivity.this);

        //Check Permission Here
        Global.checkPermission(LoginActivity.this);

        //get current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @RequiresApi(api = M)
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
        // [END auth_state_listener]
        this.llLR = (LinearLayout) findViewById(R.id.llLR);
        this.btnRegister = (Button) findViewById(R.id.btnRegister);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.tilPass = (TextInputLayout) findViewById(R.id.tilPass);
        this.etPassword = (EditText) findViewById(R.id.etPassword);
        this.tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        this.etEmail = (EditText) findViewById(R.id.etEmail);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });
        etEmail.setText("arbaz6794@gmail.com");
        etPassword.setText("123456");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });
    }


    //For User Login
    private void userLogin() {
        final String emailStr = etEmail.getText().toString();
        final String passwordStr = etPassword.getText().toString();
        if (!TextUtils.isEmpty(emailStr)) {
            if (!TextUtils.isEmpty(passwordStr)) {
                AppDialog.showProgressDialog(this);
                mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            AppDialog.dismissProgressDialog();
                            AppDialog.showAlertDialog(LoginActivity.this, null, task.getException().getMessage(), getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            AppDialog.dismissProgressDialog();
                            Toast.makeText(LoginActivity.this, "Login Success !", Toast.LENGTH_LONG).show();
                            String url = "https://fir-testapp-211a5.firebaseio.com/users.json";
                            try {
                                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        if (s.equals("null")) {
                                            //user not found1
                                            Toast.makeText(LoginActivity.this, getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                                        } else {
                                            try {
                                                JSONObject obj = new JSONObject(s);
                                                String tempEmailStr = emailStr.replace(".", "").replace("_", "");
                                                String tempEmail[] = tempEmailStr.split("@");
                                                String UserName = tempEmail[0];
                                                if (!obj.has(UserName)) {
                                                    // "user not found2"
                                                    Toast.makeText(LoginActivity.this, getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                                                } else if (obj.getJSONObject(UserName).getString("password").equals(passwordStr)) {
                                                    UserDetails.username = UserName;
                                                    UserDetails.password = passwordStr;
                                                    startActivity(new Intent(LoginActivity.this, UsersListActivity.class));
                                                    //For Animation
                                                    AnimUtils.activityenterAnim(LoginActivity.this);
                                                    finish();

                                                } else {
                                                    Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        AppDialog.dismissProgressDialog();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        System.out.println("" + volleyError);
                                        AppDialog.dismissProgressDialog();
                                    }
                                });
                                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                                rQueue.add(request);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    }
                });
            } else {

                tilPass.setError(getString(R.string.pass_validation));
            }
        } else {
            tilEmail.setError(getString(R.string.email_validation));
        }
    }

    //For User Registration
    private void userRegister() {
        final String emailStrR = etEmail.getText().toString();
        final String passwordStrR = etPassword.getText().toString();
        if (!TextUtils.isEmpty(emailStrR)) {
            if (!TextUtils.isEmpty(passwordStrR)) {
                AppDialog.showProgressDialog(this);
                mAuth.createUserWithEmailAndPassword(emailStrR, passwordStrR).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            AppDialog.dismissProgressDialog();
                            AppDialog.showAlertDialog(LoginActivity.this, null, task.getException().getMessage(), getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            AppDialog.dismissProgressDialog();
                            Toast.makeText(LoginActivity.this, "Registration Success !", Toast.LENGTH_LONG).show();
                            try {
                                String url = "https://fir-testapp-211a5.firebaseio.com/users.json";
                                String tempEmailStr = emailStrR.replace(".", "").replace("_", "");
                                String tempEmail[] = tempEmailStr.split("@");
                                final String UserNameR = tempEmail[0];

                                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        Firebase reference = new Firebase("https://fir-testapp-211a5.firebaseio.com/users");
                                        if (s.equals("null")) {
                                            reference.child(UserNameR).child("password").setValue(passwordStrR);
                                        } else {
                                            try {
                                                JSONObject obj = new JSONObject(s);

                                                if (!obj.has(UserNameR)) {
                                                    reference.child(UserNameR).child("password").setValue(passwordStrR);
                                                    Toast.makeText(LoginActivity.this, "registration successful", Toast.LENGTH_LONG).show();



                                                } else {
                                                    Toast.makeText(LoginActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        AppDialog.dismissProgressDialog();
                                    }

                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        System.out.println("" + volleyError);
                                        AppDialog.dismissProgressDialog();
                                    }
                                });

                                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                                rQueue.add(request);

                            } catch (Exception e) {

                            }

                        }

                    }
                });
            } else {
                tilPass.setError(getString(R.string.pass_validation));
            }
        } else {
            tilEmail.setError(getString(R.string.email_validation));
        }
    }

    private void showOverLay() {

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Dialog dialog = new Dialog(this, 0);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.overlay_view);

        RelativeLayout layout = (RelativeLayout) dialog.findViewById(R.id.overlayLayout);

        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LoginActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                dialog.dismiss();

            }

        });

        dialog.show();

    }

}
