package com.example.kioskmainpage.MenuManage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.util.Log;
import android.widget.Toast;

import com.example.kioskmainpage.ServerConn.DownloadUnzip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ErrorManager;

public class MenuManager {
    ArrayList<Menu> menus = new ArrayList<>();
    ArrayList<String> menu_names = new ArrayList<>();
    ArrayList<String> menu_prices = new ArrayList<>();
    ArrayList<String> menu_images = new ArrayList<>();

    String menu_folder;

    String saveDir;
    String menu_name_path = "/menu_name.txt";
    String menu_price_path = "/menu_price.txt";
    String menu_folder_path = "/category.txt";
    String menu_option_path = "/menu_option.txt";

    ArrayList<String> parsedOption;

    ArrayList<String> tabNames = new ArrayList<>();

    ArrayList<String> folder_names;

    private static final String TAG = "menumanagertesttest";

    public MenuManager(String saveDir, ArrayList<String> folder_names) {
        this.saveDir = saveDir;
        this.folder_names = folder_names;
        make_all_menus();
    }

    public void make_all_menus() { //모든 메뉴들에 대한 객체 생성

        for (int i = 0; i < folder_names.size(); i++) {

            File save_file = new File(saveDir + "/" + folder_names.get(i));
            File image_folder = new File(saveDir + "/" + folder_names.get(i) + "/images");
            File imageFileNames[] = image_folder.listFiles();

            //이미지 파일은 저장 경로만 가지고있다가 이미지 버튼에 넣을 때 디코드
            for (File f : imageFileNames) {
                menu_images.add(f.getAbsolutePath());
                Log.d(TAG, "menu images file path : " + f.getAbsolutePath());
            }

            String line = null;
            if (!save_file.exists()) {
                Log.d(TAG, "save file not exist");
                return;
            }
            try {
                //메뉴이름 txt 파일 읽어오기
                FileInputStream fileInputStream = new FileInputStream(new File(saveDir + "/" + folder_names.get(i) + menu_name_path));
                BufferedReader br_name = new BufferedReader(new InputStreamReader(fileInputStream, "EUC-KR"));

                line = br_name.readLine();
                menu_names = new ArrayList<String>(Arrays.asList(line.split("\\|")));
                br_name.close();

                //메뉴가격 txt 파일 읽어오기
                BufferedReader br_price = new BufferedReader(new FileReader(save_file + menu_price_path));
                line = br_price.readLine();
                menu_prices = new ArrayList<String>(Arrays.asList(line.split("\\|")));
                br_price.close();

                //메뉴카테고리 txt 파일 읽어오기
                BufferedReader br_folder = new BufferedReader(new FileReader(save_file + menu_folder_path));
                line = br_folder.readLine();
                menu_folder = line;
                br_folder.close();
                tabNames.add(line);

                //메뉴옵션 txt 파일 읽어오기
                BufferedReader br_option = new BufferedReader(new FileReader(save_file + menu_option_path));
                line = br_option.readLine();
                parsedOption = new ArrayList<String>(Arrays.asList(line.split("@")));
                br_option.close();

                /*
                for (int test = 0; test < parsedOption.size(); test++) {
                    Log.d(TAG, "menu name : " + menu_names.get(test));
                    Log.d(TAG, "menu_price : " + menu_prices.get(test));
                    Log.d(TAG, "category : " + menu_folder);
                    Log.d(TAG, "parsedOption : " + parsedOption.get(test));
                }*/

                if(menu_names.size()<1 || menu_images.size()<1 || menu_prices.size()<1 || menu_folder==null){
                    System.exit(ErrorManager.OPEN_FAILURE);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //옵션 txt파일 읽고 파싱해서 각 메뉴에 넣기
            for (int j = 0; j < menu_names.size(); j++) {
                ArrayList<String> oneOption = new ArrayList<String>(Arrays.asList(parsedOption.get(j).split(",")));
                Log.d(TAG, "oneOption size : " + oneOption.size());
                ArrayList<Option> oneMenuOptions = new ArrayList<Option>();
                for (int k = 0; k < oneOption.size(); k++) {
                    String option_name = oneOption.get(k).substring(0, oneOption.get(k).indexOf("&"));
                    Log.d(TAG, "option name : " + option_name);
                    String entry = oneOption.get(k).substring(oneOption.get(k).indexOf("&") + 1);
                    ArrayList<String> option_entry = new ArrayList<String>(Arrays.asList(entry.split("\\|")));
                    oneMenuOptions.add(new Option(option_name, option_entry));
                }
                Menu menu = new Menu(menu_names.get(j), menu_prices.get(j), menu_folder, menu_images.get(j), oneMenuOptions);
                menus.add(menu);
                Log.d(TAG, menu.getMenu_name() + " " + menu.getMenu_price() + " " + menu.getMenu_folder());
            }
            menu_names.clear();
            menu_prices.clear();
            menu_images.clear();
        }
    }

    public ArrayList<Menu> getMenus(String category) {
        ArrayList<Menu> result = new ArrayList<>();

        for (int i = 0; i < menus.size(); i++) {
            Menu m = menus.get(i);
            if (m.menu_folder.equals(category))
                result.add(m);
        }
        return result;
    }

    public ArrayList<String> getTabNames() {
        return tabNames;
    }
}
