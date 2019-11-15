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

public class staffProfile extends AppCompatActivity {

    TextView staff_t_name,staff_t_id,staff_t_class;
    EditText staff_t_pass;
    DatabaseReference teacher_object;
    Teacher t_data;
    Button change_pass,cancel_cahnge;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading data..!");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        change_pass = (Button)findViewById(R.id.staff_change_pass);
        cancel_cahnge = (Button)findViewById(R.id.staff_not_change);

        staff_t_name = (TextView)findViewById(R.id.staff_t_name);
        staff_t_class = (TextView)findViewById(R.id.staff_t_class);
        staff_t_id = (TextView)findViewById(R.id.staff_t_id);
        staff_t_pass = (EditText) findViewById(R.id.staff_t_pass);

        final String teacher_id = getIntent().getStringExtra("tID");

        teacher_object = FirebaseDatabase.getInstance().getReference("TechObject");
        teacher_object.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                t_data = dataSnapshot.child(teacher_id).getValue(Teacher.class);
                if(t_data !=null) {

                    mProgress.dismiss();
                    staff_t_name.setText(t_data.getTechname());
                    staff_t_class.setText(t_data.getTechtclass());
                    staff_t_id.setText(t_data.getTechregid() + "@sgsstaff.com");
                    staff_t_pass.setText(t_data.getTechpassword());
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

                user.updatePassword(staff_t_pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            teacher_object.child(teacher_id).child("techpassword").setValue(staff_t_pass.getText().toString());
                            Toast.makeText(staffProfile.this, "Password has changed", Toast.LENGTH_SHORT).show();
                            Intent gotoPanel = new Intent(getApplicationContext(),staffPanel.class);
                            gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoPanel);
                            finish();
                        }else{
                            Toast.makeText(staffProfile.this, "Password is not changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        cancel_cahnge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoPanel = new Intent(getApplicationContext(),staffPanel.class);
                gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoPanel);
                finish();

            }
        });


    }
}
