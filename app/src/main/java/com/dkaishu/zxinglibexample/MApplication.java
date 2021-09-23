package com.dkaishu.zxinglibexample;

import android.app.Application;

import com.dkaishu.zxinglib.activity.ZXingLib;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLib.initDisplayOpinion(this);
    }
}
