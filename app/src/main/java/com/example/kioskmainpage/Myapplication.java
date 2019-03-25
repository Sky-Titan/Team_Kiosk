package com.example.kioskmainpage;

import android.app.Application;

public class Myapplication extends Application {
    private int OrderNum;

    public int getOrderNum()
    {
        return OrderNum;

    }
    public void setOrderNum(int mOrderNum)
    {
        this.OrderNum=mOrderNum;

    }
}
