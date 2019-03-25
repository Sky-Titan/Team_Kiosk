package com.example.kioskmainpage.MenuManage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.crypto.spec.OAEPParameterSpec;

public class Menu implements Parcelable {
    public String menu_name;
    public String menu_price;
    public String menu_folder;
    public String bitmap;
    public ArrayList<Option> options = new ArrayList<>();

    public Menu() {
    }

    public Menu(String menu_name, String menu_price, String menu_folder, String bitmap, ArrayList<Option> options) {
        this.menu_name = menu_name;
        this.menu_price = menu_price;
        this.menu_folder = menu_folder;
        this.bitmap = bitmap;
        this.options = options;
    }

    public String getMenu_folder() {
        return menu_folder;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public String getBitmap() {
        return bitmap;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public Menu(Parcel in) {
        menu_name = in.readString();
        menu_price = in.readString();
        menu_folder = in.readString();
        bitmap = in.readString();
        in.readTypedList(options, Option.CREATOR);
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public void setMenu_folder(String menu_folder) {
        this.menu_folder = menu_folder;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(menu_name);
        dest.writeString(menu_price);
        dest.writeString(menu_folder);
        dest.writeString(bitmap);
        dest.writeTypedList(options);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
