package com.example.kioskmainpage.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kioskmainpage.MenuManage.Menu;
import com.example.kioskmainpage.R;

public class BestNewMenu extends AppCompatActivity {

    Button allmenu;
    ImageButton best;
    ImageButton new1,new2,new3;
    TextView best_name;
    TextView new1_name,new2_name,new3_name;

    String best_price;
    String new1_price,new2_price,new3_price;

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

        best_price="4400";
        new1_price="5500";
        new2_price="6600";
        new3_price="2200";
       // best.setOnClickListener(new menuOnclick(getApplicationContext(),new Menu(best_name,best_price,"",)));
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
    public class menuOnclick implements View.OnClickListener{

        Context context;
        Menu menu;

        public menuOnclick(Context context, Menu menu){
            this.context=context;
            this.menu=menu;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(BestNewMenu.this, PopupActivity.class);
            intent.putExtra("menu", menu);
            intent.putIntegerArrayListExtra("options", null);
            startActivity(intent);//기존과 다르게 수정됨
        }
    }
}
