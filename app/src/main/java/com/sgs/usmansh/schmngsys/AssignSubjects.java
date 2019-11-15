package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignSubjects extends AppCompatActivity {

    Spinner sp1Sub,sp2Sub,sp3Sub,sp4Sub,sp5Sub,sp6Sub,sp7Sub,sp8Sub;
    Spinner sp1Cls,sp2Cls,sp3Cls,sp4Cls,sp5Cls,sp6Cls,sp7Cls,sp8Cls;
    Spinner assignTspinner;

    Button  assignBt,Cancelbt;
    DatabaseReference AssignSub,teacherlist,tasSub,techObject;

    ArrayList<String> TechNamelist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_subjects);

        sp1Sub = (Spinner)findViewById(R.id.subSpinner1);
        sp2Sub = (Spinner)findViewById(R.id.subSpinner2);
        sp3Sub = (Spinner)findViewById(R.id.subSpinner3);
        sp4Sub = (Spinner)findViewById(R.id.subSpinner4);
        sp5Sub = (Spinner)findViewById(R.id.subSpinner5);
        sp6Sub = (Spinner)findViewById(R.id.subSpinner6);
        sp7Sub = (Spinner)findViewById(R.id.subSpinner7);
        sp8Sub = (Spinner)findViewById(R.id.subSpinner8);

        sp1Cls = (Spinner)findViewById(R.id.classSpinner1);
        sp2Cls = (Spinner)findViewById(R.id.classSpinner2);
        sp3Cls = (Spinner)findViewById(R.id.classSpinner3);
        sp4Cls = (Spinner)findViewById(R.id.classSpinner4);
        sp5Cls = (Spinner)findViewById(R.id.classSpinner5);
        sp6Cls = (Spinner)findViewById(R.id.classSpinner6);
        sp7Cls = (Spinner)findViewById(R.id.classSpinner7);
        sp8Cls = (Spinner)findViewById(R.id.classSpinner8);

        assignBt = (Button)findViewById(R.id.submitSubject);
        Cancelbt = (Button)findViewById(R.id.CancelassSub);

        AssignSub = FirebaseDatabase.getInstance().getReference("AssignSubjects");
        teacherlist = FirebaseDatabase.getInstance().getReference("TeachList");
        techObject = FirebaseDatabase.getInstance().getReference("TechObject");
        tasSub    = FirebaseDatabase.getInstance().getReference("Subassigned");

        ArrayAdapter<String> SubAdapter = new ArrayAdapter<String>(AssignSubjects.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.subjects));
        SubAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<String> ClsAdapter = new ArrayAdapter<String>(AssignSubjects.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.classes));
        ClsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        sp1Sub.setAdapter(SubAdapter);
        sp1Cls.setAdapter(ClsAdapter);

        sp2Sub.setAdapter(SubAdapter);
        sp2Cls.setAdapter(ClsAdapter);

        sp3Sub.setAdapter(SubAdapter);
        sp3Cls.setAdapter(ClsAdapter);

        sp4Sub.setAdapter(SubAdapter);
        sp4Cls.setAdapter(ClsAdapter);

        sp5Sub.setAdapter(SubAdapter);
        sp5Cls.setAdapter(ClsAdapter);

        sp6Sub.setAdapter(SubAdapter);
        sp6Cls.setAdapter(ClsAdapter);

        sp7Sub.setAdapter(SubAdapter);
        sp7Cls.setAdapter(ClsAdapter);

        sp8Sub.setAdapter(SubAdapter);
        sp8Cls.setAdapter(ClsAdapter);



        //Fetching Teachers list from firebase and storing into TechNameList
        /*teacherlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String tName = areaSnapshot.getValue(String.class);
                    TechNamelist.add(tName);
                }

                assignTspinner = (Spinner) findViewById(R.id.TeacherSpinner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, TechNamelist);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assignTspinner.setAdapter(areasAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        techObject.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Subject assigned by teacher id
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    Teacher teacher = areaSnapshot.getValue(Teacher.class);
                    String tId = teacher.getTechregid();
                    TechNamelist.add(tId);
                }

                assignTspinner = (Spinner) findViewById(R.id.TeacherSpinner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, TechNamelist);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assignTspinner.setAdapter(areasAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        assignBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AssignSubData();



            }
        });


        Cancelbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent AdminPanel = new Intent(getApplicationContext(),AdminPanel.class);
                AdminPanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(AdminPanel);
                finish();
            }
        });





    }



    private void AssignSubData() {


        DatabaseReference teacheridRef = AssignSub.child(assignTspinner.getSelectedItem().toString());

        Subjects subjects  = new Subjects();
        subjects.setSub1(sp1Sub.getSelectedItem().toString());
        subjects.setSub2(sp2Sub.getSelectedItem().toString());
        subjects.setSub3(sp3Sub.getSelectedItem().toString());
        subjects.setSub4(sp4Sub.getSelectedItem().toString());
        subjects.setSub5(sp5Sub.getSelectedItem().toString());
        subjects.setSub6(sp6Sub.getSelectedItem().toString());
        subjects.setSub7(sp7Sub.getSelectedItem().toString());
        subjects.setSub8(sp8Sub.getSelectedItem().toString());

        Classs classses = new Classs();
        classses.setCls1(sp1Cls.getSelectedItem().toString());
        classses.setCls2(sp2Cls.getSelectedItem().toString());
        classses.setCls3(sp3Cls.getSelectedItem().toString());
        classses.setCls4(sp4Cls.getSelectedItem().toString());
        classses.setCls5(sp5Cls.getSelectedItem().toString());
        classses.setCls6(sp6Cls.getSelectedItem().toString());
        classses.setCls7(sp7Cls.getSelectedItem().toString());
        classses.setCls8(sp8Cls.getSelectedItem().toString());

        teacheridRef.child("Classes").setValue(classses);
        teacheridRef.child("Subjects").setValue(subjects).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AssignSubjects.this, "Classes and Subjects Assigned to Teacher", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tasSub.child(assignTspinner.getSelectedItem().toString()).setValue("yes");



    }

}
