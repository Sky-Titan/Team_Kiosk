package com.example.kioskmainpage.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.kioskmainpage.R;

public class PayingPopupActivity extends Activity {
    private static final String TAG = "testtest**complete";
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_paying_popup);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);


        final ProgressDialog oDialog= new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        //   oDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        oDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        oDialog.setMessage("결제중입니다.");
        oDialog.show();

        //결제중입니다 dialog
        Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                oDialog.dismiss();
                startActivity(new Intent(PayingPopupActivity.this, CompleteOrder_PayActivity.class));
                finish();


            }
        };
        mHandler.sendEmptyMessageDelayed(0,3000);
        //결제중입니다 3초 delay
    }
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;

    }

}
