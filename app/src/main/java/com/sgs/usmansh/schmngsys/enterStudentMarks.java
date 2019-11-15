package com.sgs.usmansh.schmngsys;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Objects;

public class enterStudentMarks extends AppCompatActivity {

    Spinner markstype_spinner;
    TextView Sub1_tv,Sub2_tv,Sub3_tv,Sub4_tv,Sub5_tv,Sub6_tv,Sub7_tv,Sub8_tv,Sub9_tv,Sub10_tv;
    double mark1,mark2,mark3,mark4,mark5,mark6,mark7,mark8,mark9,mark10;
    double total_marks,result;
    EditText mrk_sub1,mrk_sub2,mrk_sub3,mrk_sub4,mrk_sub5,mrk_sub6,mrk_sub7,mrk_sub8,mrk_sub9,mrk_sub10 ;
    Button submit_marks;
    TextView mark_stu_name;
    DatabaseReference marks_Ref,enter_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sgs.usmansh.schmngsys.R.layout.activity_enter_student_marks);

                markstype_spinner = (Spinner)findViewById(com.sgs.usmansh.schmngsys.R.id.markstype_spinner);
        ArrayAdapter<String> myAdapterrr = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(com.sgs.usmansh.schmngsys.R.array.markstype));
        myAdapterrr.setDropDownViewResource(com.sgs.usmansh.schmngsys.R.layout.support_simple_spinner_dropdown_item);
        markstype_spinner.setAdapter(myAdapterrr);
        markstype_spinner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) markstype_spinner.getSelectedView()).setTextColor(Color.CYAN);
            }
        });


        mark_stu_name = (TextView)findViewById(com.sgs.usmansh.schmngsys.R.id.mrk_stu_name);
        mark_stu_name.setText(mark_stu_name.getText().toString()+getIntent().getStringExtra("sname"));


        marks_Ref = FirebaseDatabase.getInstance().getReference("Marks").child(getIntent().getStringExtra("tClass"))
                .child(getIntent().getStringExtra("sregid"));


        Sub1_tv = (TextView)findViewById(R.id.Sub1_tv);
        Sub2_tv = (TextView)findViewById(R.id.Sub2_tv);
        Sub3_tv = (TextView)findViewById(R.id.Sub3_tv);
        Sub4_tv = (TextView)findViewById(R.id.Sub4_tv);
        Sub5_tv = (TextView)findViewById(R.id.Sub5_tv);
        Sub6_tv = (TextView)findViewById(R.id.Sub6_tv);
        Sub7_tv = (TextView)findViewById(R.id.Sub7_tv);
        Sub8_tv = (TextView)findViewById(R.id.Sub8_tv);
        Sub9_tv = (TextView)findViewById(R.id.Sub9_tv);
        Sub10_tv = (TextView)findViewById(R.id.Sub10_tv);


        ArrayAdapter<String> SubAdapter = new ArrayAdapter<String>(enterStudentMarks.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.subjects));
        SubAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        mrk_sub1 = (EditText)findViewById(R.id.mrk_sub_1);
        mrk_sub2 = (EditText)findViewById(R.id.mrk_sub_2);
        mrk_sub3 = (EditText)findViewById(R.id.mrk_sub_3);
        mrk_sub4 = (EditText)findViewById(R.id.mrk_sub_4);
        mrk_sub5 = (EditText)findViewById(R.id.mrk_sub_5);
        mrk_sub6 = (EditText)findViewById(R.id.mrk_sub_6);
        mrk_sub7 = (EditText)findViewById(R.id.mrk_sub_7);
        mrk_sub8 = (EditText)findViewById(R.id.mrk_sub_8);
        mrk_sub9 = (EditText)findViewById(R.id.mrk_sub_9);
        mrk_sub10 = (EditText)findViewById(R.id.mrk_sub_10);

        submit_marks = (Button)findViewById(R.id.submit_marks);
        submit_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(enterStudentMarks.this, "Selected marks type: "+markstype_spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                SubmittingMarks(markstype_spinner.getSelectedItem().toString());



            }
        });

    }




    private void SubmittingMarks(String marksType) {


            Subjects sub = new Subjects();

        sub.setSub1(Sub1_tv.getText().toString());
        sub.setSub2(Sub2_tv.getText().toString());
        sub.setSub3(Sub3_tv.getText().toString());
        sub.setSub4(Sub4_tv.getText().toString());
        sub.setSub5(Sub5_tv.getText().toString());
        sub.setSub6(Sub6_tv.getText().toString());
        sub.setSub7(Sub7_tv.getText().toString());
        sub.setSub8(Sub8_tv.getText().toString());
        sub.setSub9(Sub9_tv.getText().toString());
        sub.setSub10(Sub10_tv.getText().toString());


        Marks mark = new Marks();

            mark.setMark1(mrk_sub1.getText().toString());
            mark.setMark2(mrk_sub2.getText().toString());
            mark.setMark3(mrk_sub3.getText().toString());
            mark.setMark4(mrk_sub4.getText().toString());
            mark.setMark5(mrk_sub5.getText().toString());
            mark.setMark6(mrk_sub6.getText().toString());
            mark.setMark7(mrk_sub7.getText().toString());
            mark.setMark8(mrk_sub8.getText().toString());
            mark.setMark9(mrk_sub9.getText().toString());
            mark.setMark10(mrk_sub10.getText().toString());

        if(!Objects.equals(mrk_sub1.getText().toString(), "")) {
           mark1 = Double.parseDouble(mrk_sub1.getText().toString());
        }if(!Objects.equals(mrk_sub2.getText().toString(), "")) {
            mark2 = Double.parseDouble(mrk_sub2.getText().toString());
        }if(!Objects.equals(mrk_sub3.getText().toString(), "")) {
            mark3 = Double.parseDouble(mrk_sub3.getText().toString());
        }if(!Objects.equals(mrk_sub4.getText().toString(), "")) {
            mark4 = Double.parseDouble(mrk_sub4.getText().toString());
        }if(!Objects.equals(mrk_sub5.getText().toString(), "")) {
            mark5 = Double.parseDouble(mrk_sub5.getText().toString());
        }if(!Objects.equals(mrk_sub6.getText().toString(), "")) {
            mark6 = Double.parseDouble(mrk_sub6.getText().toString());
        }if(!Objects.equals(mrk_sub7.getText().toString(), "")) {
            mark7 = Double.parseDouble(mrk_sub7.getText().toString());
        }if(!Objects.equals(mrk_sub8.getText().toString(), "")) {
            mark8 = Double.parseDouble(mrk_sub8.getText().toString());
        }if(!Objects.equals(mrk_sub9.getText().toString(), "")) {
            mark9 = Double.parseDouble(mrk_sub9.getText().toString());
        }if(!Objects.equals(mrk_sub10.getText().toString(), "")) {
            mark10 = Double.parseDouble(mrk_sub10.getText().toString());
        }

        total_marks = mark1+mark2+mark3+mark4+mark5+mark6+mark7+mark8+mark9+mark10;
            mark.setTotalmarks(Double.toString(total_marks));

        Toast.makeText(this, "Total Marks: "+total_marks, Toast.LENGTH_SHORT).show();
        marks_Ref.child(marksType).child("Subjects").setValue(sub);

            marks_Ref.child(marksType).child("Marks").setValue(mark).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(enterStudentMarks.this, "Marks Submit successfully..!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(enterStudentMarks.this, "Error while submittion", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
}
