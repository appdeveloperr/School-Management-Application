package com.sgs.usmansh.schmngsys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Usman Sh on 3/29/2018.
 */

public class myAdpter extends BaseAdapter {

        ArrayList<Teacher> techlist;

        Context contxt= null;
        Teacher tech = null;


        public  myAdpter (Context c, ArrayList<Teacher> tec)

        {

            this.techlist = tec;
            this.contxt =c;
        }


        @Override
        public View getView(int i, View view, ViewGroup parent)

        {

            // Toast.makeText(contxt, "In Adaptor", Toast.LENGTH_SHORT).show();

            View V = new View (contxt);

            LayoutInflater inflat= (LayoutInflater) contxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            if (view==null){
                V = inflat.inflate(R.layout.listrow,null);
            } else{
                V = view;
            }


            assert view != null;
            TextView name = (TextView) V.findViewById(R.id.nametv);

            tech = techlist.get(i);

            //id.setText(tech.getTechid());
            name.setText(tech.getTechname());




            return V;



        }

        @Override
        public int getCount() {
            return  techlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }



    }







