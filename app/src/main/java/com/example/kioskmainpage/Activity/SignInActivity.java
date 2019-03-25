package com.example.kioskmainpage.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kioskmainpage.R;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "testtest**signIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_sign_in);

        //상,하단 바 제거
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        EditText EditBizNum = (EditText)findViewById(R.id.biz_num);
        EditBizNum.setHint("10자리 숫자");

        Button signUpButton =  (Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        Button confirmButton = (Button)findViewById(R.id.bizNumConfirmButton);
        confirmButton.setOnClickListener(new bizNumConfrimListener());
    }

    public class bizNumConfrimListener implements View.OnClickListener{
        EditText EditBizNum;

        @Override
        public void onClick(View view) {
            EditBizNum= (EditText)findViewById(R.id.biz_num);
            String biz_num = EditBizNum.getText().toString();
            //TODO: 서버에서 회원인증절차 구현
            if(biz_num.length()!=10){
                Toast.makeText(view.getContext(), "사업자번호를 제대로 입력해주세요",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                Intent i = new Intent(view.getContext(), SignInActivity2.class);
                i.putExtra("bizNum", biz_num);
                startActivity(i);
            }
        }
    }
}
