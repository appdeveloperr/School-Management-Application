package com.sgs.usmansh.schmngsys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;

public class enterStudentFine extends AppCompatActivity {

    TextView d_stu_name,d_stu_class,d_stu_id,
            d_absent_fine,d_late_fine,d_uniform_fine,d_stu_password;

    Button absent_bt,late_bt,uniform_bt,cancel_bt,d_stuMarks_bt,d_stucooment_bt;
    DatabaseReference fine_Ref;
    DatabaseReference Afine,Lfine,Ufine;
    String adminKey;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_student_fine);



        absent_bt       = (Button)findViewById(R.id.absent_bt);
        late_bt         = (Button)findViewById(R.id.late_bt);
        uniform_bt      = (Button)findViewById(R.id.uniform_bt);
        //cancel_bt     = (Button)findViewById(R.id.d_cancel);
        d_stuMarks_bt   = (Button)findViewById(R.id.ad_stuMarks);
        d_stucooment_bt = (Button)findViewById(R.id.ad_stucomment);
        d_stu_name      = (TextView)findViewById(R.id.d_stu_name);
        d_stu_class     = (TextView)findViewById(R.id.d_stu_class);
        d_stu_id        = (TextView)findViewById(R.id.d_stu_id);
        d_absent_fine   = (TextView)findViewById(R.id.d_absent_fine1);
        d_late_fine     = (TextView)findViewById(R.id.d_late_fine1);
        d_uniform_fine  = (TextView)findViewById(R.id.d_uniform_fine1);
        d_stu_password  = (TextView)findViewById(R.id.d_stu_pass);


        d_stuMarks_bt.setVisibility(View.INVISIBLE);
        d_stucooment_bt.setVisibility(View.INVISIBLE);


        adminKey = getIntent().getStringExtra("adminKey");
        if(adminKey != null && adminKey.equalsIgnoreCase("admin")){
            d_stuMarks_bt.setVisibility(View.VISIBLE);
            d_stucooment_bt.setVisibility(View.VISIBLE);
        }



        d_stu_name.setText(d_stu_name.getText().toString()+getIntent().getStringExtra("sname"));
        d_stu_class.setText(d_stu_class.getText().toString()+getIntent().getStringExtra("sclass"));
        d_stu_id.setText(d_stu_id.getText().toString()+getIntent().getStringExtra("sregid")+"@sgs.com");
        d_stu_password.setText(d_stu_password.getText().toString()+getIntent().getStringExtra("spass"));


        fine_Ref = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("sregid"));
        Afine = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("sregid")).child("absent");
        Lfine = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("sregid")).child("late");
        Ufine = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("sregid")).child("uniform");


        Afine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String afine  = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(afine)) {
                    d_absent_fine.setText(""+afine);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Lfine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String lfine  = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(lfine)) {
                    d_late_fine.setText(""+lfine);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Ufine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String ufine  = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(ufine)) {
                    d_uniform_fine.setText(""+ufine);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        absent_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ab_fine = Integer.parseInt(d_absent_fine.getText().toString());
                ab_fine = ab_fine+20;
                d_absent_fine.setText(Integer.toString(ab_fine));
                fine_Ref.child("absent").setValue(d_absent_fine.getText().toString());

            }
        });


        late_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lt_fine = Integer.parseInt(d_late_fine.getText().toString());
                lt_fine = lt_fine+10;
                d_late_fine.setText(Integer.toString(lt_fine));
                fine_Ref.child("late").setValue(d_late_fine.getText().toString());

            }
        });


        uniform_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int uni_fine = Integer.parseInt(d_uniform_fine.getText().toString());
                uni_fine = uni_fine+10;
                d_uniform_fine.setText(Integer.toString(uni_fine));
                fine_Ref.child("uniform").setValue(d_uniform_fine.getText().toString());

            }
        });


        d_stuMarks_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent marks_details = new Intent(getApplicationContext(),marksDetails.class);
                marks_details.putExtra("sclass",getIntent().getStringExtra("sclass"));
                marks_details.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(marks_details);

            }
        });


        d_stucooment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go_t_student_detail = new Intent(getApplicationContext(),t_student_details.class);
                go_t_student_detail.putExtra("sname",getIntent().getStringExtra("sname"));
                go_t_student_detail.putExtra("sclass",getIntent().getStringExtra("sclass"));
                go_t_student_detail.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(go_t_student_detail);

            }
        });


        /*cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent st_cls_list = new Intent(getApplicationContext(),st_cls_list.class);
                st_cls_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                st_cls_list.putExtra("classkey",getIntent().getStringExtra("sclass"));
                startActivity(st_cls_list);
                finish();
            }
        });*/



    }
}
