package com.example.kioskmainpage.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kioskmainpage.R;

public class BestNewMenu extends AppCompatActivity {

    Button allmenu;
    ImageButton best;
    ImageButton new1,new2,new3;
    TextView best_name;
    TextView new1_name,new2_name,new3_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_new_menu);

        allmenu=(Button) findViewById(R.id.allmenu_bestmenu);
        allmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        best=(ImageButton)findViewById(R.id.bestimg_bestmenu);
        new1=(ImageButton)findViewById(R.id.newimg1_bestmenu);
        new2=(ImageButton)findViewById(R.id.newimg2_bestmenu);
        new3=(ImageButton)findViewById(R.id.newimg3_bestmenu);

        best_name=(TextView)findViewById(R.id.bestname_bestmenu);
        new1_name=(TextView)findViewById(R.id.newname1_bestmenu);
        new2_name=(TextView)findViewById(R.id.newname2_bestmenu);
        new3_name=(TextView)findViewById(R.id.newname3_bestmenu);

        best.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        new3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
