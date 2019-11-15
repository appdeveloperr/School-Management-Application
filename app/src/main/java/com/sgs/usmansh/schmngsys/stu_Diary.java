package com.sgs.usmansh.schmngsys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class stu_Diary extends AppCompatActivity {

    DatabaseReference dairy;
    TextView sh_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu__diary);



        sh_diary = (TextView)findViewById(R.id.sh_diary);


        dairy = FirebaseDatabase.getInstance().getReference("Classes").child(getIntent().getStringExtra("sclass"))
                .child(getIntent().getStringExtra("sregid")).child("diary");


        dairy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String diary  = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(diary)) {
                    sh_diary.setText(diary);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
