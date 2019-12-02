package com.example.fengtai.activity.breeddoc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.HaveBreedsAdapter;
import com.example.fengtai.adapter.MyPagerAdapter;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.breed.BreedsResult;
import com.example.fengtai.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HaveBreedsActivity extends AppCompatActivity {
    private List<BreedsResult> breedsResult = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();

    private Button creatBreedsDoc;
    private int getref = 0;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_breeddoc);
        onStarts();
        bindView();
        setClick();

    }

    private void Getref() {
        Bundle bundle = this.getIntent().getExtras();
        getref = bundle.getInt("ref");
        if (getref == 1) {
        }
    }

    //绑定控件
    public void bindView() {
        creatBreedsDoc = findViewById(R.id.creadBreedDoc);
    }

    //设置点击事件
    public void setClick() {
        creatBreedsDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HaveBreedsActivity.this, AddBreedActivity.class));
            }
        });
    }

    //筛选养殖档案并把数据传到Adapter
    public void onStarts() {
        final RecyclerView recyclerView = findViewById(R.id.recbreeds);
        //使用3列的网络布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(HaveBreedsActivity.this, 3));
        //获取接口数据
        MyApplication.API.getBreedStatistics(MyApplication.userId).enqueue(new Callback<Result<ArrayList<BreedsResult>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<BreedsResult>>> call, Response<Result<ArrayList<BreedsResult>>> response) {
                final List<BreedsResult> dataBeans = response.body().getData();
                for (int i = 0; i < dataBeans.size(); i++) {
                    if (dataBeans.get(i).getCount() != null) {
                        breedsResult.add(dataBeans.get(i));
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new HaveBreedsAdapter(breedsResult, getBaseContext()));
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<Result<ArrayList<BreedsResult>>> call, Throwable t) {

            }
        });

    }

    private void addToView(List<Fragment> fragmentList, ViewPager viewPager, TabLayout tabLayout) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addFragment(fragmentList.get(0),"月");
        myPagerAdapter.addFragment(fragmentList.get(1),"年");
        myPagerAdapter.addFragment(fragmentList.get(2),"自定义");
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void datePicker(final EditText et) {
        DatePickerDialog pickerDialog;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            pickerDialog = new DatePickerDialog(this);
            pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    et.setText(new SimpleDateFormat("YYYY-MM-dd").format(calendar.getTime()));

                }
            });
            pickerDialog.show();
        } else Util.makeToast(this, "系统不支持");

    }


}
