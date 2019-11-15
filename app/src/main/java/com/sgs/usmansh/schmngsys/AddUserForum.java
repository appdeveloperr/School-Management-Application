package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUserForum extends AppCompatActivity {

    EditText nametv,regtv,passtv;
    Button submitUser,cancel;
    Spinner ClassChoice,ForumChoice;
    DatabaseReference TechList,TechObj,StuList,StuObj,subAssinged,admininfo;
    Teacher newTeacher;
    Student newStudent;
    FirebaseAuth mAuth;
    String admin_email,admin_pass;
    int id = 1;
    int id2= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_forum);

        ForumChoice = (Spinner)findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(AddUserForum.this,
                    android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.addUserList));

        myAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ForumChoice.setAdapter(myAdapter1);

        ClassChoice = (Spinner)findViewById(R.id.spinner2);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(AddUserForum.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.classes));

        myAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ClassChoice.setAdapter(myAdapter2);

        ForumChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                //Selected option is using for Forum Interface Seleection
                selectedOption(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        TechList = FirebaseDatabase.getInstance().getReference("TeachList");
        TechObj  = FirebaseDatabase.getInstance().getReference("TechObject");
        StuList  = FirebaseDatabase.getInstance().getReference("StuList");
        StuObj   = FirebaseDatabase.getInstance().getReference("StuObject");
        subAssinged = FirebaseDatabase.getInstance().getReference("Subassigned");
        admininfo = FirebaseDatabase.getInstance().getReference("Admin");

        admininfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             admin_email = dataSnapshot.child("email").getValue().toString();
              admin_pass = dataSnapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mAuth = FirebaseAuth.getInstance();


        nametv = (EditText)findViewById(R.id.nametv);
        regtv = (EditText)findViewById(R.id.idtv);
        passtv = (EditText)findViewById(R.id.passtv);
        submitUser = (Button)findViewById(R.id.submitbt);
        cancel = (Button)findViewById(R.id.cancelbt);



        nametv.setVisibility(View.INVISIBLE);
        regtv.setVisibility(View.INVISIBLE);
        passtv.setVisibility(View.INVISIBLE);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent gotoAdPanel = new Intent(getApplicationContext(),AdminPanel.class);
                gotoAdPanel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoAdPanel);

            }
        });




        //Submit Button

    //final Teacher newTeacher = new Teacher();

        submitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubmitData();
            }
        });



    }

//'.', '#', '$', '[', or ']'
    private void SubmitData() {

        if(!TextUtils.isEmpty(nametv.getText().toString().trim())&&
                !TextUtils.isEmpty(regtv.getText().toString().trim())&&
                !TextUtils.isEmpty(passtv.getText().toString().trim())
                ) {


            if(!nametv.getText().toString().contains(".")&&
                    !nametv.getText().toString().contains("#")&&
                    !nametv.getText().toString().contains("$")&&
                    !nametv.getText().toString().contains("[")&&
                    !nametv.getText().toString().contains("]")&&
                    !nametv.getText().toString().contains(",")&&
                    !regtv.getText().toString().contains(".")&&
                    !regtv.getText().toString().contains("#")&&
                    !regtv.getText().toString().contains("$")&&
                    !regtv.getText().toString().contains("[")&&
                    !regtv.getText().toString().contains("]")&&
                    !regtv.getText().toString().contains(",")&&
                    !regtv.getText().toString().contains(" ")&&
                    passtv.getText().toString().length()>5) {


                if (ForumChoice.getSelectedItem().toString().equalsIgnoreCase("Teacher")) {

                    TechList.child(regtv.getText().toString().trim().toLowerCase()).setValue(nametv.getText().toString().trim());

                    newTeacher = new Teacher();
                    //newTeacher.setTechid(Integer.toString(id));
                    newTeacher.setTechname(nametv.getText().toString().trim().toLowerCase());
                    newTeacher.setTechtclass(ClassChoice.getSelectedItem().toString());
                    newTeacher.setTechregid(regtv.getText().toString().trim().toLowerCase());
                    newTeacher.setTechpassword(passtv.getText().toString().trim());
                    TechObj.child(regtv.getText().toString().trim().toLowerCase()).setValue(newTeacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(AddUserForum.this, "Data Submitted", Toast.LENGTH_SHORT).show();
                                id++;
                                //creating Email and pass word
                                CreatingTechLogin();
                                //gotoAdminPnl();

                            } else {
                                Toast.makeText(AddUserForum.this, "Error while Submittion", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    subAssinged.child(regtv.getText().toString().trim().toLowerCase()).setValue("no");
                }

                //Student's Data Submittion
                else if (ForumChoice.getSelectedItem().toString().equalsIgnoreCase("Student")) {

                    StuList.child(regtv.getText().toString().trim().toLowerCase()).setValue(nametv.getText().toString().trim());

                    newStudent = new Student();
                    //newStudent.setSid(Integer.toString(id2));
                    newStudent.setSname(nametv.getText().toString().trim().toLowerCase());
                    newStudent.setSclass(ClassChoice.getSelectedItem().toString());
                    newStudent.setSregNo(regtv.getText().toString().trim().toLowerCase());
                    newStudent.setSpassword(passtv.getText().toString().trim());

                    StuObj.child(regtv.getText().toString().trim().toLowerCase()).setValue(newStudent).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(AddUserForum.this, "Data Submitted", Toast.LENGTH_SHORT).show();
                                // id2++;

                                CreatingStuLogin();
                                //gotoAdminPnl();

                            } else {
                                Toast.makeText(AddUserForum.this, "Error while Submittion", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }

            else{
                Toast.makeText(this, "Don't use special characters and Spaces..! (,.,#,$,[,{", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Password need to contain at least 6 characters", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddUserForum.this, "Field is Empty", Toast.LENGTH_SHORT).show();
        }

    }//End Function

    private void CreatingStuLogin() {

        String login = newStudent.getSregNo().toLowerCase()+"@sgs.com";
        String password = newStudent.getSpassword();

        mAuth.createUserWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(AddUserForum.this, "Login created", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        FirebaseAuth.getInstance().signOut();
                        mAuth.signInWithEmailAndPassword(admin_email,admin_pass);
                        Intent gotoAdminPanel = new Intent(getApplicationContext(),AdminPanel.class);
                        gotoAdminPanel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(gotoAdminPanel);

                    } else {
                        // No user is signed in
                    }


                    finish();

                }else{
                    Toast.makeText(AddUserForum.this, "Login not created", Toast.LENGTH_SHORT).show();

                }
            }
        });




    }


    private void CreatingTechLogin() {

        String login = newTeacher.getTechregid().toLowerCase()+"@sgsstaff.com";
        String password = newTeacher.getTechpassword();

        mAuth.createUserWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(AddUserForum.this, "Login created", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        FirebaseAuth.getInstance().signOut();
                        mAuth.signInWithEmailAndPassword(admin_email,admin_pass);
                        Intent gotoAdminPanel = new Intent(getApplicationContext(),AdminPanel.class);
                        gotoAdminPanel.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(gotoAdminPanel);
                        finish();

                    } else {
                        // No user is signed in
                    }

                    finish();

                }else{
                    Toast.makeText(AddUserForum.this, "Login not created", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void selectedOption(int position) {


        if(position == 0){

            nametv.setVisibility(View.VISIBLE);
            regtv.setVisibility(View.VISIBLE);
            passtv.setVisibility(View.VISIBLE);

            nametv.setHint("Teacher's Name");
            regtv.setHint("Teacher's Id");
            passtv.setHint("Teacher's Password");

            submitUser.setText("Submit Teacher");
        }
        else if(position == 1){

            nametv.setVisibility(View.VISIBLE);
            regtv.setVisibility(View.VISIBLE);
            passtv.setVisibility(View.VISIBLE);

            nametv.setHint("Student's Name");
            regtv.setHint("Student's Id");
            passtv.setHint("Student's Password");

            submitUser.setText("Submit Student");

        }

    }
}
