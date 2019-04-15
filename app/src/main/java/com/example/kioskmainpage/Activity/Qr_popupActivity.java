package com.example.kioskmainpage.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kioskmainpage.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Qr_popupActivity extends AppCompatActivity implements View.OnClickListener {
    Bitmap bitmap;
    ImageView QrImage;
    public static int height;
    public static  int width;
    private static final String TAG = "qrpopup**";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_popup);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        QrImage = (ImageView) findViewById(R.id.QrImage);
        Button PayCancel = (Button) findViewById(R.id.PayCancel);
        Button PayRequest = (Button) findViewById(R.id.PayRequest);
        PayCancel.setOnClickListener(this);
        PayRequest.setOnClickListener(this);

        File imgFile = new File(getFilesDir().getAbsolutePath()+"/qrImage.png");// 기존"/qrImage/qrImage.png"에서 "/qrImage.png"로 수정
        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        Log.d(TAG, "file abs path : " + imgFile.getAbsolutePath());
        QrImage.setImageBitmap(bitmap);
        QrImage.setScaleType(ImageView.ScaleType.FIT_XY);

        //서버에서 qr이미지 받아서 띄움


    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;

    }
    public Bitmap rotateImage(Bitmap src, float degree)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(), matrix, true);

    }//이미지 회전함수

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            //주문최소 버튼 누를시에 뒤로 돌아감
            case R.id.PayCancel:
                finish();
                break;

            //결제확인 요청 버튼 누를시에 payingpopupactivity로 전환
            case R.id.PayRequest:
                Intent intent = new Intent (this, PayingPopupActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }
}
