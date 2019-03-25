package com.example.kioskmainpage.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

    @Override
    public int getCount() {
        Log.d(TAG, "getCount");
        return selectedMenus.size();
    }

    @Override
    public Object getItem(int i) {
        return selectedMenus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TAG", "getView");
        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.selected_item, parent, false);
        }

        TextView menuNameView = (TextView) convertView.findViewById(R.id.menuName);
        TextView menuPriceView = (TextView) convertView.findViewById(R.id.priceView);
        TextView menuCntView = (TextView) convertView.findViewById(R.id.countView);
        Button plusButton = (Button) convertView.findViewById(R.id.plusButton);
        Button minusButton = (Button) convertView.findViewById(R.id.minusButton);
        Button ModifyButton = (Button) convertView.findViewById(R.id.changeButton);
        Button DeleteButton = (Button) convertView.findViewById(R.id.deleteButton);

        LinearLayout bottomOptionLayout = (LinearLayout) convertView.findViewById(R.id.bottomOptionLayout);
        bottomOptionLayout.removeAllViews();

        SelectedMenu item = (SelectedMenu) getItem(position);
        plusButton.setOnClickListener(new addListener(item, this));
        minusButton.setOnClickListener(new minusListener(item, this));
        DeleteButton.setOnClickListener(new deleteListener(selectedMenus, position, this, context));
        ModifyButton.setOnClickListener(new modifyListener(selectedMenus, position, context));

        int price = Integer.parseInt(selectedMenus.get(position).getMenu_price());
        int cnt = selectedMenus.get(position).getCnt();
        String price_for_view = "" + price * cnt;

        menuNameView.setText(selectedMenus.get(position).getMenu_name());
        menuPriceView.setText(price_for_view + "원");
        String result = "" + selectedMenus.get(position).getCnt();
        menuCntView.setText(result + "개");

        ArrayList<Integer> options = selectedMenus.get(position).getChoices();
        for (int i = 0; i < selectedMenus.get(position).getOptions().size(); i++) {
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams optionParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            optionParam.setMargins(30, 0, 0, 0);

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
            bottomOptionLayout.addView(textView);
        }

        return convertView;
    }

    public void addItem(Menu menu, ArrayList<Integer> choices) {
        Log.d(TAG, "additem, now size : " + selectedMenus.size());
        SelectedMenu selectedMenu = new SelectedMenu(menu.getMenu_name(), menu.getMenu_price(), menu.getBitmap(), menu.getMenu_folder(), menu.getOptions(), choices);
        selectedMenus.add(selectedMenu);
    }

    public void clearItem(SelectedListAdapter adapter) {
        selectedMenus.clear();
        adapter.notifyDataSetChanged();
    }

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

    public class deleteListener implements View.OnClickListener {
        //삭제 버튼 리스너
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

    public class modifyListener implements View.OnClickListener {
        //변경 버튼 리스너
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
            i.putExtra("index", index);
            i.putExtra("menu", (Menu) selectedMenus.get(index));
            i.putIntegerArrayListExtra("options", selectedMenus.get(index).getChoices());
            ((MainActivity) context).startActivityForResult(i, 2); //변경버튼에 대한 requestCode == 2
        }
    }

    public void onActivityResult(int index, ArrayList<Integer> choices) {
        //Main화면에서 변경 버튼 결과를 받아서 실행 SelectedMenu arraylist에서 해당 메뉴의 옵션을 바꿔줌
        selectedMenus.get(index).setChoices(choices);
    }

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
