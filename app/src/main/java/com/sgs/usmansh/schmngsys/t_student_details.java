package com.sgs.usmansh.schmngsys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class t_student_details extends AppCompatActivity {

    TextView t_stu_name,t_stu_class,t_stu_id;
    EditText s_comment;
    Button s_submit_comment,s_cancel_data;
    Spinner markstype_spinner;
    DatabaseReference Enter_stu_class_data;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_student_details);


        t_stu_name  = (TextView)findViewById(R.id.t_stu_name);
        t_stu_class = (TextView)findViewById(R.id.t_stu_class);
        t_stu_id    = (TextView)findViewById(R.id.t_stu_id);

        t_stu_name.setText(t_stu_name.getText().toString()+getIntent().getStringExtra("sname"));
        t_stu_class.setText(t_stu_class.getText().toString()+getIntent().getStringExtra("sclass"));
        t_stu_id.setText(t_stu_id.getText().toString()+getIntent().getStringExtra("sregid"));


        s_comment        = (EditText)findViewById(R.id.s_comment);
        s_submit_comment = (Button)findViewById(R.id.s_submit_comment);
        s_cancel_data    = (Button)findViewById(R.id.s_cancel_data);


        s_cancel_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent go_stu_list = new Intent(getApplicationContext(),Comment_class_list.class);
                go_stu_list.putExtra("tClass",getIntent().getStringExtra("tClass"));
                go_stu_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go_stu_list);
                finish();*/
            }
        });


        //Attaching database
        Enter_stu_class_data = FirebaseDatabase.getInstance().getReference("Classes").child(getIntent().getStringExtra("sclass"))
                .child(getIntent().getStringExtra("sregid"));


        final String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        s_submit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Enter_stu_class_data.child("name").setValue(getIntent().getStringExtra("sname"));
                Enter_stu_class_data.child("comment").setValue(mydate+"\n\n"+s_comment.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(t_student_details.this, "Comment has submitted", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(t_student_details.this, "error in comment", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
