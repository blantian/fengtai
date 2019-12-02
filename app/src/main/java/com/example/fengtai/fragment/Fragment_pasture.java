package com.example.fengtai.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.entity.UserInfoResult;
import com.example.fengtai.entity.base.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_pasture extends Fragment {
    private TextView farmUsername;
    private TextView farmLiusername;
    private View view;
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_pasture,container,false);
        return view;

    }
    public void onStart(){
        super.onStart();
        farmUsername = getView().findViewById(R.id.farmusername);
        farmLiusername = getView().findViewById(R.id.farmliusername);
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
                                    farmUsername.setText(response.body().getData().getUsername());
                                    farmLiusername.setText(response.body().getData().getUsername());
                                }

                                @Override
                                public void onFailure(Call<Result<UserInfoResult>> call, Throwable t) {

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
