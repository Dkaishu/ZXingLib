# ZXingLib
快速集成：二维码扫描及生成
- 二维码扫描识别
- 二维码生成（支持是否带图标）
- 可自定义扫描界面 UI
- 支持本地二维码图片解析

### 使用
How to

To get the project into your build:

- Step 1. Add the JitPack repository to your build file

        //Add it in your root build.gradle at the end of repositories:
        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }
- Step 2. Add the dependency

        dependencies {
                compile 'com.github.Dkaishu:ZXingLib:v1.0.1'
        }

That's it! Add then:
- 在AndroidManifest.xml 中申明权限：

        <uses-permission android:name="android.permission.CAMERA" />
        <uses-permission android:name="android.permission.FLASHLIGHT" />
        <uses-feature android:name="android.hardware.camera" />
        <uses-feature android:name="android.hardware.camera.autofocus" />
        <uses-permission android:name="android.permission.VIBRATE" /><!--允许访问振动设备-->
        <uses-permission android:name="android.permission.WAKE_LOCK" />
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.INTERNET" />

 - 在 Application 中初始化
 
       @Override
        public void onCreate() {
          super.onCreate();
          ZXingLib.initDisplayOpinion(this);
         }
 
 - 打开扫码界面
 
       Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
       startActivityForResult(intent, REQUEST_CODE);
       
 - onActivityResult 方法中接收扫描结果
 
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
         
 - 其他待补充