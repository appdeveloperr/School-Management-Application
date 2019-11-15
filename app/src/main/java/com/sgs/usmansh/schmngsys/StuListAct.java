package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class StuListAct extends AppCompatActivity {

    Spinner UserListChoice;
    //RaiflatButton removeBt;
    //EditText remId;
    DatabaseReference TechobjDB,StuobjDB,techlist,stulist;
    public ListView listVie;
    FirebaseListAdapter<Student> firebaseListAdapter;
    FirebaseListAdapter<String>listAdapter;
    ArrayList<Student> arrStulist;
    int dataDel = 0;
    int listid = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_list);



        //removeBt = (RaiflatButton)findViewById(R.id.remUser);
        //remId = (EditText)findViewById(R.id.delId);
        listVie = (ListView)findViewById(R.id.listV);

        TechobjDB = FirebaseDatabase.getInstance().getReference("TechObject");
        StuobjDB = FirebaseDatabase.getInstance().getReference("StuObject");

        stulist = FirebaseDatabase.getInstance().getReference("StuList");



        //Suppose you want to retrieve "chats" in your Firebase DB:
        Query query = FirebaseDatabase.getInstance().getReference("StuList");

        //The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<String> options = new FirebaseListOptions.Builder<String>()
                .setQuery(query, String.class)
                .setLayout(R.layout.listrow)
                .build();

        //Finally you pass them to the constructor here:
        // Toast.makeText(this, "going in adapter", Toast.LENGTH_SHORT).show();


        listAdapter = new FirebaseListAdapter<String>(options) {
            @Override
            protected void populateView(View v, String model, int position) {


                TextView id = (TextView) v.findViewById(R.id.idtv);
                TextView name = (TextView) v.findViewById(R.id.nametv);

                id.setTextColor(Color.parseColor("#1DE9B6"));
                name.setTextColor(Color.parseColor("#1DE9B6"));

                //id.setText(Integer.toString(listid)+": ");
                name.setText(model);
                //listid++;
            }
        };


        listVie.setAdapter(listAdapter);


        arrStulist = new ArrayList<Student>();

        StuobjDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Student stu = snapshot.getValue(Student.class);
                    arrStulist.add(stu);
                    //Toast.makeText(StuListAct.this, "size of arr: "+arrStulist.size(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listVie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Student selectedStu = arrStulist.get(i);

                Intent StudentDetalis = new Intent(getApplicationContext(),stuDetails.class);

                //StudentDetalis.putExtra("id",selectedTeach.getTechid().toString());
                StudentDetalis.putExtra("name",selectedStu.getSname());
                StudentDetalis.putExtra("class",selectedStu.getSclass());
                StudentDetalis.putExtra("regid",selectedStu.getSregNo());
                StudentDetalis.putExtra("password",selectedStu.getSpassword());

                startActivity(StudentDetalis);

            }
        });


    }




    @Override
    protected void onStart() {
        super.onStart();
        // firebaseListAdapter.startListening();

        listAdapter.startListening();
        listid = 1;
    }


    @Override
    protected void onStop() {
        super.onStop();
        //firebaseListAdapter.stopListening();
        listAdapter.stopListening();
    }

}
