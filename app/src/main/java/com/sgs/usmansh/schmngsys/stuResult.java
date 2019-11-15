package com.sgs.usmansh.schmngsys;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Objects;

public class stuResult extends AppCompatActivity {

    TextView res_sub1,res_sub2,res_sub3,res_sub4,res_sub5,res_sub6,res_sub7,res_sub8,res_sub9,res_sub10,mtype,total_res;
    DatabaseReference result_marks,testMarks;
    double total_marks,result,test_total,sumTotal;
    String res_percent;
    DecimalFormat f;
    Marks res_marks,test_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_result);


        mtype    = (TextView)findViewById(R.id.mtype);
        res_sub1 = (TextView)findViewById(R.id.res_sub_1);
        res_sub2 = (TextView)findViewById(R.id.res_sub_2);
        res_sub3 = (TextView)findViewById(R.id.res_sub_3);
        res_sub4 = (TextView)findViewById(R.id.res_sub_4);
        res_sub5 = (TextView)findViewById(R.id.res_sub_5);
        res_sub6 = (TextView)findViewById(R.id.res_sub_6);
        res_sub7 = (TextView)findViewById(R.id.res_sub_7);
        res_sub8 = (TextView)findViewById(R.id.res_sub_8);
        res_sub9 = (TextView)findViewById(R.id.res_sub_9);
        res_sub10 = (TextView)findViewById(R.id.res_sub_10);
        total_res = (TextView)findViewById(R.id.totalres);


        mtype.setText(getIntent().getStringExtra("markstype"));

        result_marks = FirebaseDatabase.getInstance().getReference("Marks").child(getIntent().getStringExtra("sclass"))
                .child(getIntent().getStringExtra("sregid")).child(getIntent().getStringExtra("markstype")).child("Marks");

        result_marks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 res_marks = dataSnapshot.getValue(Marks.class);

                if(res_marks !=null){
                     Toast.makeText(stuResult.this, "All marks getted", Toast.LENGTH_SHORT).show();

                     res_sub1.setText(res_marks.getMark1());
                     res_sub2.setText(res_marks.getMark2());
                     res_sub3.setText(res_marks.getMark3());
                     res_sub4.setText(res_marks.getMark4());
                     res_sub5.setText(res_marks.getMark5());
                     res_sub6.setText(res_marks.getMark6());
                     res_sub7.setText(res_marks.getMark7());
                     res_sub8.setText(res_marks.getMark8());
                     res_sub9.setText(res_marks.getMark9());
                     res_sub10.setText(res_marks.getMark10());

                     createResult(getIntent().getStringExtra("markstype"));
                 }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @SuppressLint("SetTextI18n")
    private void createResult(String markstype) {


        if(markstype.equalsIgnoreCase("Monthly test 1")||
                markstype.equalsIgnoreCase("Monthly test 2")||
                markstype.equalsIgnoreCase("Monthly test 3")){


            total_marks = Double.parseDouble(res_marks.getTotalmarks());
            result = (total_marks/225)*100;;
            res_percent = Double.toString(result);
            f = new DecimalFormat("##.00");
            createTestGrade(result);

        }
        else if( markstype.equalsIgnoreCase("First term")){

            //1st Term
            total_marks = Double.parseDouble(res_marks.getTotalmarks());
            testMarks = FirebaseDatabase.getInstance().getReference("Marks").child(getIntent().getStringExtra("sclass"))
                    .child(getIntent().getStringExtra("sregid")).child("Monthly test 1").child("Marks");
            testMarks.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    test_marks = dataSnapshot.getValue(Marks.class);
                    if(test_marks !=null) {
                        test_total = Double.parseDouble(test_marks.getTotalmarks());
                        createFinalResult();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else if( markstype.equalsIgnoreCase("Second term")){

            //1st Term
            total_marks = Double.parseDouble(res_marks.getTotalmarks());
            testMarks = FirebaseDatabase.getInstance().getReference("Marks").child(getIntent().getStringExtra("sclass"))
                    .child(getIntent().getStringExtra("sregid")).child("Monthly test 2").child("Marks");
            testMarks.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    test_marks = dataSnapshot.getValue(Marks.class);
                    if(test_marks !=null) {
                        test_total = Double.parseDouble(test_marks.getTotalmarks());
                        createFinalResult();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else if( markstype.equalsIgnoreCase("Third term")){

            //1st Term
            total_marks = Double.parseDouble(res_marks.getTotalmarks());
            testMarks = FirebaseDatabase.getInstance().getReference("Marks").child(getIntent().getStringExtra("sclass"))
                    .child(getIntent().getStringExtra("sregid")).child("Monthly test 3").child("Marks");
            testMarks.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    test_marks = dataSnapshot.getValue(Marks.class);
                    if(test_marks !=null) {
                        test_total = Double.parseDouble(test_marks.getTotalmarks());
                        createFinalResult();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


    }


    private void createFinalResult() {


        sumTotal = total_marks+test_total;
        result = (sumTotal/900)*100;
        res_percent = Double.toString(result);
        f = new DecimalFormat("##.00");
        createTermGrade(result);

    }

    private void createTermGrade(double result) {

        Toast.makeText(this, "sumTotal: "+sumTotal, Toast.LENGTH_SHORT).show();
        if(result >=90){
            total_res.setText("Total Marks: "+sumTotal+"/1000"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: A+ ");

        }else if(result>=80 && result<90){

            total_res.setText("Total Marks: "+sumTotal+"/1000"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: A ");
        }
        else if(result>=70 && result<80){

            total_res.setText("Total Marks: "+sumTotal+"/1000"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: B ");
        }else if(result>=60 && result<70){

            total_res.setText("Total Marks: "+sumTotal+"/1000"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: C ");
        }else if(result>=50 && result<60){

            total_res.setText("Total Marks: "+sumTotal+"/1000"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: D ");
        }else if(result>=33 && result<50){

            total_res.setText("Total Marks: "+sumTotal+"/1000"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: E ");
        }else if(result<33){

                total_res.setText("Total Marks: "+sumTotal+"/1000"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: F ");
        }
    }


    private void createTestGrade(double result) {

        if(result >=90){
            total_res.setText("Total Marks: "+total_marks+"/250"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: A+ ");

        }else if(result>=80 && result<90){

            total_res.setText("Total Marks: "+total_marks+"/250"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: A ");
        }
        else if(result>=70 && result<80){

            total_res.setText("Total Marks: "+total_marks+"/250"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: B ");
        }else if(result>=60 && result<70){

            total_res.setText("Total Marks: "+total_marks+"/250"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: C ");
        }else if(result>=50 && result<60){

            total_res.setText("Total Marks: "+total_marks+"/250"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: D ");
        }else if(result>=33 && result<50){

            total_res.setText("Total Marks: "+total_marks+"/250"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: E ");
        }else if(result<33){

            total_res.setText("Total Marks: "+total_marks+"/250"+"\nPercent Age: "+f.format(result)+"%"+"\nGrade: F ");
        }





    }

}
