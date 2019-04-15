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
    //기존 PagerAdapter에서 DownloadUnzip을 통해 down 및 unzip 하던것을 LodingActivitiy에서 대신하고 categories만 받아오는 걸로 변경
    public PagerAdapter(FragmentManager fm, Context context, ArrayList<String> Pre_categories) {
        super(fm);
        Log.d(TAG, "constructor");
        this.context = context;

        //Loading에서 받아온 categories를 입력
        categories = Pre_categories;

        //서버의 저장된 .zip 파일을 folder_names에 확장자를 지우고 저장해서 내부 저장소의 디렉토리 이름으로 사용
        for (int i = 0; i < categories.size(); i++) {
            String temp_tabName = categories.get(i).substring(0, categories.get(i).lastIndexOf('.'));
            folder_names.add(temp_tabName);
        }

        //Fragment 몇 개를 만들 것인지 category의 개수를 보고 결정
        PAGE_COUNT = categories.size();

        menuManager = new MenuManager(context.getFilesDir().getAbsolutePath() +"", folder_names);//TODO:test서버 사용시엔 getAbsolutePath() + "/test"
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
