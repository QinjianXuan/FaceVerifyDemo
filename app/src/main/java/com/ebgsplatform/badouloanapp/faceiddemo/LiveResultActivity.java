package com.ebgsplatform.badouloanapp.faceiddemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebgsplatform.badouloanapp.R;
import com.ebgsplatform.badouloanapp.faceiddemo.view.RotaterView;
import com.ebgsplatform.badouloanapp.utils.FileUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class LiveResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private ImageView mImageView;
    private LinearLayout ll_result_image;
    private ImageView bestImage, envImage;
    private String delta;
    private Map<String, byte[]> images;
    private String bestImgPath;
    private String envImgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_result);
        init();
    }

    private void init() {
        mImageView = (ImageView) findViewById(R.id.result_status);
        textView = (TextView) findViewById(R.id.result_text_result);
        ll_result_image = (LinearLayout) findViewById(R.id.ll_result_image);
        bestImage = (ImageView) findViewById(R.id.iv_best);
        envImage = (ImageView) findViewById(R.id.iv_env);
        findViewById(R.id.result_next).setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String resultOBJ = bundle.getString("result");

        try {
            JSONObject result = new JSONObject(resultOBJ);
            textView.setText(result.getString("result"));

            int resID = result.getInt("resultcode");
            if (resID == R.string.verify_success) {
                doPlay(R.raw.meglive_success);
            } else if (resID == R.string.liveness_detection_failed_not_video) {
                doPlay(R.raw.meglive_failed);
            } else if (resID == R.string.liveness_detection_failed_timeout) {
                doPlay(R.raw.meglive_failed);
            } else if (resID == R.string.liveness_detection_failed) {
                doPlay(R.raw.meglive_failed);
            } else {
                doPlay(R.raw.meglive_failed);
            }

            boolean isSuccess = result.getString("result").equals(
                    getResources().getString(R.string.verify_success));
            mImageView.setImageResource(isSuccess ? R.drawable.result_success
                    : R.drawable.result_failded);
            if (isSuccess) {
                delta = bundle.getString("delta");
                images = (Map<String, byte[]>) bundle.getSerializable("images");
                if (images.containsKey("image_best")) {
                    byte[] bestImg = images.get("image_best");
                    if (bestImg != null && bestImg.length > 0) {
                        Bitmap bestBitMap = BitmapFactory.decodeByteArray(bestImg, 0, bestImg.length);
                        bestImgPath = FileUtil.saveFile(LiveResultActivity.this, "image_best.jpg", bestBitMap);
                        bestImage.setImageBitmap(bestBitMap);
                    }
                }
                if (images.containsKey("image_env")) {
                    byte[] envImg = images.get("image_env");
                    if (envImg != null && envImg.length > 0) {
                        Bitmap envBitMap = BitmapFactory.decodeByteArray(envImg, 0, envImg.length);
                        envImgPath = FileUtil.saveFile(LiveResultActivity.this, "image_env.jpg", envBitMap);
                        envImage.setImageBitmap(envBitMap);
                    }
                }
                ll_result_image.setVisibility(View.VISIBLE);
//                imageVerify(images,delta);
            } else {
                ll_result_image.setVisibility(View.GONE);
            }
            doRotate(isSuccess);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如何调用Verify2.0方法
     * <p>
     */
    public void imageVerify(Map<String, byte[]> images, String delta) {

        RequestParams requestParams = new RequestParams();
        requestParams.put("idcard_name", "轩勤俭");
        requestParams.put("idcard_number", "411421198911051632");
//        try {
//            requestParams.put("image_ref1", new FileInputStream(new File(
//                    "image_idcard")));// 传入身份证头像照片路径
//        } catch (Exception e) {
//        }
        requestParams.put("delta", delta);
        requestParams.put("api_key", "2DfHqYDLIoCe7En1PcS68aWeIVXgR6d-");
        requestParams.put("api_secret", "Hb8PePiI97eDRDq4pLJa9W6insCs_cI5");

        requestParams.put("comparison_type", "1");
        requestParams.put("face_image_type", "meglive");

        for (Map.Entry<String, byte[]> entry : images.entrySet()) {
            requestParams.put(entry.getKey(),
                    new ByteArrayInputStream(entry.getValue()));
        }
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = "https://api.megvii.com/faceid/v2/verify";
        asyncHttpClient.post(url, requestParams,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String successStr = new String(responseBody);
                        Log.i("result","verify成功："+successStr);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(successStr);
                            if (!jsonObject.has("error_message")) {
                                // 活体最好的一张照片和公安部系统上身份证上的照片比较
                                double confidence = jsonObject.getJSONObject(
                                        "result_faceid")
                                        .getDouble("confidence");

                                Bundle bundle = new Bundle();
                                bundle.putString("result",String.valueOf(confidence));

                                Intent intent = new Intent(LiveResultActivity.this,TotalResultActivity.class);
                                intent.putExtras(bundle);

                                startActivity(intent);

//                                try {
//                                    // 活体最好的一张照片和拍摄身份证上的照片的比较
//                                    JSONObject jObject = jsonObject
//                                            .getJSONObject("result_ref1");
//                                    double idcard_confidence = jObject
//                                            .getDouble("confidence");
//                                    double idcard_threshold = jObject
//                                            .getJSONObject("thresholds")
//                                            .getDouble("1e-3");
//                                    double idcard_tenThreshold = jObject
//                                            .getJSONObject("thresholds")
//                                            .getDouble("1e-4");
//                                    double idcard_hundredThreshold = jObject
//                                            .getJSONObject("thresholds")
//                                            .getDouble("1e-5");
//                                } catch (Exception e) {
//
//                                }
//                                // 解析faceGen
//                                JSONObject jObject = jsonObject
//                                        .getJSONObject("face_genuineness");
//
//                                float mask_confidence = (float) jObject
//                                        .getDouble("mask_confidence");
//                                float mask_threshold = (float) jObject
//                                        .getDouble("mask_threshold");
//                                float screen_replay_confidence = (float) jObject
//                                        .getDouble("screen_replay_confidence");
//                                float screen_replay_threshold = (float) jObject
//                                        .getDouble("screen_replay_threshold");
//                                float synthetic_face_confidence = (float) jObject
//                                        .getDouble("synthetic_face_confidence");
//                                float synthetic_face_threshold = (float) jObject
//                                        .getDouble("synthetic_face_threshold");
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }



                    @Override
                    public void onFailure(int i, Header[] headers,
                                          byte[] bytes, Throwable throwable) {
                        // 请求失败
                        Log.i("result","verify失败");

                    }
                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.result_next) {
//            imageVerify();
            imageVerify(images, delta);
        }
    }

    private void doRotate(boolean success) {
        RotaterView rotaterView = (RotaterView) findViewById(R.id.result_rotater);
        rotaterView.setColour(success ? 0xff4ae8ab : 0xfffe8c92);
        final ImageView statusView = (ImageView) findViewById(R.id.result_status);
        statusView.setVisibility(View.INVISIBLE);
        statusView.setImageResource(success ? R.drawable.result_success
                : R.drawable.result_failded);

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(rotaterView,
                "progress", 0, 100);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(600);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Animation scaleanimation = AnimationUtils.loadAnimation(
                        LiveResultActivity.this, R.anim.scaleoutin);
                statusView.startAnimation(scaleanimation);
                statusView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    private MediaPlayer mMediaPlayer = null;

    private void doPlay(int rawId) {
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();

        mMediaPlayer.reset();
        try {
            AssetFileDescriptor localAssetFileDescriptor = getResources()
                    .openRawResourceFd(rawId);
            mMediaPlayer.setDataSource(
                    localAssetFileDescriptor.getFileDescriptor(),
                    localAssetFileDescriptor.getStartOffset(),
                    localAssetFileDescriptor.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception localIOException) {
            localIOException.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
    }
}
