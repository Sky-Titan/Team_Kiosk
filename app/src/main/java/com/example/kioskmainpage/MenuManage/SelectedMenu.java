package com.example.kioskmainpage.MenuManage;

import java.util.ArrayList;

public class SelectedMenu extends Menu { //메뉴를 터치해 옵션을 고른후 선택버튼을 눌렀을 때 하단에 표시할 메뉴 객체를 생성하기위함
    private int cnt;
    private ArrayList<Integer> choices;

    public SelectedMenu(String menuName, String menuPrice, String bitmap, String category, ArrayList<Option> options, ArrayList<Integer> choices) {
        super(menuName, menuPrice, category, bitmap, options);
        this.choices = choices;
        cnt = 1;
    }

    public void setChoices(ArrayList<Integer> choices) {
        this.choices = choices;
    }

    public int getCnt() {
        return cnt;
    }

    @Override
    public ArrayList<Option> getOptions() {
        return super.getOptions();
    }

    @Override
    public String getMenu_folder() {
        return super.getMenu_folder();
    }

    @Override
    public String getBitmap() {
        return super.getBitmap();
    }

    @Override
    public String getMenu_name() {
        return super.getMenu_name();
    }

    @Override
    public String getMenu_price() {
        return super.getMenu_price();
    }

    public ArrayList<Integer> getChoices() {
        return choices;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

}
