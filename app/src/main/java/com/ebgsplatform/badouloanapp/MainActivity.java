package com.ebgsplatform.badouloanapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ebgsplatform.badouloanapp.faceiddemo.IDCardAutherActivity;
import com.ebgsplatform.badouloanapp.faceiddemo.InputIdCardActivity;
import com.ebgsplatform.badouloanapp.faceiddemo.LiveResultActivity;
import com.ebgsplatform.badouloanapp.utils.ToastAlert;
import com.ebgsplatform.badouloanapp.utils.dialog.AlertDialogManager;
import com.ebgsplatform.badouloanapp.utils.permission.PermissionManager;
import com.ebgsplatform.badouloanapp.utils.permission.PermissionProhibitedListener;
import com.ebgsplatform.badouloanapp.utils.permission.RequestPermissionCallback;
import com.ebgsplatform.badouloanapp.utils.preferences.PreferenceManager;
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

                PermissionManager.performWithPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                        .showRationaleBeforeRequest("请授予相机权限以启用功能")
                        .doOnProhibited(new PermissionProhibitedListener() {
                            @Override
                            public void onProhibited(String permission) {
                                PermissionManager.showPermissionProhibitedDialog(MainActivity.this, permission);
                            }
                        })
                        .perform(this, new RequestPermissionCallback() {
                            @Override
                            public void onGranted() {
                                //                                AlertDialogManager.showAlertDialog("权限已授予");





                                startActivity(new Intent(MainActivity.this, IDCardAutherActivity.class));
                            }

                            @Override
                            public void onDenied() {
                                AlertDialogManager.showAlertDialog("未授予权限");
                            }
                        });



                break;
            case R.id.btn_load:
                //头像认证 验证头像入口


                PermissionManager.performWithPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                        .showRationaleBeforeRequest("请授予相机权限以启用功能")
                        .doOnProhibited(new PermissionProhibitedListener() {
                            @Override
                            public void onProhibited(String permission) {
                                PermissionManager.showPermissionProhibitedDialog(MainActivity.this, permission);
                            }
                        })
                        .perform(this, new RequestPermissionCallback() {
                            @Override
                            public void onGranted() {
                                //                                AlertDialogManager.showAlertDialog("权限已授予");
                                String name = PreferenceManager.getDefault().getString("name","");
                                String idCardNum = PreferenceManager.getDefault().getString("idNum","");
                                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(idCardNum)){
                                    startActivity(new Intent(MainActivity.this, InputIdCardActivity.class));
                                }else{
                                    startActivityForResult(new Intent(MainActivity.this, LivenessActivity.class), PAGE_INTO_LIVENESS);
                                }
                            }

                            @Override
                            public void onDenied() {
                                AlertDialogManager.showAlertDialog("未授予权限");
                            }
                        });

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
