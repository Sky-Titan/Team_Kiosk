package com.example.kioskmainpage.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kioskmainpage.Activity.MainActivity;
import com.example.kioskmainpage.Activity.PopupActivity;
import com.example.kioskmainpage.MenuManage.Menu;
import com.example.kioskmainpage.MenuManage.SelectedMenu;
import com.example.kioskmainpage.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SelectedListAdapter extends BaseAdapter {
    private static final String TAG = "adaptertesttest";
    LayoutInflater inflater = null;
    private ArrayList<SelectedMenu> selectedMenus = new ArrayList<SelectedMenu>();
    Context context;

    public SelectedListAdapter(ArrayList<SelectedMenu> selectedMenus, Context context) {
        Log.d(TAG, "constructor");
        this.selectedMenus = selectedMenus;
        this.context = context;
    }

    @Override       // adapter 항목의 개수를 조사한다.
    public int getCount() {
        Log.d(TAG, "getCount");
        return selectedMenus.size();
    }

    @Override       // i 위치의 항목을 조사한다.
    public Object getItem(int i) {
        return selectedMenus.get(i);
    }


    @Override       // i 위치의 항목을 id를 리턴한다.
    public long getItemId(int i) { return i;}


    @Override       //position위치의 항목 하나를 출력하기 위한 뷰를 생성하여 리턴한다.
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TAG", "getView");
        //counterView 최초 호출시 판별
        if (convertView == null) {
            final Context context = parent.getContext();
            //inflater는 한 activity에 두개의 layout사용시 필요하다.
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            //다음 호출시부터 사용되는 뷰를 생성한다.
            convertView = inflater.inflate(R.layout.selected_item, parent, false);
        }

        // 메뉴, 가격, 수량의 text와 수량 수정, 변경, 취소의 button을 convertView에 적용시킨다.
        TextView menuNameView = (TextView) convertView.findViewById(R.id.menuName);
        TextView menuPriceView = (TextView) convertView.findViewById(R.id.priceView);
        TextView menuCntView = (TextView) convertView.findViewById(R.id.countView);
        ImageButton plusButton = (ImageButton) convertView.findViewById(R.id.plusButton);
        ImageButton minusButton = (ImageButton) convertView.findViewById(R.id.minusButton);
        Button ModifyButton = (Button) convertView.findViewById(R.id.changeButton);
        Button DeleteButton = (Button) convertView.findViewById(R.id.deleteButton);

        //메뉴 하나당 생기는 줄을 LinearLayout을 생성하여 적용시킨다.
        LinearLayout bottomOptionLayout = (LinearLayout) convertView.findViewById(R.id.bottomOptionLayout);
        //현재 적용된 bottomOptionLayout을 초기화 시킨다.
        bottomOptionLayout.removeAllViews();

        //선택된 메뉴의 아이템의 정보를 받아 추가,감소,삭제,변경 리스너들을 적용시킨다.
        SelectedMenu item = (SelectedMenu) getItem(position);
        plusButton.setOnClickListener(new addListener(item, this));
        minusButton.setOnClickListener(new minusListener(item, this));
        DeleteButton.setOnClickListener(new deleteListener(selectedMenus, position, this, context));
        ModifyButton.setOnClickListener(new modifyListener(selectedMenus, position, context));

        //선택된 메뉴의 가격을 int형으로 변환시킨다.
        int price = Integer.parseInt(selectedMenus.get(position).getMenu_price());
        //선택된 메뉴의 수량들을 int형으로 변환시킨다.
        int cnt = selectedMenus.get(position).getCnt();
        //총 가격을 string 형태로 표현한다.
        String price_for_view = "" + price * cnt;

        //메뉴이름, 가격, 수량을 text로 나타낸다.
        menuNameView.setText(selectedMenus.get(position).getMenu_name());
        menuPriceView.setText(price_for_view + "원");
        String result = "" + selectedMenus.get(position).getCnt();
        menuCntView.setText(result + "개");

        //선택된 메뉴의 옵션을 integer형태의 arraylist로 적용시킨다.
        ArrayList<Integer> options = selectedMenus.get(position).getChoices();
        //선택된 메뉴의 옵션 갯수만큼 반복문
        for (int i = 0; i < selectedMenus.get(position).getOptions().size(); i++) {
            TextView textView = new TextView(context);
            //옵션 값들을 linearlayout 형태로 받기위해 생성
            LinearLayout.LayoutParams optionParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            optionParam.setMargins(30, 0, 0, 0);

            //선택된 옵션들을 textView에 추가
            textView.setLayoutParams(optionParam);
            String optionName = selectedMenus.get(position).getOptions().get(i).getOption_name();
            String selectedOption = selectedMenus.get(position).getOptions().get(i).getOptions().get(options.get(i));
            Log.d(TAG, "bitmap : " + selectedMenus.get(position).getBitmap());

            if (i == 0) {
                textView.setText("ㄴ" + optionName + " : " + selectedOption);
            } else {
                textView.setText(optionName + " : " + selectedOption);
            }
            textView.setTextSize(11);
            //bottomOptionLayout에 textView를 출력
            bottomOptionLayout.addView(textView);
        }

        return convertView;
    }

    //선택된 메뉴 추가
    public void addItem(Menu menu, ArrayList<Integer> choices) {
        Log.d(TAG, "additem, now size : " + selectedMenus.size());
        SelectedMenu selectedMenu = new SelectedMenu(menu.getMenu_name(), menu.getMenu_price(), menu.getBitmap(), menu.getMenu_folder(), menu.getOptions(), choices);
        selectedMenus.add(selectedMenu);
    }

    //선택된 메뉴 초기화
    public void clearItem(SelectedListAdapter adapter) {
        selectedMenus.clear();
        //adapter를 재구성 할 필요가 있을 때 사용
        adapter.notifyDataSetChanged();
    }

    //수량 추가 리스너
    public class addListener implements View.OnClickListener {

        SelectedMenu selectedMenu;
        SelectedListAdapter adapter;

        public addListener(SelectedMenu selectedMenu, SelectedListAdapter adapter) {
            this.selectedMenu = selectedMenu;
            this.adapter = adapter;
        }

        @Override
        public void onClick(View view) {
            selectedMenu.setCnt(selectedMenu.getCnt() + 1);
            adapter.notifyDataSetChanged();
        }
    }

    //수량 감소 리스너
    public class minusListener implements View.OnClickListener {

        SelectedMenu selectedMenu;
        SelectedListAdapter adapter;

        public minusListener(SelectedMenu selectedMenu, SelectedListAdapter adapter) {
            this.selectedMenu = selectedMenu;
            this.adapter = adapter;
        }

        @Override
        public void onClick(View view) {
            if (selectedMenu.getCnt() > 1) {
                selectedMenu.setCnt(selectedMenu.getCnt() - 1);
                adapter.notifyDataSetChanged();
            }
        }
    }

    //삭제 버튼 리스너
    public class deleteListener implements View.OnClickListener {
        ArrayList<SelectedMenu> selectedMenus;
        int index;
        SelectedListAdapter adapter;
        Context context;

        public deleteListener(ArrayList<SelectedMenu> selectedMenus, int index, SelectedListAdapter adapter, Context context) {
            this.selectedMenus = selectedMenus;
            this.index = index;
            this.adapter = adapter;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            selectedMenus.remove(index);
            //메뉴가 리스트에 하나도 없으면 하단 레이아웃을 숨긴다
            if (selectedMenus.size() == 0) ((MainActivity) context).hideMenu();
            adapter.notifyDataSetChanged();
        }
    }

    //변경 버튼 리스너
    public class modifyListener implements View.OnClickListener {
        ArrayList<SelectedMenu> selectedMenus;
        int index;
        Context context;

        public modifyListener(ArrayList<SelectedMenu> selectedMenus, int index, Context context) {
            this.selectedMenus = selectedMenus;
            this.index = index;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent((MainActivity) context, PopupActivity.class);
            //activity를 주고 받기 위해 사용
            i.putExtra("index", index);
            i.putExtra("menu", (Menu) selectedMenus.get(index));
            i.putIntegerArrayListExtra("options", selectedMenus.get(index).getChoices());
            ((MainActivity) context).startActivityForResult(i, 2); //변경버튼에 대한 requestCode == 2
        }
    }

    //Main화면에서 변경 버튼 결과를 받아서 실행 SelectedMenu arraylist에서 해당 메뉴의 옵션을 바꿔줌
    public void onActivityResult(int index, ArrayList<Integer> choices) {
        selectedMenus.get(index).setChoices(choices);
    }

    //총 가격 계산
    public int getPriceSum(){
        int sum=0;
        for(int i=0; i<selectedMenus.size();i++){
            int cnt = selectedMenus.get(i).getCnt();
            int price = Integer.parseInt(selectedMenus.get(i).getMenu_price());
            sum += price*cnt;
        }
        return sum;
    }
}
