package com.dkaishu.zxinglibexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dkaishu.zxinglib.activity.CaptureFragment;
import com.dkaishu.zxinglib.activity.CodeUtils;

/**
 * Created by Administrator on 2018/2/27.
 */

public class CustomActivity extends AppCompatActivity {

    private CaptureFragment captureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.custom_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

        TextView textView = (TextView) findViewById(R.id.tv_custom);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeUtils.setLight(true);
            }
        });
    }


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle       = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            CustomActivity.this.setResult(RESULT_OK, resultIntent);
            CustomActivity.this.finish();
//            Intent intent = new Intent(CustomActivity.this, SecondActivity.class);
//            intent.putExtra(CodeUtils.RESULT_STRING, result);
//            startActivity(intent);
//            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle       = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CustomActivity.this.setResult(RESULT_OK, resultIntent);
            CustomActivity.this.finish();
        }
    };
}
