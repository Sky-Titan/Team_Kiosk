package com.example.kioskmainpage.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kioskmainpage.R;
import com.example.kioskmainpage.ServerConn.DownloadUnzip;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {


    //서버에서 다운로드, 압축해제를 위한 DownloadUnzip 객체 생성, 내부저장소 절대경로 전달
    DownloadUnzip downloadUnzip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        downloadUnzip = new DownloadUnzip(this.getFilesDir().getAbsolutePath(),false);//TODO: 파라매타에서 가져오는걸로 수정해


        CheckTypesTask task = new CheckTypesTask();
        task.execute();

    }
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                LoadingActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("서버로부터 데이터를 받아오는중입니다.");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //전체 다운로드, 압축해제 하는 부분
            //TODO: 지금은 앱이 실행될 때마다 다운로드,압축해제를 실행하는데 서버에 DB가 생기면 그대로 만들고 그냥 실행시 load만 하도록 수정
            downloadUnzip.doDownUnzip();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
            ArrayList<String> categories = downloadUnzip.getFileNames();
            //MainActivity로 이동하기위한 intent
            Intent intent_ToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            intent_ToMainActivity.putExtra("categories", categories);
            startActivity(intent_ToMainActivity);
            finish();
        }
    }

}