package com.ebgsplatform.badouloanapp.faceiddemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ebgsplatform.badouloanapp.R;
import com.ebgsplatform.badouloanapp.http.HttpApi;
import com.ebgsplatform.badouloanapp.http.HttpRequest;
import com.ebgsplatform.badouloanapp.module.IDCardFrontInfo;
import com.ebgsplatform.badouloanapp.module.VerifyResultInfo;
import com.ebgsplatform.badouloanapp.utils.FileUtil;
import com.ebgsplatform.badouloanapp.utils.ToastAlert;
import com.ebgsplatform.badouloanapp.utils.preferences.PreferenceManager;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.megvii.idcardlib.IDCardScanActivity;
import com.megvii.idcardlib.view.AutoRatioImageview;
import com.megvii.idcardquality.IDCardQualityLicenseManager;
import com.megvii.licensemanager.Manager;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IDCardAutherActivity extends AppCompatActivity {

    @BindView(R.id.sfz_layout_frontImage)
    AutoRatioImageview mImgFront;
    @BindView(R.id.sfz_layout_backImage)
    AutoRatioImageview mImgBack;
    private static final int IDCARD_REQUEST = 101;
    private String fileFront;
    private String fileBack;
    private String portraitImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_auther);
        ButterKnife.bind(this);

        networkAuther();

        PreferenceManager.getDefault().clear();

    }

    private void networkAuther() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(IDCardAutherActivity.this);
                IDCardQualityLicenseManager idCardLicenseManager = new IDCardQualityLicenseManager(
                        IDCardAutherActivity.this);
                manager.registerLicenseManager(idCardLicenseManager);
                String uuid = "13213214321424";
                manager.takeLicenseFromNetwork(uuid);
                String contextStr = manager.getContext(uuid);
                Log.w("ceshi", "contextStr====" + contextStr);

                Log.w("ceshi",
                        "idCardLicenseManager.checkCachedLicense()===" + idCardLicenseManager.checkCachedLicense());
                if (idCardLicenseManager.checkCachedLicense() > 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(IDCardAutherActivity.this,"联网授权成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(IDCardAutherActivity.this,"联网授权失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).start();
    }


    @OnClick({R.id.sfz_layout_frontImage,R.id.sfz_layout_backImage,R.id.sfz_layout_shibieBtn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.sfz_layout_frontImage:
                //身份证正面

                startIDCardScan(this,0,IDCARD_REQUEST);
                break;
            case R.id.sfz_layout_backImage:
                //身份证反面
                startIDCardScan(this,1,IDCARD_REQUEST);
                break;
            case R.id.sfz_layout_shibieBtn:
                //上传并识别
                verify(portraitImg);
                break;
        }
    }

    private void verify(String portraitImg) {
        HttpParams params = new HttpParams();
        params.put("api_key","2DfHqYDLIoCe7En1PcS68aWeIVXgR6d-");
        params.put("api_secret","Hb8PePiI97eDRDq4pLJa9W6insCs_cI5");
        params.put("comparison_type","1");
        params.put("face_image_type","raw_image");
        params.put("idcard_name",PreferenceManager.getDefault().getString("name",""));
        params.put("idcard_number",PreferenceManager.getDefault().getString("idNum",""));
        params.put("image",new File(portraitImg));

        HttpRequest.formUpload(HttpApi.VERIFY, this, params, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {




                Gson gson = new Gson();
                VerifyResultInfo verifyResultInfo = gson.fromJson(response.body().toString(), VerifyResultInfo.class);

                if(!TextUtils.isEmpty(verifyResultInfo.getError_message())){
                    ToastAlert.showToast(IDCardAutherActivity.this,verifyResultInfo.getError_message());
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("result",String.valueOf(verifyResultInfo.getResult_faceid().getConfidence()));

                Intent intent = new Intent(IDCardAutherActivity.this,TotalResultActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });


    }

    private void uploadAndOCR(String filePath) {

        HttpParams params = new HttpParams();
        params.put("api_key","2DfHqYDLIoCe7En1PcS68aWeIVXgR6d-");
        params.put("api_secret","Hb8PePiI97eDRDq4pLJa9W6insCs_cI5");
        params.put("image",new File(filePath));


        HttpRequest.formUpload(HttpApi.OCRIDCARD, this, params, new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                Logger.e(response.toString());

                Gson gson = new Gson();
                IDCardFrontInfo idCardFrontInfo = gson.fromJson(response.body().toString(), IDCardFrontInfo.class);


                if("front".equals(idCardFrontInfo.getSide())){

                    if(TextUtils.isEmpty(idCardFrontInfo.getId_card_number()) || TextUtils.isEmpty(idCardFrontInfo.getName())){
                        ToastAlert.showToast(IDCardAutherActivity.this,"信息获取不全，请上传一张清晰的身份证照片");
                    }
                    PreferenceManager.getDefault().put("idNum",idCardFrontInfo.getId_card_number());
                    PreferenceManager.getDefault().put("name",idCardFrontInfo.getName());
                    PreferenceManager.getDefault().put("address",idCardFrontInfo.getAddress());
                    PreferenceManager.getDefault().put("race",idCardFrontInfo.getRace());
                    PreferenceManager.getDefault().put("gender",idCardFrontInfo.getGender());
                    PreferenceManager.getDefault().put("nirthday_year",idCardFrontInfo.getBirthday().getYear());
                    PreferenceManager.getDefault().put("nirthday_month",idCardFrontInfo.getBirthday().getMonth());
                    PreferenceManager.getDefault().put("nirthday_day",idCardFrontInfo.getBirthday().getDay());
                }else if("back".equals(idCardFrontInfo.getSide())){
                    if(TextUtils.isEmpty(idCardFrontInfo.getIssued_by()) || TextUtils.isEmpty(idCardFrontInfo.getValid_date())){
                        ToastAlert.showToast(IDCardAutherActivity.this,"信息获取不全，请上传一张清晰的身份证照片");
                    }
                    PreferenceManager.getDefault().put("issued_by",idCardFrontInfo.getIssued_by());
                    PreferenceManager.getDefault().put("valid_date",idCardFrontInfo.getValid_date());
                }



            }


        });
    }

    public static void startIDCardScan(Activity activity, int side, int i) {
        if ( activity != null) {
            Intent intent = new Intent(activity, IDCardScanActivity.class);
            intent.putExtra("side", side);
            activity.startActivityForResult(intent, i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            switch (requestCode){
                case IDCARD_REQUEST:

                    IdCard idCard = data.getIntExtra("side", 0) == 0 ? IdCard.IDCARD_SIDE_FRONT : IdCard.IDCARD_SIDE_BACK;
                    byte[] byteArrayExtra = data.getByteArrayExtra("idcardImg");
                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length);

                    if (idCard == IdCard.IDCARD_SIDE_FRONT) {
                        mImgFront.setImageBitmap(decodeByteArray);
                        fileFront = FileUtil.saveFile(IDCardAutherActivity.this, idCard.toString()+".jpg", decodeByteArray);

                        uploadAndOCR(fileFront);

                        byte[] byteArrayExtra2 = data.getByteArrayExtra("portraitImg");
                        Bitmap decodeByteArray2 = BitmapFactory.decodeByteArray(byteArrayExtra2, 0, byteArrayExtra2.length);
                        if (decodeByteArray2 != null) {
                            portraitImg = FileUtil.saveFile(IDCardAutherActivity.this, "portraitImg.jpg", decodeByteArray2);
                        }
                    }else if (idCard == IdCard.IDCARD_SIDE_BACK) {
                        mImgBack.setImageBitmap(decodeByteArray);
                        fileBack = FileUtil.saveFile(IDCardAutherActivity.this, idCard.toString() +".jpg", decodeByteArray);

                        uploadAndOCR(fileBack);

//                        m3909a(decodeByteArray, false, false);
                        Toast.makeText(IDCardAutherActivity.this,"保存图片成功",Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        return;
                    }
                    break;

            }


        }
    }
}
