package com.example.kioskmainpage.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.kioskmainpage.R;

import org.w3c.dom.Text;

public class pay_popupActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_popup);
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        findViewById(R.id.btnKaKao).setOnClickListener(this); //카카오 qr 결제버튼
        findViewById(R.id.btnLater).setOnClickListener(this); //후불결제버튼

        Intent intent3 =getIntent();
        String summ =intent3.getStringExtra("sum");
        TextView tv = (TextView)findViewById(R.id.sum);
        tv.setText(summ);
        //총 금액 textview에 입력

    }
    public void onClick(View v)
    {
        switch(v.getId())
        {
            //qr결제 클릭시에 qr_popupactivity로 전환
            case R.id.btnKaKao:
                //   this.finish();
                Intent intent = new Intent (this, Qr_popupActivity.class);
                startActivity(intent);
                finish();

                break;
            //후불결제 클릭시 completorder_nopayActivity로 전환
            case R.id.btnLater:
                Intent intent2 = new Intent (this, CompleteOrder_NopayActivity.class);
                startActivity(intent2);
                finish();
                break;

        }


    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;

    }
}
