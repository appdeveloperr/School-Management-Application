package com.sgs.usmansh.schmngsys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.*;
import static android.R.layout.browser_link_context_header;
import static android.R.layout.simple_expandable_list_item_1;
import static java.security.AccessController.getContext;

public class UserListAct extends AppCompatActivity {

    Spinner UserListChoice;
    //RaiflatButton removeBt;
    //EditText remId;
    DatabaseReference TechobjDB,StuobjDB,techlist;
    public ListView listVie;
    FirebaseListAdapter<Teacher>firebaseListAdapter;
    FirebaseListAdapter<String>listAdapter;
    ArrayList<Teacher> arrTechlist;
    int dataDel = 0;
    int listid = 1;
    //EditText srchtext;
    myAdpter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        listVie = (ListView)findViewById(R.id.listV);

        TechobjDB = FirebaseDatabase.getInstance().getReference("TechObject");
        StuobjDB = FirebaseDatabase.getInstance().getReference("StuObject");

        techlist = FirebaseDatabase.getInstance().getReference("TeachList");


        //Suppose you want to retrieve "Teacher's list" in your Firebase DB:
        Query query = FirebaseDatabase.getInstance().getReference("TeachList");

        //The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<String> options = new FirebaseListOptions.Builder<String>()
                .setQuery(query, String.class)
                .setLayout(R.layout.listrow)
                .build();

        //Finally you pass them to the constructor here:

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



          arrTechlist = new ArrayList<Teacher>();


        TechobjDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Teacher tech = snapshot.getValue(Teacher.class);
                    arrTechlist.add(tech);
                    //Toast.makeText(getApplicationContext(), "size of arr: "+arrTechlist.size(), Toast.LENGTH_SHORT).show();
                }
                   // setListAdpter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Searching Function coding
  /*      srchtext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //When user changed the Text
                ArrayList<Teacher> secondarr = new ArrayList<Teacher>();

                for (int i = 0; i < arrTechlist.size(); i++) {

                    Teacher teacher = arrTechlist.get(i);

                    if (teacher.getTechname().contains(cs))

                    {
                        secondarr.add(teacher);
                    }

                }
                listVie.setAdapter(new myAdpter(getApplicationContext(), secondarr));

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
*/


        listVie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Teacher selectedTeach = arrTechlist.get(i);
                Intent teacherDetalis = new Intent(getApplicationContext(),tecDetails.class);
                //teacherDetalis.putExtra("id",selectedTeach.getTechid().toString());
                teacherDetalis.putExtra("name",selectedTeach.getTechname());
                teacherDetalis.putExtra("class",selectedTeach.getTechtclass());
                teacherDetalis.putExtra("regid",selectedTeach.getTechregid());
                teacherDetalis.putExtra("password",selectedTeach.getTechpassword());
                startActivity(teacherDetalis);

            }
        });





    }


    private void setListAdpter() {

         adap = new myAdpter(this, arrTechlist);
        listVie.setAdapter(adap);

    }


    @Override
    protected void onStart() {
        super.onStart();

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
