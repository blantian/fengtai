package com.example.fengtai.activity.breeddoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.BreedsDocAdapter;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.breed.BreedIndexResult;
import com.example.fengtai.entity.breed.BreedsResult;
import com.example.fengtai.util.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsDocActivity extends AppCompatActivity {


    private String id;
    private TextView showDocname;

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breedsdoc);
        bindView();
        getAPIData();

        //获取传过来的养殖牲畜id
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");



    }

    private void bindView() {

        showDocname = findViewById(R.id.showdocname);
        button = findViewById(R.id.btn_creatDoc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getAPIData() {

        final RecyclerView recyclerView = findViewById(R.id.breeds_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerDecoration(this));
        //获取户主养殖列表
        MyApplication.API.getBreedStatistics(MyApplication.userId).enqueue(new Callback<Result<ArrayList<BreedsResult>>>() {

            @Override
            public void onResponse(Call<Result<ArrayList<BreedsResult>>> call, Response<Result<ArrayList<BreedsResult>>> response) {
                final ArrayList<BreedsResult> breedsData = response.body().getData();
                for (int i = 0; i < breedsData.size(); i++) {
                    if (breedsData.get(i).getId().equals(id)) {
                        //显示档案对应的牲畜名字
                        showDocname.setText(breedsData.get(i).getName());
                        //获取对应养殖牲畜id下的数据列表
                        MyApplication.API.getBreedIndex(MyApplication.userId, id).enqueue(new Callback<Result<ArrayList<BreedIndexResult>>>() {

                            @Override
                            public void onResponse(Call<Result<ArrayList<BreedIndexResult>>> call, Response<Result<ArrayList<BreedIndexResult>>> response) {
                                final List<BreedIndexResult> breedIndexResults = response.body().getData();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView.setAdapter(new BreedsDocAdapter(breedIndexResults, getBaseContext(), id));
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Result<ArrayList<BreedIndexResult>>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    } else {
                        continue;
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<BreedsResult>>> call, Throwable t) {

            }
        });
    }
}
