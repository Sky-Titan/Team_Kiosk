package com.example.kioskmainpage.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.example.kioskmainpage.Myapplication;
import com.example.kioskmainpage.R;

public class Coupon_Activity extends AppCompatActivity implements View.OnClickListener{

    public int exN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        //전역변수 주문번호 받음
        Myapplication app=(Myapplication) getApplication();
        exN=app.getOrderNum()+1;
        app.setOrderNum(exN);
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
                finish();
                break;
            case R.id.btnCancel_coupon:
                finish();
                break;
        }
    }
}
