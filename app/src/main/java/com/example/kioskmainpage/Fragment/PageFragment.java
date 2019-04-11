package com.example.kioskmainpage.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;

import com.example.kioskmainpage.Activity.PopupActivity;
import com.example.kioskmainpage.Adapter.RAdapter;
import com.example.kioskmainpage.MenuManage.Menu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.kioskmainpage.R;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    public static final String TAG = "testtest**PageFragment";
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String NAME_PAGE = "NAME_PAGE";
    public static final String MENU_PAGE = "MENU_PAGE";
    private int mPage;
    private String saveDir;
    private String pagetitle;
    public ArrayList<? extends Menu> menuList;
    private RAdapter adapter;
    public int numOfMenus;
    int num;
    RecyclerView recyclerView;

    public PageFragment() {
        // Required empty public constructor
    }


    public static PageFragment newInstance(int page, String tabName, ArrayList<? extends Parcelable> list) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(NAME_PAGE, tabName);
        args.putParcelableArrayList(MENU_PAGE, list);

        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPage = getArguments().getInt(ARG_PAGE);
        pagetitle = getArguments().getString(NAME_PAGE);

        //FragmentPager 어댑터에서 새로운 Fragment 생성시 추가한 메뉴를 받아옴
        menuList = getArguments().getParcelableArrayList(MENU_PAGE);
        numOfMenus = menuList.size();
        adapter=new RAdapter((ArrayList<Menu>)menuList);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page, container, false);
        //saveDir = view.getContext().getFilesDir().getAbsolutePath();
        ScrollView scrollView = (ScrollView) view;

        //스크롤뷰의 수직 스크롤, 뷰페이저의 횡스크롤 충돌을 막음
        scrollView.requestDisallowInterceptTouchEvent(true);
        createView(view);
        return view;
    }

    public void createView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.menu_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        recyclerView.setAdapter(adapter);



    }
}
