package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.provider.ContactsContract;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class stuDetails extends AppCompatActivity {

    EditText etname,etclass;
    TextView idText,regidText,passText;

    Button delBt,edBt,canBt;
    DatabaseReference StuObj,StuList;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_details);



        //idText = (TextView) findViewById(R.id.tid);
        etname = (EditText)findViewById(R.id.sname);
        etclass = (EditText)findViewById(R.id.sclass);
        regidText = (TextView) findViewById(R.id.sregid);
        passText = (TextView) findViewById(R.id.spass);

        delBt = (Button)findViewById(R.id.deleteBt);
        edBt = (Button)findViewById(R.id.editBt);
        canBt = (Button)findViewById(R.id.canBt);

        final Intent data = getIntent();

        //Showing Teacher's Details
        //idText.setText(data.getStringExtra("id"));
        etname.setText(data.getStringExtra("name"));
        etclass.setText(data.getStringExtra("class"));
        regidText.setText(data.getStringExtra("regid")+"@sgs.com");
        passText.setText(data.getStringExtra("password"));

        mAuth = FirebaseAuth.getInstance();
        StuObj = FirebaseDatabase.getInstance().getReference("StuObject");
        StuList = FirebaseDatabase.getInstance().getReference("StuList");


        //Delete Selected Teacher
        delBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                


                DatabaseReference delmarks =  FirebaseDatabase.getInstance().getReference("Marks").child(getIntent().getStringExtra("class"))
                        .child(getIntent().getStringExtra("regid"));
                delmarks.removeValue();

                DatabaseReference delFine = FirebaseDatabase.getInstance().getReference("Fine").child(getIntent().getStringExtra("regid"));
                delFine.removeValue();

                DatabaseReference del_com_dia = FirebaseDatabase.getInstance().getReference("Classes").child(getIntent().getStringExtra("class"))
                        .child(getIntent().getStringExtra("regid"));
                del_com_dia.removeValue();

                StuList.child(data.getStringExtra("regid")).removeValue();

                StuObj.child(data.getStringExtra("regid")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(stuDetails.this, "Student Deleted", Toast.LENGTH_SHORT).show();
                            Intent gotoPanel = new Intent(getApplicationContext(),AdminPanel.class);
                            gotoPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoPanel);
                            finish();

                        }else{
                            Toast.makeText(stuDetails.this, "Error while deleting", Toast.LENGTH_SHORT).show();
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

                    StuList.child(getIntent().getStringExtra("regid")).setValue(etname.getText().toString().trim());

                    StuObj.child(getIntent().getStringExtra("regid")).child("sname").setValue(etname.getText().toString().trim());
                    StuObj.child(getIntent().getStringExtra("regid")).child("sclass").setValue(etclass.getText().toString().trim());


                }

            }
        });


        canBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotoUserList = new Intent(getApplicationContext(),StuListAct.class);
                gotoUserList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoUserList);
                finish();

            }
        });


    }


}
