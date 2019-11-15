package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class marksDetails extends AppCompatActivity {

    Button m_test_1,m_test_2,m_test_3;
    Button f_term,s_term,t_term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_details);

        m_test_1 = (Button) findViewById(R.id.m_test_1);
        m_test_2 = (Button) findViewById(R.id.m_test_2);
        m_test_3 = (Button) findViewById(R.id.m_test_3);

        f_term = (Button)findViewById(R.id.f_term);
        s_term = (Button)findViewById(R.id.s_term);
        t_term = (Button)findViewById(R.id.t_term);

        m_test_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stuResult = new Intent(getApplicationContext(),stuResult.class);
                stuResult.putExtra("markstype","Monthly test 1");
                stuResult.putExtra("sclass",getIntent().getStringExtra("sclass"));
                stuResult.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(stuResult);
            }
        });


        m_test_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stuResult = new Intent(getApplicationContext(),stuResult.class);
                stuResult.putExtra("markstype","Monthly test 2");
                stuResult.putExtra("sclass",getIntent().getStringExtra("sclass"));
                stuResult.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(stuResult);
            }
        });


        m_test_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stuResult = new Intent(getApplicationContext(),stuResult.class);
                stuResult.putExtra("markstype","Monthly test 3");
                stuResult.putExtra("sclass",getIntent().getStringExtra("sclass"));
                stuResult.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(stuResult);
            }
        });





        f_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stuResult = new Intent(getApplicationContext(),stuResult.class);
                stuResult.putExtra("markstype","First term");
                stuResult.putExtra("sclass",getIntent().getStringExtra("sclass"));
                stuResult.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(stuResult);
            }
        });

        s_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stuResult = new Intent(getApplicationContext(),stuResult.class);
                stuResult.putExtra("markstype","Second term");
                stuResult.putExtra("sclass",getIntent().getStringExtra("sclass"));
                stuResult.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(stuResult);
            }
        });



        t_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stuResult = new Intent(getApplicationContext(),stuResult.class);
                stuResult.putExtra("markstype","Third term");
                stuResult.putExtra("sclass",getIntent().getStringExtra("sclass"));
                stuResult.putExtra("sregid",getIntent().getStringExtra("sregid"));
                startActivity(stuResult);
            }
        });
    }


}
