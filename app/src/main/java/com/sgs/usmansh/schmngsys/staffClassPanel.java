package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class staffClassPanel extends AppCompatActivity {

    Button st_cl1,st_cl2,st_cl3,st_cl4,st_cl5,st_cl6,st_cl7,st_cl8,st_cl9,st_cl10,des_SOUT;
    FirebaseAuth mAuth_decipline;
    String adminKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_class_panel);

        st_cl1   = (Button)findViewById(R.id.st_cl1);
        st_cl2   = (Button)findViewById(R.id.st_cl2);
        st_cl3   = (Button)findViewById(R.id.st_cl3);
        st_cl4   = (Button)findViewById(R.id.st_cl4);
        st_cl5   = (Button)findViewById(R.id.st_cl5);
        st_cl6   = (Button)findViewById(R.id.st_cl6);
        st_cl7   = (Button)findViewById(R.id.st_cl7);
        st_cl8   = (Button)findViewById(R.id.st_cl8);
        st_cl9   = (Button)findViewById(R.id.st_cl9);
        st_cl10  = (Button)findViewById(R.id.st_cl10);
        des_SOUT = (Button)findViewById(R.id.des_out);


        mAuth_decipline = FirebaseAuth.getInstance();

      adminKey = getIntent().getStringExtra("adminKey");

        if(adminKey != null && adminKey.equalsIgnoreCase("admin")){
            des_SOUT.setVisibility(View.INVISIBLE);
        }


        st_cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 1");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });

        st_cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 2");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });
        st_cl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 3");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });
        st_cl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 4");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);


            }
        });
        st_cl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 5");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });
        st_cl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 6");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });
        st_cl7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 7");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });
        st_cl8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 8");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });
        st_cl9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 9");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });
        st_cl10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.putExtra("classkey","Class 10");
                st_cls_list.putExtra("adminKey",adminKey);
                startActivity(st_cls_list);

            }
        });


        des_SOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth_decipline.signOut();
                Intent gotoMainLogin = new Intent(getApplicationContext(),MainLoginAct.class);
                gotoMainLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoMainLogin);
                finish();
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();

        adminKey = getIntent().getStringExtra("adminKey");
    }
}
