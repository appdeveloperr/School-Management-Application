package com.sgs.usmansh.schmngsys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherClassList extends AppCompatActivity {

    TextView t_class_label;
    ListView t_class_list;
    DatabaseReference s_list;
    ArrayList<Student> stu_arrList = new ArrayList<>();
    ArrayList<String> student_names = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_list);



        t_class_list = (ListView)findViewById(R.id.t_class_list);
        t_class_label =  (TextView)findViewById(R.id.tclasslabel);
        t_class_label.setText(getIntent().getStringExtra("tClass")+" Students List");

        s_list = FirebaseDatabase.getInstance().getReference("StuObject");
        s_list.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postData:dataSnapshot.getChildren()){

                    Student select_student = postData.getValue(Student.class);

                    if(select_student.getSclass().equals(getIntent().getStringExtra("tClass"))){

                        student_names.add(select_student.getSname());
                        stu_arrList.add(select_student);
                       arrayAdapter.notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,student_names);
         arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, student_names){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.parseColor("#00e5ff"));

                // Generate ListView Item using TextView
                return view;
            }
        };

        t_class_list.setAdapter(arrayAdapter);


    }

}
