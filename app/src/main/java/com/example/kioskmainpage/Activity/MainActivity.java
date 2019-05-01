package com.example.kioskmainpage.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kioskmainpage.Adapter.PagerAdapter;
import com.example.kioskmainpage.Adapter.SelectedListAdapter;
import com.example.kioskmainpage.MenuManage.Menu;
import com.example.kioskmainpage.MenuManage.SelectedMenu;
import com.example.kioskmainpage.Myapplication;
import com.example.kioskmainpage.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "testtest**MAIN";

    ListView selectedListview;
    public SelectedListAdapter adapter;
    public ArrayList<SelectedMenu> selectedMenus = new ArrayList<SelectedMenu>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LoadingActivity로부터 categories를 받아옴
        Intent Pre_categories = getIntent() ;
        ArrayList<String> categories  = Pre_categories.getStringArrayListExtra("categories");

        //상,하단 바 제거
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        //메뉴 선택,확인 시 하단에 추가 될 리스트 뷰 어댑터 생성 및 적용
        selectedListview = (ListView) findViewById(R.id.selected_items);
        adapter = new SelectedListAdapter(selectedMenus, MainActivity.this);
        selectedListview.setAdapter(adapter);

        //Fragment를 생성해서 카테고리별 메뉴를 보여줄 뷰페이져 어댑터 생성 및 적용
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        Log.d(TAG, "set adapter to view pager");
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), MainActivity.this, categories));

        //탭 + 뷰페이져 적용
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        Log.d(TAG, "tablayout setup with view pager");
        tabLayout.setupWithViewPager(viewPager);

        //하단에 메뉴추가시 생기는 버튼 클릭이벤트에 대한 리스너 적용
        onClickButton onClickHandler = new onClickButton(adapter);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(onClickHandler);
        Button payButton = (Button)findViewById(R.id.payButton);
        payButton.setOnClickListener(onClickHandler);

        //주문번호 전역변수를 위함
        Myapplication myapp=(Myapplication) getApplication();
        myapp.setOrderNum(0);
        myapp.getOrderNum();


        //BestNewMenu액티비티 띄움 작성자 : 2019-1 종합설계프로젝트 팀 (팀장 박준현)*/
        Intent intent_BestNewMenu = new Intent(this, BestNewMenuActivity.class);
        intent_BestNewMenu.putExtra("data", "Test Popup");
        startActivityForResult(intent_BestNewMenu, 1);
    }

    public void hideMenu() {
        //하단 레이아웃 visibility를 gone으로 설정해서 공간을 차지하지 않고 보이게 않게 만듬
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bottomLayout);
        linearLayout.setVisibility(View.GONE);
    }

    public class onClickButton implements View.OnClickListener {
        SelectedListAdapter adapter;

        public onClickButton(SelectedListAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cancelButton:
                    //취소 버튼 클릭 시 실행
                    adapter.clearItem(adapter);
                    hideMenu();
                    Toast.makeText(view.getContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.payButton:
                    //주문하기 버튼 클릭 시 실행
                    int price =adapter.getPriceSum();
                    Log.d(TAG,"total price ; " + price);
                    Toast.makeText(view.getContext(),"총 금액 : " + price,  Toast.LENGTH_SHORT).show();

                    Intent intent3=new Intent(view.getContext(),pay_popupActivity.class);
                    intent3.putExtra("sum",price+"원");
                    startActivity(intent3);
                    //주문버튼 누를시에 pay_popupActivity로 전환, 총 금액도 전달
                    adapter.clearItem(adapter);
                    hideMenu();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //팝업 액티비티의 결과를 받는 부분
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1:
                    //메뉴 이미지를 클릭해서 팝업 액티비티를 띄웠을 때 requestCode == 1

                    //인텐트에서 선택된 메뉴의 데이터를 받은 후
                    Menu menu = data.getParcelableExtra("menu");
                    ArrayList<Integer> choices = data.getIntegerArrayListExtra("options");

                    //하단 리스트뷰가 있는 레이아웃 visibility를 VISIBLE로 바꿔 보이도록 설정
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bottomLayout);
                    linearLayout.setVisibility(View.VISIBLE);

                    //어댑터 내부 arraylist안에 추가 후 변화를 알림
                    adapter.addItem(menu, choices);
                    adapter.notifyDataSetChanged();
                    break;

                case 2:
                    //선택된 메뉴리스트에서 변경 버튼을 눌러 팝업 액티비티를 띄웠을 때 requestCode == 2

                    //변경된 메뉴의 adapter 내부 arraylist에서의 인덱스, 바뀐 선택들을 받음
                    int index = data.getIntExtra("index", -1);
                    ArrayList<Integer> mod_choices = data.getIntegerArrayListExtra("options");

                    //어탭터 내부 arraylist의 해당 인덱스 SelectedMenu 객체의 options attribute를 재설정하고 변화를 알림
                    adapter.onActivityResult(index, mod_choices);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }//뒤로가기 버튼 막음
}
