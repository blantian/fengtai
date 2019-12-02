package com.example.fengtai.activity.breeddoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.farmdoc.HuKou;
import com.example.fengtai.util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedActivity extends AppCompatActivity {

    private Button addBreed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeddoc);

        addBreed = findViewById(R.id.creatFarmdocBttn);
        addBreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.API.getHuKouList(MyApplication.userId).enqueue(new Callback<Result<ArrayList<HuKou>>>() {
                    @Override
                    public void onResponse(Call<Result<ArrayList<HuKou>>> call, Response<Result<ArrayList<HuKou>>> response) {
                        if(response.body().getCode()==200 && response.body().getData().get(0).getName()!=null){
                            Intent intent = new Intent(BreedActivity.this, AddBreedActivity.class);
                            startActivity(intent);
                        }else {
                            Util.makeToast(BreedActivity.this,"请填写牧户档案");
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<ArrayList<HuKou>>> call, Throwable t) {

                    }
                });


            }
        });
    }
}
