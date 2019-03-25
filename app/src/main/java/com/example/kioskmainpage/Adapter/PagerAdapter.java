package com.example.kioskmainpage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TextView;

import com.example.kioskmainpage.Fragment.PageFragment;
import com.example.kioskmainpage.MenuManage.MenuManager;
import com.example.kioskmainpage.ServerConn.DownloadUnzip;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT;
    private ArrayList<String> categories;
    private ArrayList<String> folder_names = new ArrayList<>();
    private ArrayList<String> tab_names;
    public Context context;

    private static final String TAG = "testtest**PagerAdapter";
    MenuManager menuManager;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        Log.d(TAG, "constructor");
        this.context = context;

        //서버에서 다운로드, 압축해제를 위한 DownloadUnzip 객체 생성, 내부저장소 절대경로 전달
        DownloadUnzip downloadUnzip = new DownloadUnzip(context.getFilesDir().getAbsolutePath());
        //서버에서 다운 받아와야할 zip 목록들의 이름을 읽어옴
        categories = downloadUnzip.getFileNames();

        //서버의 저장된 .zip 파일을 folder_names에 확장자를 지우고 저장해서 내부 저장소의 디렉토리 이름으로 사용
        for (int i = 0; i < categories.size(); i++) {
            String temp_tabName = categories.get(i).substring(0, categories.get(i).lastIndexOf('.'));
            folder_names.add(temp_tabName);
        }

        //Fragment 몇 개를 만들 것인지 category의 개수를 보고 결정
        PAGE_COUNT = categories.size();


        //전체 다운로드, 압축해제 하는 부분
        //TODO: 지금은 앱이 실행될 때마다 다운로드,압축해제를 실행하는데 서버에 DB가 생기면 그대로 만들고 그냥 실행시 load만 하도록 수정
        downloadUnzip.doDownUnzip();

        menuManager = new MenuManager(context.getFilesDir().getAbsolutePath() + "/test", folder_names);
        tab_names = menuManager.getTabNames();
    }

    @Override
    public int getCount() {
        //Log.d(TAG, "getCount");
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //tab_name에 따라 필요한 메뉴만 Fragment를 생성할 때 전달
        return PageFragment.newInstance(position, tab_names.get(position), menuManager.getMenus(tab_names.get(position)));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_names.get(position);
    }
}
