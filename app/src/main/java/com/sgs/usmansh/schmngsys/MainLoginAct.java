package com.sgs.usmansh.schmngsys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainLoginAct extends AppCompatActivity {

    RaiflatButton loginBtn;
    ProgressDialog mProgressDialog;
    FirebaseAuth adminAuth,staffAuth,stuAuth,mAuth;
    DatabaseReference login;
    private EditText loginEmailField;
    private EditText loginPasswordField;
    FirebaseAuth.AuthStateListener adminAuthListner,staffAuthListner,stuAuthListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        login = FirebaseDatabase.getInstance().getReference("LoginStatus");
        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        loginEmailField = (EditText)findViewById(R.id.loginEmailF);
        loginPasswordField = (EditText)findViewById(R.id.loginPasswordF);
        loginBtn = (RaiflatButton)findViewById(R.id.loginbtn);


        adminAuth = FirebaseAuth.getInstance();
        adminAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if(firebaseAuth.getCurrentUser() != null)
                {

                    String checkEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if(checkEmail.contains("@sgsadmin.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),AdminPanel.class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();
                }
                else if(checkEmail.contains("@sgsstaff.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),staffPanel.class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();
                }
                else if (checkEmail.contains("@sgs.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),studentPanel.class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();
                }else if(checkEmail.contains("@sgsdec.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),staffClassPanel .class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();

                }

                }
            }
        };





        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //check login
                checkLogin();

            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();

        //checkLoginState();
        adminAuth.addAuthStateListener(adminAuthListner);
    }

    private void checkLoginState() {


        login.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

          String logincheck = (String) dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue();

            if(logincheck!=null && logincheck.equals("online")){

                String checkEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if(checkEmail.contains("@sgsadmin.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),AdminPanel.class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();
                }
                else if(checkEmail.contains("@sgsstaff.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),staffPanel.class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();
                }
                else if (checkEmail.contains("@sgs.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),studentPanel.class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();
                }else if(checkEmail.contains("@sgsdecipline.com")){
                    Intent gotoPanel = new Intent(getApplicationContext(),DeciplinePanel.class);
                    gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoPanel);
                    finish();

                }

            }


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });

    }

    //Login
    private void checkLogin() {

        String email = loginEmailField.getText().toString().trim();
        String password = loginPasswordField.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProgressDialog.setMessage("Checking Login..!");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if(task.isSuccessful()){

                        if(loginEmailField.getText().toString().contains("@sgsstaff.com")){
                           // login.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("online");
                            Toast.makeText(MainLoginAct.this, "Staff login", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                            Intent gotostaffPanel = new Intent(getApplicationContext(),staffPanel.class);
                            gotostaffPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotostaffPanel);
                            finish();

                        }

                        else if(loginEmailField.getText().toString().contains("@sgsadmin.com")){
                            //login.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("online");
                            Toast.makeText(MainLoginAct.this, "Admin login", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                            Intent gotoadminPanel = new Intent(getApplicationContext(),AdminPanel.class);
                            gotoadminPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoadminPanel);
                            finish();

                        }
                        else if(loginEmailField.getText().toString().contains("@sgs.com")){
                            //login.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("online");
                            Toast.makeText(MainLoginAct.this, "Student login", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                            Intent gotostuPanel = new Intent(getApplicationContext(),studentPanel.class);
                            gotostuPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotostuPanel);
                            finish();

                        }else if(loginEmailField.getText().toString().contains("@sgsdec.com")){
                            //login.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("online");
                            Toast.makeText(MainLoginAct.this, "Decipline login", Toast.LENGTH_SHORT).show();
                            Intent gotoPanel = new Intent(getApplicationContext(),staffClassPanel.class);
                            gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoPanel);
                            finish();

                        }
                        else{
                            Toast.makeText(MainLoginAct.this, "No one logged in", Toast.LENGTH_SHORT).show();
                        }


                        //checkUserExist();


                    }
                    else{

                        mProgressDialog.dismiss();
                        Toast.makeText(MainLoginAct.this, "Sign In Error", Toast.LENGTH_SHORT).show();

                    }


                }
            });

        }

    }




}
