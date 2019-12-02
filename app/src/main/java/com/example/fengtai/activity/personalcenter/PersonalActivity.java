package com.example.fengtai.activity.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.entity.UserInfoResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalActivity extends AppCompatActivity {


    Handler mHandler = new Handler();
    private ImageView personalBack;
    private ImageView personalHead;
    private TextView personaName;
    private TextView personalName0;
    private TextView showPersonalName;
    private TextView showPersonalSex;
    private TextView showPersonalPhone;
    private TextView showPersonalEmail;
    private TextView showPersonalIdCard;
    private TextView showPersonalAge;
    private TextView showPersonalAdress;
    private Button personalEdite;
    private String shengData;
    private String shiData;
    private String xianData;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_msg);
        initComponents();
    }
    private void initComponents(){
        personalBack = findViewById(R.id.personal_back);
        //显示信息控件
        personalHead = findViewById(R.id.personal_head);
        personaName = findViewById(R.id.personal_name);
        personalName0 = findViewById(R.id.personal_name0);
        showPersonalName = findViewById(R.id.showpersonalname);
        showPersonalSex = findViewById(R.id.showpersonalsex);
        showPersonalPhone = findViewById(R.id.showpersonalphone);
        showPersonalEmail =findViewById(R.id.showpersonalemail);
        showPersonalIdCard = findViewById(R.id.showpersonalidcard);
        showPersonalAge = findViewById(R.id.showpersonalage);
        showPersonalAdress = findViewById(R.id.showpersonaladress);
        personalEdite = findViewById(R.id.personaledite);

        //获取个人信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   Thread.sleep(0);
                   mHandler.post(new Runnable() {
                       @Override
                       public void run() {
                           MyApplication.API.getUserInfo(MyApplication.userId).enqueue(new Callback<Result<UserInfoResult>>() {
                               @Override
                               public void onResponse(Call<Result<UserInfoResult>> call, Response<Result<UserInfoResult>> response) {
                                   Glide.with(PersonalActivity.this).load(response.body().getData().getAvatar()).into(personalHead);
                                   personaName.setText(response.body().getData().getUsername());
                                   personalName0.setText(response.body().getData().getUsername());
                                   showPersonalName.setText(response.body().getData().getUsername());
                                   if (response.body().getData().getSex().equals("1")){
                                       showPersonalSex.setText("男");
                                   }else {
                                       showPersonalSex.setText("女");
                                   }
                                   showPersonalPhone.setText(response.body().getData().getPhone());
                                   if (response.body().getData().getEmail().equals("")){
                                       showPersonalEmail.setText("请完善信息");
                                   }else {
                                       showPersonalEmail.setText(response.body().getData().getEmail());
                                   }
                                   showPersonalIdCard.setText(response.body().getData().getId_card());
                                   showPersonalAge.setText(response.body().getData().getAge());
                                   shengData = response.body().getData().getShengdata();
                                   xianData = response.body().getData().getXiandata();
                                   shiData = response.body().getData().getShidata();
                                   showPersonalAdress.setText(shengData+shiData+xianData+response.body().getData().getAddress());

                               }

                               @Override
                               public void onFailure(Call<Result<UserInfoResult>> call, Throwable t) {
                                   t.printStackTrace();
                                   Util.makeToast(PersonalActivity.this,"出错");
                               }
                           });
                       }
                   });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        personalEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalActivity.this,PersonalEditeMsgActivity.class));
            }
        });
        personalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
