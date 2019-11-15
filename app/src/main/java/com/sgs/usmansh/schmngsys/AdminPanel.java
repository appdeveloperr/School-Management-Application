package com.sgs.usmansh.schmngsys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.github.rubensousa.raiflatbutton.RaiflatImageButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.function.ToLongBiFunction;

public class AdminPanel extends AppCompatActivity {

    Button addUser,userList,Stulist,AssignSubject,admin_fb,admin_profile,ad_signout,ad_NB;
    FirebaseAuth mAuth;
    DatabaseReference setlogout_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);




        mAuth = FirebaseAuth.getInstance();
        setlogout_admin = FirebaseDatabase.getInstance().getReference("LoginStatus");

        ad_NB         = (Button)findViewById(R.id.ad_NB);
        addUser       = (Button)findViewById(R.id.addUser);
        userList      = (Button)findViewById(R.id.userList);
        Stulist       = (Button)findViewById(R.id.Stulist);
        AssignSubject = (Button)findViewById(R.id.assignSub);
        admin_fb      = (Button)findViewById(R.id.admin_fb);
        admin_profile = (Button)findViewById(R.id.admin_profile);
        ad_signout    = (Button)findViewById(R.id.ad_signout);


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent go = new Intent(getApplicationContext(),AddUserForum.class);
                startActivity(go);
            }
        });


        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent go = new Intent(getApplicationContext(),UserListAct.class);

                startActivity(go);


            }
        });


        Stulist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goadminclasses = new Intent(getApplicationContext(),staffClassPanel.class);
                goadminclasses.putExtra("adminKey","admin");
                startActivity(goadminclasses);


            }
        });



        AssignSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent goSub = new Intent(getApplicationContext(),AssignSubjects.class);
                startActivity(goSub);
            }
        });


        admin_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goadminPro = new Intent(getApplicationContext(),adminProfile.class);
                startActivity(goadminPro);
            }
        });


        admin_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminPanel.this, "fb", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/Suffah24/?ref=br_rs"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/Suffah24/?ref=br_rs")));
                }
            }
        });


        ad_NB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goNoticeBoard = new Intent(getApplicationContext(),noticeBoard.class);
                startActivity(goNoticeBoard);

            }
        });

        ad_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                setlogout_admin.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("offline");
                mAuth.signOut();
                Intent gotoMainLogin = new Intent(getApplicationContext(),MainLoginAct.class);
                gotoMainLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoMainLogin);
                finish();


            }
        });


    }





}

