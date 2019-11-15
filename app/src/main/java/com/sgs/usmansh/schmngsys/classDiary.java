package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.ArrayList;
import java.util.Calendar;

public class classDiary extends AppCompatActivity {

    EditText s_diary;
    Button submit_diary,cancel_diary;
    DatabaseReference enter_Diary,getting_class_students;
    ArrayList<Student> all_student = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_diary);

        s_diary = (EditText)findViewById(R.id.class_diary);
        submit_diary = (Button)findViewById(R.id.s_submit_dairy);
        cancel_diary = (Button)findViewById(R.id.s_cancel_dairy);


        enter_Diary = FirebaseDatabase.getInstance().getReference("Classes").child(getIntent().getStringExtra("tClass"));

        getting_class_students = FirebaseDatabase.getInstance().getReference("StuObject");
        getting_class_students.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postData:dataSnapshot.getChildren()){

                    Student select_student = postData.getValue(Student.class);

                    if(select_student.getSclass().equals(getIntent().getStringExtra("tClass"))){

                        all_student.add(select_student);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        submit_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(int i =0 ; i<all_student.size();i++){

                    Student student = all_student.get(i);
                    enter_Diary.child(student.getSregNo()).child("diary").setValue(mydate+"\n\n"+s_diary.getText().toString());

                }
                Toast.makeText(classDiary.this, "Diary has Submitted", Toast.LENGTH_SHORT).show();

            }
        });

        cancel_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoStaffpanel = new Intent(getApplicationContext(),staffPanel.class);
                gotoStaffpanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoStaffpanel);
                finish();

            }
        });

    }
}
