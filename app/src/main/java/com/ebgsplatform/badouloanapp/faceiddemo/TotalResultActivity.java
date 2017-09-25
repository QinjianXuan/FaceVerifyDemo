package com.ebgsplatform.badouloanapp.faceiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ebgsplatform.badouloanapp.R;
import com.ebgsplatform.badouloanapp.utils.preferences.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TotalResultActivity extends AppCompatActivity {

    @BindView(R.id.tv_result)
    TextView mTvResult;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id_num)
    TextView mTvIdNum;
    @BindView(R.id.tv_address)
    TextView mTvAdress;
    @BindView(R.id.tv_minzu)
    TextView mTvMinzu;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthDay;
    @BindView(R.id.tv_valid_date)
    TextView mTvValidDate;
    @BindView(R.id.tv_issue_by)
    TextView mTvIssueBy;


    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            result = bundle.getString("result");
            mTvResult.setText(result + "%");


            String idNum = PreferenceManager.getDefault().getString("idNum", "");
            String name = PreferenceManager.getDefault().getString("name", "");
            String address = PreferenceManager.getDefault().getString("address", "");
            String race = PreferenceManager.getDefault().getString("race", "");
            String gender = PreferenceManager.getDefault().getString("gender", "");
            String nirthday_year = PreferenceManager.getDefault().getString("nirthday_year", "");
            String nirthday_month = PreferenceManager.getDefault().getString("nirthday_month", "");
            String nirthday_day = PreferenceManager.getDefault().getString("nirthday_day", "");
            String issued_by = PreferenceManager.getDefault().getString("issued_by", "");
            String valid_date = PreferenceManager.getDefault().getString("valid_date", "");

            mTvName.setText("姓名：\t\t\t\t" +name);
            mTvIdNum.setText("身份证号：\t\t\t\t" +idNum);
            mTvAdress.setText("地址：\t\t\t\t" + address);
            mTvMinzu.setText("民族：\t\t\t\t" + race);
            mTvSex.setText("性别：\t\t\t\t" + gender);
            mTvBirthDay.setText("出生日期：\t\t\t\t" + nirthday_year +"-"+ nirthday_month +"-"+ nirthday_day);
            mTvValidDate.setText("有限期限：\t\t\t\t" + valid_date);
            mTvIssueBy.setText("签发机关：\t\t\t\t" + issued_by);

        }
    }
}
