package com.ebgsplatform.badouloanapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ebgsplatform.badouloanapp.faceiddemo.IDCardAutherActivity;
import com.ebgsplatform.badouloanapp.faceiddemo.LiveResultActivity;
import com.ebgsplatform.badouloanapp.utils.ToastAlert;
import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.livenesslib.LivenessActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final int EXTERNAL_STORAGE_REQ_CAMERA_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        netWorkWarranty();
    }

    @OnClick({R.id.btn_auther,R.id.btn_load})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_auther:
                //实名认证 验证身份证入口

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //进行权限请求
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQ_CAMERA_CODE);
                    } else {
                        startActivity(new Intent(this, IDCardAutherActivity.class));
                    }
                }

                break;
            case R.id.btn_load:
                //头像认证 验证头像入口

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //进行权限请求
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQ_CAMERA_CODE);
                    } else {
                        startActivityForResult(new Intent(this, LivenessActivity.class), PAGE_INTO_LIVENESS);
                    }
                }
                break;
        }
    }

    private static final int PAGE_INTO_LIVENESS = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAGE_INTO_LIVENESS && resultCode == RESULT_OK) {

            Bundle bundle=data.getExtras();
            Intent intent = new Intent(this,LiveResultActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
//            LiveResultActivity.startActivity(this, bundle);
        }
    }


    /**
     * 联网授权
     */
    private void netWorkWarranty() {
        ToastAlert.showToast(MainActivity.this,"联网授权中……");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(MainActivity.this);
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(MainActivity.this);
                manager.registerLicenseManager(licenseManager);
                String uuid = "13213214321424";
                manager.takeLicenseFromNetwork(uuid);

                if (licenseManager.checkCachedLicense() > 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"联网授权成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(MainActivity.this,"联网授权失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).start();
    }
}
