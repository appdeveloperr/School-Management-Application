package com.sgs.usmansh.schmngsys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class subDetails extends AppCompatActivity {

    TextView cl1,cl2,cl3,cl4,cl5,cl6,cl7,cl8;
    TextView sb1,sb2,sb3,sb4,sb5,sb6,sb7,sb8;
    TextView teachername ;
    Button backtotechdetails;
    DatabaseReference assignSubDetail;
    String assigned="";
    Intent tname;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

         tname = getIntent();

        backtotechdetails = (Button)findViewById(R.id.subbackbt);
        teachername = (TextView)findViewById(R.id.subtname);

        cl1 = (TextView)findViewById(R.id.cl1);
        cl2 = (TextView)findViewById(R.id.cl2);
        cl3 = (TextView)findViewById(R.id.cl3);
        cl4 = (TextView)findViewById(R.id.cl4);
        cl5 = (TextView)findViewById(R.id.cl5);
        cl6 = (TextView)findViewById(R.id.cl6);
        cl7 = (TextView)findViewById(R.id.cl7);
        cl8 = (TextView)findViewById(R.id.cl8);

        sb1 = (TextView)findViewById(R.id.sb1);
        sb2 = (TextView)findViewById(R.id.sb2);
        sb3 = (TextView)findViewById(R.id.sb3);
        sb4 = (TextView)findViewById(R.id.sb4);
        sb5 = (TextView)findViewById(R.id.sb5);
        sb6 = (TextView)findViewById(R.id.sb6);
        sb7 = (TextView)findViewById(R.id.sb7);
        sb8 = (TextView)findViewById(R.id.sb8);

        teachername.setText(teachername.getText()+" "+tname.getStringExtra("tname"));



                assignSubDetail = FirebaseDatabase.getInstance().getReference("Subassigned");
                assignSubDetail.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                       assigned = dataSnapshot.child(getIntent().getStringExtra("tID")).getValue().toString();
                        check_SubjectsData(assigned);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





        backtotechdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotoTlist = new Intent(getApplicationContext(),UserListAct.class);
               gotoTlist.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoTlist);
                finish();
            }
        });


    }

    private void check_SubjectsData(String assigned) {



        if(assigned.equals("no")){
            Toast.makeText(this, "Subjects not Assigned yet..!", Toast.LENGTH_SHORT).show();

            cl1.setText("1. Not assigned " );
            cl2.setText("2. Not assigned");
            cl3.setText("3. Not assigned");
            cl4.setText("4. Not assigned");
            cl5.setText("5. Not assigned");
            cl6.setText("6. Not assigned");
            cl7.setText("7. Not assigned");
            cl8.setText("8. Not assigned");



            sb1.setText("Not assigned");
            sb2.setText("Not assigned");
            sb3.setText("Not assigned");
            sb4.setText("Not assigned");
            sb5.setText("Not assigned");
            sb6.setText("Not assigned");
            sb7.setText("Not assigned");
            sb8.setText("Not assigned");



        }
        else{
            Toast.makeText(this, "subjects are Assigned..!", Toast.LENGTH_SHORT).show();



            DatabaseReference clsData = FirebaseDatabase.getInstance().getReference("AssignSubjects").child(getIntent().getStringExtra("tID")).child("Classes");

            clsData.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Classs clss = dataSnapshot.getValue(Classs.class);
                    if(clss!=null) {
                        cl1.setText("1. " + clss.getCls1());
                        cl2.setText("2. " + clss.getCls2());
                        cl3.setText("3. " + clss.getCls3());
                        cl4.setText("4. " + clss.getCls4());
                        cl5.setText("5. " + clss.getCls5());
                        cl6.setText("6. " + clss.getCls6());
                        cl7.setText("7. " + clss.getCls7());
                        cl8.setText("8. " + clss.getCls8());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            DatabaseReference subData = FirebaseDatabase.getInstance().getReference("AssignSubjects").child(getIntent().getStringExtra("tID")).child("Subjects");

            subData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Subjects subjects = dataSnapshot.getValue(Subjects.class);

                    if(subjects !=null) {
                        sb1.setText(subjects.getSub1());
                        sb2.setText(subjects.getSub2());
                        sb3.setText(subjects.getSub3());
                        sb4.setText(subjects.getSub4());
                        sb5.setText(subjects.getSub5());
                        sb6.setText(subjects.getSub6());
                        sb7.setText(subjects.getSub7());
                        sb8.setText(subjects.getSub8());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
