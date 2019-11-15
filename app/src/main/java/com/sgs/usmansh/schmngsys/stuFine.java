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

public class stuFine extends AppCompatActivity {

    TextView sh_Afine,sh_Lfine,sh_Ufine;

    DatabaseReference Afine,Lfine,Ufine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_fine);

        sh_Afine= (TextView)findViewById(R.id.sh_Afine);
        sh_Lfine= (TextView)findViewById(R.id.sh_Lfine);
        sh_Ufine= (TextView)findViewById(R.id.sh_Ufine);



        Afine = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("sregid")).child("absent");
        Lfine = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("sregid")).child("late");
        Ufine = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("sregid")).child("uniform");

        Afine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String afine  = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(afine)) {
                    sh_Afine.setText("Absent fine is: "+afine);
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
                    sh_Lfine.setText("Late fine is: "+lfine);
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
                    sh_Ufine.setText("Uniform fine is: "+ufine);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
