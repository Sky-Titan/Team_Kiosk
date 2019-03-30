package com.example.kioskmainpage.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kioskmainpage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    String signup_url="http://mobilekiosk.co.kr/admin/api/company.php";
    String result_json;//회원가입 요청후 리턴값 받아옴

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
        String hp;
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
            EditText edit_hp = (EditText)findViewById(R.id.editSignUpHP);

            bizName = edit_biz_name.getText().toString();
            bizNum = edit_biz_num.getText().toString();
            password1 = edit_password.getText().toString();
            password2 = edit_password2.getText().toString();
            address = edit_address.getText().toString()+edit_address2.getText().toString();
            email = edit_email.getText().toString();
            hp = edit_hp.getText().toString();

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
                System.out.println("insertdata시작");
                InsertData insertData = new InsertData();
                insertData.execute(signup_url,bizNum,bizName,password1,email,hp,address);

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
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SignUpActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            result_json=result;
            progressDialog.dismiss();
            validReturnValue();
        }


        @Override
        protected String doInBackground(String... params) {

            String mb_id = (String)params[1];
            String mb_name = (String)params[2];
            String mb_password = (String)params[3];
            String mb_email = (String)params[4];
            String mb_hp = (String)params[5];
            String mb_addr1= (String)params[6];

            String serverURL = (String)params[0];

            String postParameters = "mb_id="+mb_id+"&mb_name=" + mb_name + "&mb_password="+mb_password + "&mb_email="+mb_password+"&mb_hp="+mb_hp+"&mb_addr1="+mb_addr1;
            System.out.println(postParameters);
            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                System.out.println("json 리턴: "+sb.toString());
                bufferedReader.close();


                return sb.toString();
            }catch (Exception e) {

                return new String("Error: " + e.getMessage());
            }

        }
    }
    protected void validReturnValue(){//서버에 회원가입 요청 보내고 온 정보 json 형태로 받고 pasing
        try{
            JSONObject jsonObj = new JSONObject(result_json);
            String resultOfreturn=jsonObj.getString("result");
            String msgOfreturn = jsonObj.getString("msg");
            System.out.println(resultOfreturn);
            System.out.println(msgOfreturn);
            if(resultOfreturn.equals("Y"))//회원가입완료
            {
                Intent intent = new Intent(SignUpActivity.this,SignUpPopupActivity.class);
                startActivity(intent);
                finish();
            }
            else//회원가입 실패
            {
                Toast.makeText(SignUpActivity.this,msgOfreturn,Toast.LENGTH_SHORT).show();
            }



        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
