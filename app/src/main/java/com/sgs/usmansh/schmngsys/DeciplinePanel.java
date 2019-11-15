package com.sgs.usmansh.schmngsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeciplinePanel extends AppCompatActivity {

    RaiflatButton all_classes;
    Button menuPopup;
    DatabaseReference setlogout_decipline;
    FirebaseAuth mAuth_decipline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decipline_panel);


        all_classes = (RaiflatButton)findViewById(R.id.class_list_panel);
        mAuth_decipline = FirebaseAuth.getInstance();
        setlogout_decipline = FirebaseDatabase.getInstance().getReference("LoginStatus");



        all_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent all_class = new Intent(getApplicationContext(),staffClassPanel.class);
                startActivity(all_class);
            }
        });



        menuPopup = (Button)findViewById(R.id.menu_decipline);
        menuPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //pop up menu
                showPopUpMenu();
            }
        });


    }

    private void showPopUpMenu() {

        final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),menuPopup);

        popupMenu.getMenuInflater().inflate(R.menu.menubar,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                //if user select Signout option in popup menu
                if(menuItem.getTitle().equals("SignOut")){

                    setlogout_decipline.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("offline");
                    mAuth_decipline.signOut();
                    Intent gotoMainLogin = new Intent(getApplicationContext(),MainLoginAct.class);
                    gotoMainLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(gotoMainLogin);
                    finish();

                }
                return true;
            }
        });

        popupMenu.show();

    }
}
