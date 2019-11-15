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

public class st_cls_list extends AppCompatActivity {



    TextView d_class_label;
    ListView d_class_list;
    DatabaseReference s_list;
    ArrayList<Student> stu_arrList = new ArrayList<>();
    ArrayList<String> student_names = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String adminKey;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_cls_list);

        Toast.makeText(this, "Class key : "+ getIntent().getStringExtra("classkey"), Toast.LENGTH_SHORT).show();
        adminKey = getIntent().getStringExtra("adminKey");



        d_class_list = (ListView)findViewById(R.id.d_class_list);
        d_class_label =  (TextView)findViewById(R.id.d_classlabel);
        d_class_label.setText(getIntent().getStringExtra("classkey")+" Students List");


        s_list = FirebaseDatabase.getInstance().getReference("StuObject");
        s_list.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postData:dataSnapshot.getChildren()){

                    Student select_student = postData.getValue(Student.class);

                    if(select_student.getSclass().equals(getIntent().getStringExtra("classkey"))){

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

        //arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,student_names);

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


        d_class_list.setAdapter(arrayAdapter);






        d_class_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Student student = stu_arrList.get(i);

                Intent go_t_student_fine = new Intent(getApplicationContext(),enterStudentFine.class);
                go_t_student_fine.putExtra("sname" , student.getSname());
                go_t_student_fine.putExtra("sclass",student.getSclass());
                go_t_student_fine.putExtra("sregid",student.getSregNo());
                go_t_student_fine.putExtra("spass",student.getSpassword());
                go_t_student_fine.putExtra("classkey",getIntent().getStringExtra("tClass"));
                go_t_student_fine.putExtra("adminKey",adminKey);
                startActivity(go_t_student_fine);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        adminKey = getIntent().getStringExtra("adminKey");
    }
}
