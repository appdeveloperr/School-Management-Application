package com.sgs.usmansh.schmngsys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.github.rubensousa.raiflatbutton.RaiflatImageButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class staffPanel extends AppCompatActivity {

    TextView staff_t_name,staff_t_id,staff_t_class,staff_t_pass;
    ProgressDialog mProgress;
    Button s_list_bt,staff_s_diary,staff_s_marks,staff_s_NB,staff_s_profile,staff_s_fb,staff_SOUT,staff_ttable;
    FirebaseAuth mAuth;
    DatabaseReference setlogout_staff,teacher_object;
    Teacher t_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_panel);


        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading data..!");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        staff_t_name  = (TextView)findViewById(R.id.staff_t_name);
        staff_t_class = (TextView)findViewById(R.id.staff_t_class);
        staff_t_id    = (TextView)findViewById(R.id.staff_t_id);
        staff_t_pass  = (TextView)findViewById(R.id.staff_t_pass);


        setlogout_staff = FirebaseDatabase.getInstance().getReference("LoginStatus");

        teacher_object = FirebaseDatabase.getInstance().getReference("TechObject");

        mAuth = FirebaseAuth.getInstance();

        Toast.makeText(this, "Login email : "+mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();


        String email = mAuth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        final String teacher_id = parts[0]; // 004

//Fetching teacher object data
        teacher_object.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                t_data = dataSnapshot.child(teacher_id).getValue(Teacher.class);
                if(t_data != null){
                    mProgress.dismiss();
                   }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        s_list_bt = (Button)findViewById(R.id.staff_s_list);
        s_list_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent go_T_class_list = new Intent(getApplicationContext(),TeacherClassList.class);
                go_T_class_list.putExtra("tClass",t_data.getTechtclass());
                startActivity(go_T_class_list);
            }
        });

        staff_s_diary = (Button)findViewById(R.id.staff_s_diary);
        staff_s_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDiary = new Intent(getApplicationContext(),classDiary.class);
                goDiary.putExtra("tClass",t_data.getTechtclass());
                startActivity(goDiary);
            }
        });

        staff_s_marks = (Button)findViewById(R.id.staff_s_marks);
        staff_s_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent go_enter_marks = new Intent(getApplicationContext(),Mark_class_list.class);
                go_enter_marks.putExtra("tClass",t_data.getTechtclass());
                startActivity(go_enter_marks);
            }
        });

        staff_s_NB = (Button)findViewById(R.id.staff_s_NB);
        staff_s_NB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PopupMenu popup = new PopupMenu(staffPanel.this, staff_s_NB);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menubar, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Student notice")){

                            Intent showNB = new Intent(getApplicationContext(),showNotice.class);
                            showNB.putExtra("noticeType","All");
                            startActivity(showNB);

                        }else if(item.getTitle().toString().equalsIgnoreCase("Teacher notice")){
                            Intent showNB = new Intent(getApplicationContext(),showNotice.class);
                            showNB.putExtra("noticeType","Teacher");
                            startActivity(showNB);


                        }

                        return true;
                    }
                });

                popup.show(); //showing popup menu

            }
        });

        staff_s_fb = (Button)findViewById(R.id.staff_s_fb);
        staff_s_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(staffPanel.this, "fb", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/Suffah24/?ref=br_rs"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/Suffah24/?ref=br_rs")));
                }
            }
        });


        staff_s_profile = (Button)findViewById(R.id.staff_s_profile);
        staff_s_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent profilesetting = new Intent(getApplicationContext(),staffProfile.class);
                profilesetting.putExtra("tID",teacher_id);
                startActivity(profilesetting);
            }
        });


        staff_SOUT = (Button)findViewById(R.id.st_menu);
        staff_SOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //setlogout_staff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("offline");
                mAuth.signOut();
                Intent gotoMainLogin = new Intent(getApplicationContext(),MainLoginAct.class);
                gotoMainLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoMainLogin);
                finish();

            }
        });


        staff_ttable = (Button)findViewById(R.id.staff_ttable);
        staff_ttable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent showSubDetails = new Intent(getApplicationContext(),staff_subDetails.class);
                showSubDetails.putExtra("tID",teacher_id);
                showSubDetails.putExtra("tname",t_data.getTechname());
                startActivity(showSubDetails);

            }
        });


    }



}
