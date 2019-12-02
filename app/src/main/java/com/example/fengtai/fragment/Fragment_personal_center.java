package com.example.fengtai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.activity.AboutUsActivity;
import com.example.fengtai.activity.personalcenter.FindActivity;
import com.example.fengtai.activity.personalcenter.PersonalActivity;
import com.example.fengtai.entity.UserInfoResult;
import com.example.fengtai.entity.base.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_personal_center extends Fragment {


    private Handler mHandler = new Handler();
    private TextView bttnFind;
    private TextView bttnMymsg;
    private TextView bttnAboutUs;
    private Button bttn_quite;
    private ImageView userHead;
    private TextView HeadName;
    private TextView Username;

    private void InitComponent(){

        bttnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FindActivity.class);
                startActivity(intent);
            }
        });
        bttnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        bttnMymsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                startActivity(intent);
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.activity_personal_center, container, false);
         return view;

    }

    public void onStart(){
        super.onStart();
        View view = getView();
        bttnFind = view.findViewById(R.id.bttn_find);
        bttnMymsg = view.findViewById(R.id.bttn_my_msg);
        bttnAboutUs = view.findViewById(R.id.bttn_about_us);
        bttn_quite =view.findViewById(R.id.bttn_quit);
        userHead = view.findViewById(R.id.ic_head);
        HeadName = view.findViewById(R.id.ic_head_name);
        Username = view.findViewById(R.id.perusername);
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
                                    HeadName.setText(response.body().getData().getUsername());
                                    Username.setText(response.body().getData().getUsername());
                                    Glide.with(getContext()).load(response.body().getData().getAvatar()).into(userHead);
                                    InitComponent();
                                }

                                @Override
                                public void onFailure(Call<Result<UserInfoResult>> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
