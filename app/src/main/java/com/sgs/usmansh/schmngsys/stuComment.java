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

public class stuComment extends AppCompatActivity {

    TextView sh_comment;
    DatabaseReference comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_comment);

        sh_comment= (TextView)findViewById(R.id.sh_comment);


        comment = FirebaseDatabase.getInstance().getReference("Classes").child(getIntent().getStringExtra("sclass"))
                .child(getIntent().getStringExtra("sregid")).child("comment");

        comment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String coment  = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(coment)) {
                    sh_comment.setText(coment);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
