package com.dkaishu.zxinglib;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public final class CheckPermissionUtils {
    private CheckPermissionUtils() {
    }

    //需要申请的权限
    private static String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE
    };

    //检测权限
    public static String[] checkPermission(Context context) {
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : permissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[0]);
    }
}
