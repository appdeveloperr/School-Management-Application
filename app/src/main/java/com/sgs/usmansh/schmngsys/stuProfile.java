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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class stuProfile extends AppCompatActivity {

    TextView stu_name,stu_class,stu_regid;
    EditText stu_password;
    DatabaseReference stu_object;
    Student student;
    ProgressDialog mProgress;
    Button change_pass,cancel_cahnge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_profile);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading data..!");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();


        change_pass = (Button)findViewById(R.id.stu_change_pass);
        cancel_cahnge = (Button)findViewById(R.id.stu_not_change);

        stu_name      = (TextView)findViewById(R.id.stu_name_tv);
        stu_class     = (TextView)findViewById(R.id.stu_class_tv);
        stu_regid     = (TextView)findViewById(R.id.stu_regid_tv);
        stu_password  = (EditText) findViewById(R.id.stu_pass_tv);

        stu_object = FirebaseDatabase.getInstance().getReference("StuObject");



        stu_object.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                student = dataSnapshot.child(getIntent().getStringExtra("sID")).getValue(Student.class);
                if(student != null){
                    mProgress.dismiss();
                    stu_name.setText(student.getSname());
                    stu_class.setText(student.getSclass());
                    stu_regid.setText(student.getSregNo()+"@sgs.com");
                    stu_password.setText(student.getSpassword());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.updatePassword(stu_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            stu_object.child(getIntent().getStringExtra("sID")).child("spassword").setValue(stu_password.getText().toString());
                            Toast.makeText(stuProfile.this, "Password has changed", Toast.LENGTH_SHORT).show();
                            Intent gotoPanel = new Intent(getApplicationContext(),studentPanel.class);
                            gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoPanel);
                            finish();
                        }else{
                            Toast.makeText(stuProfile.this, "Password is not changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        cancel_cahnge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoPanel = new Intent(getApplicationContext(),studentPanel.class);
                gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoPanel);
                finish();

            }
        });




    }
}
