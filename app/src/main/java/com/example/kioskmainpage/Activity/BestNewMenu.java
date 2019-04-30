package com.example.kioskmainpage.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kioskmainpage.MenuManage.Menu;
import com.example.kioskmainpage.MenuManage.MenuManager;
import com.example.kioskmainpage.R;
import com.example.kioskmainpage.ServerConn.DownloadUnzip;

import java.io.File;
import java.util.ArrayList;

public class BestNewMenu extends AppCompatActivity {

    /* 작성자 : 2019-1 종합설계프로젝트 팀 (팀장 박준현)*/

    Button allmenu;
    ImageButton best;
    ImageButton new1,new2,new3;
    TextView best_name;
    TextView new1_name,new2_name,new3_name;

    private ArrayList<Menu> menus=new ArrayList<>();

    String best_price;
    String new1_price,new2_price,new3_price;

    private int PAGE_COUNT;
    private ArrayList<String> categories;
    private ArrayList<String> folder_names = new ArrayList<>();
    private ArrayList<String> tab_names;
    public Context context;

    private static final String TAG = "testtest**PagerAdapter";
    MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_new_menu);

        //전체 메뉴 보기 버튼(클릭시 BestNewMenu액티비티가 꺼지면서 자연스럽게 MainActivity가 등장
        allmenu=(Button) findViewById(R.id.allmenu_bestmenu);
        allmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

        //best 메뉴와 new메뉴들 image button 객체와 메뉴이름 textview 객체 받아옴
        best=(ImageButton)findViewById(R.id.bestimg_bestmenu);
        new1=(ImageButton)findViewById(R.id.newimg1_bestmenu);
        new2=(ImageButton)findViewById(R.id.newimg2_bestmenu);
        new3=(ImageButton)findViewById(R.id.newimg3_bestmenu);

        best_name=(TextView)findViewById(R.id.bestname_bestmenu);
        new1_name=(TextView)findViewById(R.id.newname1_bestmenu);
        new2_name=(TextView)findViewById(R.id.newname2_bestmenu);
        new3_name=(TextView)findViewById(R.id.newname3_bestmenu);

        //서버에서 다운로드, 압축해제를 위한 DownloadUnzip 객체 생성, 내부저장소 절대경로 전달
        DownloadUnzip downloadUnzip = new DownloadUnzip(this.getFilesDir().getAbsolutePath(),true);
        //서버에서 다운 받아와야할 zip 목록들의 이름을 읽어옴
        categories = downloadUnzip.getFileNames();
        Log.i("categories size : ",categories.size()+"");
        //서버의 저장된 .zip 파일을 folder_names에 확장자를 지우고 저장해서 내부 저장소의 디렉토리 이름으로 사용
        for (int i = 0; i < categories.size(); i++) {
            String temp_tabName = categories.get(i).substring(0, categories.get(i).lastIndexOf('.'));
            folder_names.add(temp_tabName);
        }
        Log.i("folder_names : ",folder_names.get(0));
        //Fragment 몇 개를 만들 것인지 category의 개수를 보고 결정
        PAGE_COUNT = categories.size();


        //전체 다운로드, 압축해제 하는 부분
        //TODO: 지금은 앱이 실행될 때마다 다운로드,압축해제를 실행하는데 서버에 DB가 생기면 그대로 만들고 그냥 실행시 load만 하도록 수정
        downloadUnzip.doDownUnzip();

        menuManager = new MenuManager(this.getFilesDir().getAbsolutePath() +"", folder_names);//TODO:test서버 사용시엔 getAbsolutePath() + "/test"
        tab_names = menuManager.getTabNames();
        Log.i("tabnames : ",tab_names.get(0));
        menus=menuManager.getMenus(tab_names.get(0));//메뉴들 가져옴
        Log.i("menus : ",menus.get(0).menu_name);
        setMenuSetting();//메뉴 이미지 파일, 이름 설정

        //각 메뉴의 imageButton 클릭 시 리스너 등록 (클릭 시 PopupActivity 띄움)
        best.setOnClickListener(new menuOnclick(getApplicationContext(),menus.get(0)));
        new1.setOnClickListener(new menuOnclick(getApplicationContext(),menus.get(1)));
        new2.setOnClickListener(new menuOnclick(getApplicationContext(),menus.get(2)));
        new3.setOnClickListener(new menuOnclick(getApplicationContext(),menus.get(3)));
    }
    public void setMenuSetting()
    {
        Bitmap bitmap = null;
        File imageFile = new File(menus.get(0).getBitmap());
        bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        best.setImageBitmap(bitmap);
        best_name.setText(menus.get(0).menu_name);

        imageFile = new File(menus.get(1).getBitmap());
        bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        new1.setImageBitmap(bitmap);
        new1_name.setText(menus.get(1).menu_name);

        imageFile = new File(menus.get(2).getBitmap());
        bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        new2.setImageBitmap(bitmap);
        new2_name.setText(menus.get(2).menu_name);

        imageFile = new File(menus.get(3).getBitmap());
        bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        new3.setImageBitmap(bitmap);
        new3_name.setText(menus.get(3).menu_name);
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
