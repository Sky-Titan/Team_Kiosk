package com.example.kioskmainpage.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.kioskmainpage.R;

public class SignUpPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up_popup);

        //상,하단의 바를 제거하고 풀스크린으로 만듬
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        Button signUpDoneButton = (Button)findViewById(R.id.signUpDoneButton);
        signUpDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //Intent i = new Intent(view.getContext(), SignInActivity.class);
                //startActivity(i);
            }
        });
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
