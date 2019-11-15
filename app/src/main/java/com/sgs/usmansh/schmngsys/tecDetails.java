package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class tecDetails extends AppCompatActivity {

    EditText etname,etclass;
    TextView idText,regidText,passText;
    
    Button delBt,edBt,canBt,subdetails;
    DatabaseReference TeachObj,TechList,AssignSub,SubAssigned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tec_details);

        //idText = (TextView) findViewById(R.id.tid);
        etname = (EditText)findViewById(R.id.tname);
        etclass = (EditText)findViewById(R.id.tclass);
        regidText = (TextView) findViewById(R.id.tregid);
        passText = (TextView) findViewById(R.id.tpass);

        delBt = (Button)findViewById(R.id.deleteBt);
        edBt = (Button)findViewById(R.id.editBt);
        canBt = (Button)findViewById(R.id.canBt);
        subdetails = (Button)findViewById(R.id.subdetails);

        final Intent data = getIntent();

        //Showing Teacher's Details
        //idText.setText(data.getStringExtra("id"));
        etname.setText(data.getStringExtra("name"));
        etclass.setText(data.getStringExtra("class"));
        regidText.setText(data.getStringExtra("regid")+"@sgsstaff.com");
        passText.setText(data.getStringExtra("password"));

        TeachObj = FirebaseDatabase.getInstance().getReference("TechObject");
        TechList = FirebaseDatabase.getInstance().getReference("TeachList");
        AssignSub = FirebaseDatabase.getInstance().getReference("AssignSubjects");
        SubAssigned = FirebaseDatabase.getInstance().getReference("Subassigned");

        //Subject details
        subdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent showSubDetails = new Intent(getApplicationContext(),subDetails.class);
                showSubDetails.putExtra("tID",data.getStringExtra("regid"));
                showSubDetails.putExtra("tname",data.getStringExtra("name"));
                startActivity(showSubDetails);
            }
        });

        //Delete Selected Teacher
        delBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // SubAssigned.child(data.getStringExtra("regid")).removeValue();
                AssignSub.child(data.getStringExtra("regid")).removeValue();
                TechList.child(data.getStringExtra("regid")).removeValue();
                TeachObj.child(data.getStringExtra("regid")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(tecDetails.this, "Teacher Deleted", Toast.LENGTH_SHORT).show();
                            Intent gotoPanel = new Intent(getApplicationContext(),AdminPanel.class);
                            gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoPanel);
                            finish();

                        }else{
                            Toast.makeText(tecDetails.this, "Error while deleting", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



        //Edit Teacher's Data
        edBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(!TextUtils.isEmpty(etname.getText().toString().trim())&&
                        !TextUtils.isEmpty(etclass.getText().toString().trim())){

                    TechList.child(data.getStringExtra("regid")).setValue(etname.getText().toString().trim());

                    TeachObj.child(data.getStringExtra("regid")).child("techname").setValue(etname.getText().toString().trim());
                    TeachObj.child(data.getStringExtra("regid")).child("techtclass").setValue(etclass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(tecDetails.this, "Teacher Edit", Toast.LENGTH_SHORT).show();
                                Intent gotoPanel = new Intent(getApplicationContext(),AdminPanel.class);
                                gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(gotoPanel);
                                finish();

                            }else{
                                Toast.makeText(tecDetails.this, "Error while Editing", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }

            }
        });


        canBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotoUserList = new Intent(getApplicationContext(),UserListAct.class);
                gotoUserList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoUserList);
                finish();

            }
        });



    }
}
