package com.sgs.usmansh.schmngsys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showNotice extends AppCompatActivity {

    TextView sh_notice;
    DatabaseReference getNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notice);

        sh_notice = (TextView)findViewById(R.id.sh_notice);



        getNotice = FirebaseDatabase.getInstance().getReference("NoticeBoard").child(getIntent().getStringExtra("noticeType"));
        getNotice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String noticeValue = dataSnapshot.getValue(String.class);
                if(noticeValue!=null){
                    sh_notice.setText(noticeValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
