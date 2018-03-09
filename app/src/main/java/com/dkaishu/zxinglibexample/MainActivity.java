package com.dkaishu.zxinglibexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dkaishu.zxinglib.activity.CaptureActivity;
import com.dkaishu.zxinglib.activity.CaptureFragment;
import com.dkaishu.zxinglib.activity.CodeUtils;

public class MainActivity extends AppCompatActivity {
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final  int    REQUEST_CODE  = 111;
    public static final  int    REQUEST_IMAGE = 112;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.iv_qr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
         /*选择系统图片并解析*/
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
/*
        else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(this, "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();
        }*/
    }


    public void selectPic(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void startCapture(View view) {
        Intent intent = new Intent(getApplication(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void startCustomActivity(View view) {
        Intent intent = new Intent(getApplication(), CustomActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void generateQR(View view) {
        Bitmap Bitmap = CodeUtils.createImage("https://github.com/Dkaishu/ZXingLib", 300, 300
                , BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        imageView.setImageBitmap(Bitmap);
    }

}
