package com.dkaishu.zxinglibexample;

import android.app.Application;

import com.dkaishu.zxinglib.activity.ZXingLib;
import com.squareup.leakcanary.LeakCanary;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ZXingLib.initDisplayOpinion(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
