package com.example.kioskmainpage.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kioskmainpage.Myapplication;
import com.example.kioskmainpage.R;

public class Coupon_Activity extends Activity implements View.OnClickListener{

    public int exN;
    EditText phonenumber;
    TextView[] numberPad = new TextView[10];
    ImageButton numberPad2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().from(this).inflate(R.layout.activity_coupon,null);
        setContentView(view);


        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        phonenumber = (EditText) findViewById(R.id.phonenumber_edit);

        Button btnFinish = (Button) findViewById(R.id.btnFinish_coupon);
        Button btnCancel = (Button) findViewById(R.id.btnCancel_coupon);
        btnCancel.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        for(int i=0;i<=9;i++) {
            numberPad[i] = (TextView) view.findViewWithTag("numberPad_" + i);
            numberPad[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = phonenumber.getText().toString();
                    System.out.println( ((TextView)view).getText().toString() );
                    number += ((TextView)view).getText().toString();
                    phonenumber.setText(number);

                }
            });
        }
        numberPad2 = (ImageButton) view.findViewWithTag("numberPad_10");//삭제버튼
            numberPad2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = phonenumber.getText().toString();
                    if (!number.equals("")) {
                        phonenumber.setText(number.substring(0, number.length() - 1));
                    }
                }
            });
        //전역변수 주문번호 받음
        Myapplication app=(Myapplication) getApplication();
        exN=app.getOrderNum();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFinish_coupon://주문완료
                /*TODO:전화번호 서버에서 확인 후 적립*/
                /*TODO:완료팝업띄우기*/
                Intent intent = new Intent(this,CouponComplete_Activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnCancel_coupon:
                finish();
                break;
        }
    }
}
