package com.sgs.usmansh.schmngsys;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;

public class adminProfile extends AppCompatActivity {

    ProgressDialog mProgress;
    TextView admin_email;
    EditText admin_pass;
    DatabaseReference admininfo,adminemail,adminpass;
    Button change_pass,cancel_cahnge;
    FirebaseUser user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading data..!");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();



        change_pass = (Button)findViewById(R.id.ad_change_pass);
        cancel_cahnge = (Button)findViewById(R.id.ad_pas_cancel);

        admin_email = (TextView)findViewById(R.id.adminemail);
        admin_pass  = (EditText) findViewById(R.id.adminpass);

        admininfo = FirebaseDatabase.getInstance().getReference("Admin");

        admininfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            admin_email.setText(dataSnapshot.child("email").getValue().toString());
            admin_pass.setText(dataSnapshot.child("password").getValue().toString());
            mProgress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         user = FirebaseAuth.getInstance().getCurrentUser();

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.updatePassword(admin_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            admininfo.child("password").setValue(admin_pass.getText().toString());
                            Toast.makeText(adminProfile.this, "Password has changed", Toast.LENGTH_SHORT).show();
                            Intent gotoPanel = new Intent(getApplicationContext(),AdminPanel.class);
                            gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoPanel);
                            finish();
                        }else{
                            Toast.makeText(adminProfile.this, "Password is not changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        cancel_cahnge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoPanel = new Intent(getApplicationContext(),AdminPanel.class);
                gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoPanel);
                finish();

            }
        });
    }
}
