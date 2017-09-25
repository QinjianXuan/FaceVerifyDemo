package com.ebgsplatform.badouloanapp.faceiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ebgsplatform.badouloanapp.R;
import com.ebgsplatform.badouloanapp.utils.ToastAlert;
import com.ebgsplatform.badouloanapp.utils.preferences.PreferenceManager;
import com.megvii.livenesslib.LivenessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputIdCardActivity extends AppCompatActivity {

    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_id_card_num)
    EditText mEdtIdCardNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_id_card);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_next:
                //下一步
                String name = mEdtName.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    ToastAlert.showToast(this,"姓名不能为空！");
                    return;
                }
                String idCardNum = mEdtIdCardNum.getText().toString().trim();
                if(TextUtils.isEmpty(idCardNum)){
                    ToastAlert.showToast(this,"身份证号不能为空！！");
                    return;
                }

                PreferenceManager.getDefault().put("name",name);
                PreferenceManager.getDefault().put("idNum",idCardNum);
                //保存姓名和身份证号 跳转头像认证
                startActivityForResult(new Intent(this, LivenessActivity.class), PAGE_INTO_LIVENESS);

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
            finish();
            //            LiveResultActivity.startActivity(this, bundle);
        }
    }

}
