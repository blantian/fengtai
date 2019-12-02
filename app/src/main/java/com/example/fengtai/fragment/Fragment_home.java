package com.example.fengtai.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.HomeListAdapter;
import com.example.fengtai.entity.HomeResult;
import com.example.fengtai.entity.UserInfoResult;
import com.example.fengtai.entity.base.Result;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_home extends Fragment {


    private View view;
    private TextView liUserName;
    private TextView Username;
    public Fragment_home() {

    }


    public static Fragment_home newInstance() {
        return new Fragment_home();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final RecyclerView recyclerView = getView().findViewById(R.id.home_list);
        liUserName = view.findViewById(R.id.liusername);
        Username = view.findViewById(R.id.username);
        //采用３列的网格布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //从应用变量中获取登录后获取的user_id
        MyApplication.API.getHome(RequestBody.create(MediaType.parse(""), MyApplication.userId)).enqueue(new Callback<Result<ArrayList<HomeResult>>>() {
            @Override
            public void onResponse(@NotNull Call<Result<ArrayList<HomeResult>>> call, @NotNull Response<Result<ArrayList<HomeResult>>> response) {
                if (response.body().getStatus() == 200) {
                    final ArrayList<HomeResult> homeResults = response.body().getData();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new HomeListAdapter(homeResults, getContext()));
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call<Result<ArrayList<HomeResult>>> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        MyApplication.API.getUserInfo(MyApplication.userId).enqueue(new Callback<Result<UserInfoResult>>() {
            @Override
            public void onResponse(Call<Result<UserInfoResult>> call, Response<Result<UserInfoResult>> response) {
                Username.setText(response.body().getData().getUsername());
                liUserName.setText(response.body().getData().getUsername());
            }

            @Override
            public void onFailure(Call<Result<UserInfoResult>> call, Throwable t) {

            }
        });

    }
}