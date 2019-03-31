package com.example.kioskmainpage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kioskmainpage.Activity.MainActivity;
import com.example.kioskmainpage.Activity.PopupActivity;
import com.example.kioskmainpage.MenuManage.Menu;
import com.example.kioskmainpage.R;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class RAdapter extends RecyclerView.Adapter<RAdapter.CustomViewHolder> {

    private ArrayList<Menu> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView best;
        protected ImageView menu_image;
        protected TextView menu_name;
        protected TextView menu_price;
        Context mContext;

        public CustomViewHolder(View view, Context context) {
            super(view);
            this.best = (ImageView) view.findViewById(R.id.best_icon_imageview);
            this.menu_image = (ImageView) view.findViewById(R.id.menu_image_view);
            this.menu_name = (TextView) view.findViewById(R.id.menu_name_view);
            this.menu_price = (TextView) view.findViewById(R.id.menu_price_view);
            this.mContext=context;
        }

        public Context getContext() {
            return mContext;
        }
    }
        public RAdapter(ArrayList<Menu> list){
            this.mList = list;
        }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item,viewGroup,false);

        CustomViewHolder viewHolder = new CustomViewHolder(view, viewGroup.getContext());
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewHolder, int position) {
        viewHolder.menu_name.setTextSize(20);
        viewHolder.menu_price.setTextSize(17);

        Menu menu = mList.get(position);

        Bitmap bitmap = null;
        File imageFile = new File(menu.getBitmap());
        bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        FrameLayout.LayoutParams img_param = new FrameLayout.LayoutParams(180, 180);//메인메뉴 원형 이미지들 크기 조절
        img_param.gravity = Gravity.CENTER;

        viewHolder.menu_image.setLayoutParams(img_param);
        viewHolder.menu_image.setImageBitmap(bitmap);
        viewHolder.menu_image.setBackground(new ShapeDrawable(new OvalShape()));
        viewHolder.menu_image.setClipToOutline(true);
        viewHolder.menu_image.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.menu_image.setAdjustViewBounds(true);
        viewHolder.menu_image.setOnClickListener(new menuOnclick(viewHolder.getContext(),menu));

        viewHolder.menu_name.setText(menu.getMenu_name());

        String commaNum = NumberFormat.getInstance().format(Integer.parseInt(menu.getMenu_price()));
        String won = Currency.getInstance(Locale.KOREA).getSymbol();
        viewHolder.menu_price.setText(won + commaNum);

        if(position%2==0){
            viewHolder.best.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class menuOnclick implements View.OnClickListener{

        Context context;
        Menu menu;

        public menuOnclick(Context context, Menu menu){
            this.context=context;
            this.menu=menu;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PopupActivity.class);
            intent.putExtra("menu", menu);
            intent.putIntegerArrayListExtra("options", null);
            ((MainActivity)context).startActivityForResult(intent,1);
        }
    }

}
