package com.example.fengtai.activity.personalcenter;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.FindListAdapter;
import com.example.fengtai.entity.personalcenter.FindResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.DividerDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindActivity extends AppCompatActivity {

    private Handler mHandler =new Handler();
    RefreshLayout refreshLayout;
    private ImageButton findBack;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        initComponents();
        setListeners();
        getData();
    }

    /**
     * 网络请求
     */
    public void getData(){
        final RecyclerView recyclerView = this.findViewById(R.id.recycview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerDecoration (this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication.API.getFind("1").enqueue(new Callback<Result<ArrayList<FindResult>>>() {
                                @Override
                                public void onResponse(Call<Result<ArrayList<FindResult>>> call, Response<Result<ArrayList<FindResult>>> response) {
                                    final ArrayList<FindResult> findResults = response.body().getData();
                                    System.out.print("findResult" + findResults);
                                    recyclerView.setAdapter(new FindListAdapter(findResults,getBaseContext()));
                                }

                                @Override
                                public void onFailure(Call<Result<ArrayList<FindResult>>> call, Throwable t) {

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


    private void initComponents(){
        findBack = findViewById(R.id.find_back);
    }
    private  void setListeners(){
        findBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
