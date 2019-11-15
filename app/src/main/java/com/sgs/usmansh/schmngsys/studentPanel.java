package com.sgs.usmansh.schmngsys;

import android.app.ProgressDialog;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class studentPanel extends AppCompatActivity {



    ProgressDialog mProgress;
    Button dairy_bt,comment_bt,fine_bt,marks_bt,profile_bt,stu_fb_bt,stu_SOUT,stu_NB;
    DatabaseReference setlogout_stu,stu_object;
    FirebaseAuth stuAuth;
    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);


        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading data..!");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        stu_NB        = (Button)findViewById(R.id.stu_NB);
        stu_SOUT      = (Button)findViewById(R.id.stu_menu);
        dairy_bt      = (Button)findViewById(R.id.stu_dairy);
        comment_bt    = (Button)findViewById(R.id.stu_comment);
        fine_bt       = (Button)findViewById(R.id.stu_fine);
        marks_bt      = (Button)findViewById(R.id.stu_marks);
        profile_bt    = (Button)findViewById(R.id.stu_profile);
        stu_fb_bt     = (Button)findViewById(R.id.stu_fb);

        stu_object = FirebaseDatabase.getInstance().getReference("StuObject");
        setlogout_stu = FirebaseDatabase.getInstance().getReference("LoginStatus");
        stuAuth = FirebaseAuth.getInstance();





        String email = stuAuth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        final String stu_id = parts[0]; // 004


        stu_object.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                student = dataSnapshot.child(stu_id).getValue(Student.class);
                if(student != null){
                    mProgress.dismiss();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        stu_NB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showNB = new Intent(getApplicationContext(),showNotice.class);
                showNB.putExtra("noticeType","All");
                startActivity(showNB);

            }
        });

        dairy_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent showDairy = new Intent(getApplicationContext(),stu_Diary.class);
                showDairy.putExtra("sclass",student.getSclass());
                showDairy.putExtra("sregid",student.getSregNo());
                startActivity(showDairy);
            }
        });


        comment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stuComment = new Intent(getApplicationContext(),stuComment.class);
                stuComment.putExtra("sclass",student.getSclass());
                stuComment.putExtra("sregid",student.getSregNo());
                startActivity(stuComment);
            }
        });

        fine_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stuFine = new Intent(getApplicationContext(),stuFine.class);
                stuFine.putExtra("sclass",student.getSclass());
                stuFine.putExtra("sregid",student.getSregNo());
                startActivity(stuFine);
            }
        });


        marks_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent marks_details = new Intent(getApplicationContext(),marksDetails.class);
                marks_details.putExtra("sclass",student.getSclass());
                marks_details.putExtra("sregid",student.getSregNo());
                startActivity(marks_details);
            }
        });



        profile_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gostuProf = new Intent(getApplicationContext(),stuProfile.class);
                gostuProf.putExtra("sID",student.getSregNo());
                startActivity(gostuProf);
            }
        });

        stu_fb_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(studentPanel.this, "fb", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/Suffah24/?ref=br_rs"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/Suffah24/?ref=br_rs")));
                }
            }
        });



        stu_SOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setlogout_stu.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("offline");
                stuAuth.signOut();
                Intent gotoMainLogin = new Intent(getApplicationContext(),MainLoginAct.class);
                gotoMainLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoMainLogin);
                finish();


            }
        });


    }




}
