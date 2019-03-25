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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "testtest**signup";
    String bizName;
    String bizNum;
    String address;
    String email;
    String password1;
    String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_sign_up);


        //상,하단 바 제거
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        Button confirmButton = (Button)findViewById(R.id.signUpConfirmButton);
        confirmButton.setOnClickListener(new signUpOnClick());
    }

    public class signUpOnClick implements View.OnClickListener{

        String bizName;
        String bizNum;
        String password1;
        String password2;
        String address;
        String email;

        @Override
        public void onClick(View view) {
            EditText edit_biz_name = (EditText)findViewById(R.id.editBizName);
            EditText edit_biz_num = (EditText)findViewById(R.id.editBizNum);
            edit_biz_num.setHint("10자리 숫자");
            EditText edit_password = (EditText)findViewById(R.id.editSignUpPassword);
            edit_password.setHint("7~11 자리의 모든 문자 입력 가능");
            EditText edit_password2 = (EditText)findViewById(R.id.editSignUpPassword2);
            edit_password2.setHint("비밀번호 다시 입력");
            EditText edit_address = (EditText)findViewById(R.id.editBizAddress);
            EditText edit_address2 = (EditText)findViewById(R.id.editBizAddress2);
            EditText edit_email = (EditText)findViewById(R.id.editEmail);
            edit_email.setHint("~~~@~~~.com 형식 입력" );

            bizName = edit_biz_name.getText().toString();
            bizNum = edit_biz_num.getText().toString();
            password1 = edit_password.getText().toString();
            password2 = edit_password2.getText().toString();
            address = edit_address.getText().toString()+edit_address2.getText().toString();
            email = edit_email.getText().toString();


            if(!bizNumValidator(bizNum)){
                Toast.makeText(view.getContext(), "사업자번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }else if(!passwordValidator(password1,password2)){
                Toast.makeText(view.getContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }else if(!emailValidator(email)){
                Toast.makeText(view.getContext(), "이메일주소를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }else {
                //TODO:서버에 회원가입 정보를 넘겨주고 등록절차 구현
                Intent i = new Intent(view.getContext(), SignUpPopupActivity.class);
                startActivity(i);
            }
        }
    }

    public boolean passwordValidator(String pw1, String pw2){ //패스워드가 같은지, 길이가 맞는지 검사함
        if(pw1!= null && pw1.equals(pw2) && pw1.length()<12 && pw1.length()>6)
            return true;
        else
            return false;
    }

    public boolean emailValidator(String email) { //email 형식이 맞는지 아닌지만 검사함
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean bizNumValidator(String bizNum){ //사업자 번호가 숫자10자리가 맞는지 검사함
        Pattern pattern;
        final String bizNum_PATTERN = "^\\d{10}$";
        pattern = Pattern.compile(bizNum_PATTERN);
        return pattern.matcher(bizNum).matches();
    }
}
