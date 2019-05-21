package com.example.kioskmainpage.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignInActivity2 extends AppCompatActivity {

    private static final String TAG = "testtest**signIn2";
    EditText edit_password;
    String login_url="http://mobilekiosk.co.kr/admin/api/login.php";//서버 url
    String result_json;//로그인 요청후 리턴값 받아옴
    String bizNum;
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

        //SignInActivity에서 넘겨받은 사업자 번호 정보 intent로 넘겨받음
        Intent data = getIntent();
        bizNum = data.getStringExtra("bizNum");
        Log.d(TAG, "biznum : " +bizNum);

        //비밀번호 객체 받아옴
        edit_password = (EditText)findViewById(R.id.editPassword);
        edit_password.setHint("7~11자리 비밀번호");

        //로그인 버튼 리스너
        Button confirmButton = (Button)findViewById(R.id.passwordConfirmButton);
        confirmButton.setOnClickListener(new confirmOnClick());

    }
    public class confirmOnClick implements View.OnClickListener{
        //TODO: 서버로 넘겨주고 서버에서 비밀번호 인증 구현
        @Override
        public void onClick(View view) {
            String password = edit_password.getText().toString();

            if(passwordValidator(password)) {
                //서버에 인증요청 보냄
                InsertData insertData = new InsertData();
                insertData.execute(login_url,bizNum,password);
            }
            else{
                Toast.makeText(view.getContext(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    //비밀번호가 조건에 맞는지 확인
    public boolean passwordValidator(String password) {
        if(password.length()<6 || password.length()>12) {
            return false;
        } else
            return true;
    }

    //server에 인증 요청
    class InsertData extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            result_json=result;
            validReturnValue();
        }


        @Override
        protected String doInBackground(String... params) {

            String mb_id = (String)params[1];
            String mb_password = (String)params[2];


            String serverURL = (String)params[0];

            String postParameters = "mb_id="+mb_id+"&mb_password="+mb_password;
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
            if(resultOfreturn.equals("Y"))//로그인완료
            {
                //로그인 완료되면 LoadingActivity 띄운다.
                Intent intent = new Intent(SignInActivity2.this,LoadingActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            else//로그인 실패
            {
                Toast.makeText(SignInActivity2.this,msgOfreturn,Toast.LENGTH_SHORT).show();
            }



        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
