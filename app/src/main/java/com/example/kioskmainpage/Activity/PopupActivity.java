package com.example.kioskmainpage.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kioskmainpage.MenuManage.Menu;
import com.example.kioskmainpage.R;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PopupActivity extends AppCompatActivity {

    private static final String TAG = "popup**testtest";
    ArrayList<MultiStateToggleButton> buttons = new ArrayList<>();
    ArrayList<Integer> before_state = new ArrayList<>();
    Menu menu;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        //상,하단의 바를 제거하고 풀스크린으로 만듬
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        //인텐트를 받아와서 레이아웃 생성
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        menu = intent.getParcelableExtra("menu");
        before_state = intent.getIntegerArrayListExtra("options");
        Log.d(TAG, "get index : " +index);

        TextView tv_menu_name = (TextView) findViewById(R.id.menuName);
        tv_menu_name.setText(menu.getMenu_name());
        tv_menu_name.setPaintFlags(tv_menu_name.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);

        TextView tv_menu_price = (TextView) findViewById(R.id.menuPrice);
        tv_menu_price.setText(menu.getMenu_price() + "원");

        Bitmap bitmap = null;
        File imageFile = new File(menu.getBitmap());
        bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        for (int i = 0; i < menu.getOptions().size(); i++) {
            //옵션 이름 생성
            TextView option_name = new TextView(this);
            option_name.setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams optionNameParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            optionNameParam.setMargins(10, 0, 0, 0);
            option_name.setLayoutParams(optionNameParam);
            option_name.setText(menu.getOptions().get(i).getOption_name());

            //토글버튼을 생성하고 옵션 엔트리를 적용
            MultiStateToggleButton multiStateToggleButton = new MultiStateToggleButton(this);
            LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonParam.setMargins(10, 0, 0, 15);
            multiStateToggleButton.setLayoutParams(buttonParam);
            multiStateToggleButton.setColors(getResources().getColor(R.color.black), getResources().getColor(R.color.snow));
            ArrayList<String> optionElements = menu.getOptions().get(i).getOptions();
            multiStateToggleButton.setElements((List<String>) menu.getOptions().get(i).getOptions());

            //새롭게 메뉴를 선택하는 건지, 기존 선택 된 메뉴의 변경인지에 따라서 토글버튼 초기 선택 값 지정
            //boolean array형태로 만들어서 적용해야함
            if(before_state==null){
                boolean[] state = new boolean[optionElements.size()];
                state[0] = true;
                multiStateToggleButton.setStates(state);
            }
            else{
                boolean[] before_button_state = new boolean[optionElements.size()];
                before_button_state[before_state.get(i)]=true;
                multiStateToggleButton.setStates(before_button_state);
            }

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.optionLayout);
            linearLayout.addView(option_name);
            linearLayout.addView(multiStateToggleButton);

            buttons.add(multiStateToggleButton);
        }

    }


    public void mOnClose(View v) {

        //취소 버튼 선택시 바로 종료해버림
        if (v.getId() == R.id.cancelButton) {
            //setResult(RESULT_CANCELED);
            finish();
            return;
        }

        //버튼의 현재 선택 위치를 int arraylist 형태로 저장해서 넘겨줌
        ArrayList<Integer> choices = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getValue() < 0) {
                //버튼이 하나라도 선택 안되어있으면 선택해 달라고 Toast를 띄우고 return
                Toast.makeText(this, "선택해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                choices.add(buttons.get(i).getValue());
            }
        }

        Intent intent = new Intent();
        intent.putExtra("index", index);
        Log.d(TAG, "put index : " + index);
        intent.putExtra("menu", menu);
        intent.putIntegerArrayListExtra("options", choices);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //팝업 액티비티의 바깥쪽 터치를 무시하도록
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //하단 네비게이션 바를 숨겨놨지만 최하단을 위쪽으로 드래그하면 바가 나와서 뒤로가기 버튼 막음
        return;
    }
}
