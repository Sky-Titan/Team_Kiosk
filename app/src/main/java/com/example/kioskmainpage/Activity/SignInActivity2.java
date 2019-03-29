package com.example.kioskmainpage.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kioskmainpage.R;

public class SignInActivity2 extends AppCompatActivity {

    private static final String TAG = "testtest**signIn2";
    EditText edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_sign_in2);

        //상,하단 바 제거
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        Intent data = getIntent();
        String bizNum = data.getStringExtra("bizNum");
        Log.d(TAG, "biznum : " +bizNum);

        edit_password = (EditText)findViewById(R.id.editPassword);
        edit_password.setHint("7~11자리 비밀번호");

        Button confirmButton = (Button)findViewById(R.id.passwordConfirmButton);
        confirmButton.setOnClickListener(new confirmOnClick());

    }
    public class confirmOnClick implements View.OnClickListener{
        //TODO: 서버로 넘겨주고 서버에서 비밀번호 인증 구현
        @Override
        public void onClick(View view) {
            String password = edit_password.getText().toString();
            if(passwordValidator(password)) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
            else{
                Toast.makeText(view.getContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    public boolean passwordValidator(String password) {
        if(password.length()<6 || password.length()>12) {
            return false;
        } else
            return true;
    }
}
