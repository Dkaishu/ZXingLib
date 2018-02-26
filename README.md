# ZXingLib

[![](https://jitpack.io/v/helen-x/JitpackReleaseDemo.svg)](https://jitpack.io/#Dkaishu/ZXingLib)

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
                compile 'com.github.Dkaishu:ZXingLib:V1.0.2'
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

       CaptureFragment.showFlashLight(true);  //是否显示闪光灯开关，默认不显示
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
         
 - 解析二维码图片

        // 打开图库
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);

        //解析
        if (requestCode == 1) {
                    if (data != null) {
                        Uri uri = data.getData();
                        ContentResolver cr = getContentResolver();
                        try {
                            Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                            CodeUtils.analyzeBitmap(mBitmap, new CodeUtils.AnalyzeCallback() {
                                @Override
                                public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                                    //解析结果：result
                                }

                                @Override
                                public void onAnalyzeFailed() {
                                    //解析失败
                                }
                            });

                            if (mBitmap != null) {
                                mBitmap.recycle();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

  - 生成二维码

        //最后一个参数为null时，则不带logo
        Bitmap Bitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

    - 自定义UI

            //在activity中
            @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_second);
                    CaptureFragment captureFragment = new CaptureFragment();
                    // 一、
                    CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
                    // 二、
                    captureFragment.setAnalyzeCallback(analyzeCallback);
                    // 三、
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
                }


            //根据需求选择性自定义即可，其中 ：
            // 一、调用CondeUtils.setFragmentArgs方法，改变参数进行调整，具体参数有：
            app:inner_width="200dp"
            app:inner_height="200dp"
            app:inner_margintop="150dp"
            app:inner_corner_color="@color/scan_corner_color"
            app:inner_corner_length="30dp"
            app:inner_corner_width="5dp"
            app:inner_scan_bitmap="@drawable/scan_image"
            app:inner_scan_speed="10"
            app:inner_scan_iscircle="false"

            // 二、analyzeCallback 是对扫描结果的回调， new 一个并实现方法即可

            // 三、替换添加扫描控件




    - 其他待补充