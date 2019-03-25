package com.example.kioskmainpage.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.kioskmainpage.Myapplication;
import com.example.kioskmainpage.R;

import org.w3c.dom.Text;

public class CompleteOrder_PayActivity extends AppCompatActivity implements View.OnClickListener{
    public int exN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order__pay);

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

        Button btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(this);

        //주문번호 출력
        TextView order_num = (TextView)findViewById(R.id.order_num);
        order_num.setText(String.valueOf(exN));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFinish://주문완료
                finish();


                break;
        }
    }
}
