package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class noticeBoard extends AppCompatActivity {

    Spinner NB_choice;
    Button submit_NB,cancel_NB;
    EditText notice_et;
    DatabaseReference noticeBoard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        NB_choice = (Spinner)findViewById(R.id.NB_type);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(noticeBoard.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.NbUserList));
        myAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        NB_choice.setAdapter(myAdapter1);


        submit_NB = (Button)findViewById(R.id.submitNB);
        cancel_NB = (Button)findViewById(R.id.cancelNB);
        notice_et = (EditText)findViewById(R.id.notice_et);

        noticeBoard = FirebaseDatabase.getInstance().getReference("NoticeBoard");

        final String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        submit_NB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference finalNB = noticeBoard.child(NB_choice.getSelectedItem().toString());
                finalNB.setValue(mydate+"\n\n"+notice_et.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(noticeBoard.this, "Notice Successfully Uploaded..!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(noticeBoard.this, "Error while uploading notice..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
