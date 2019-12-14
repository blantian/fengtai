package com.example.fengtai.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.activity.breeddoc.BreedActivity;
import com.example.fengtai.activity.breeddoc.HaveBreedsActivity;
import com.example.fengtai.activity.farmdoc.FarmdocMainActivity;
import com.example.fengtai.activity.farmdoc.Farmdoc_Activity;
import com.example.fengtai.entity.HomeResult;
import com.example.fengtai.entity.breed.BreedsResult;
import com.example.fengtai.entity.farmdoc.HuKou;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeViewHolder> {

    private ArrayList<HomeResult> list;
    private ArrayList<String> pic;
    private List<BreedsResult> breedsResults = new ArrayList<>();
    private Context context;
    private String path;//要获取的路径
    private Handler mHandler = new Handler();

    public HomeListAdapter(ArrayList<HomeResult> list, Context context) {
        this.list = list;
        this.context = context;

    }



    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
        return new HomeViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int i) {
        final HomeResult item = list.get(i);
        //todo 渲染主页
        Glide.with(context).load(item.getProduct_pic()).into(holder.icon);
        holder.name.setText(item.getProduct_name());
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 可在此处跳转具体页面
                if (item.getProduct_id().equals("2")) {
                    //根据是否有名判断是否为新注册用户
                    MyApplication.API.getHuKouList(MyApplication.userId).enqueue(new Callback<Result<ArrayList<HuKou>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<HuKou>>> call, Response<Result<ArrayList<HuKou>>> response) {
                            if (response.body().getCode() == 200 && response.body().getData().get(0).getName() != null) {

                                Intent intent = new Intent(context, FarmdocMainActivity.class);
                                context.startActivity(intent);
                            } else {
                                Util.makeToast(context, "请填写牧户档案");
                                Intent intent = new Intent(context, Farmdoc_Activity.class);
                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<HuKou>>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }
                if(item.getProduct_id().equals("5")){
                    //判断有没有户主信息
                    MyApplication.API.getHuKouList(MyApplication.userId).enqueue(new Callback<Result<ArrayList<HuKou>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<HuKou>>> call, Response<Result<ArrayList<HuKou>>> response) {
                            final Result<ArrayList<HuKou>> getDta = response.body();
                            if (getDta.getCode() == 200 && getDta.getData().get(0).getName()!=null){
                                String id = response.body().getData().get(0).getId();
                                MyApplication.API.getHuKou(id).enqueue(new Callback<Result<HuKou>>() {
                                    @Override
                                    public void onResponse(Call<Result<HuKou>> call, Response<Result<HuKou>> response) {
                                        if (response.body().getData().getName()!=null){
                                            //如果有户主信息，再判断用户有没有建立养殖档案
                                             MyApplication.API.getBreedStatistics(MyApplication.userId).enqueue(new Callback<Result<ArrayList<BreedsResult>>>() {
                                                @Override
                                                 public void onResponse(Call<Result<ArrayList<BreedsResult>> >call, Response<Result<ArrayList<BreedsResult>>> response) {
                                                       List<BreedsResult> str = response.body().getData();
                                                           for (int i =0;i<str.size();i++){
                                                               if(str.get(i).getCount()!=null){
                                                                   breedsResults.add(str.get(i));
                                                               }
                                                               else {
                                                                    continue;
                                                               }
                                                           }
                                                               if (breedsResults.size()>0){
                                                                    Intent intent = new Intent(context, HaveBreedsActivity.class);
                                                                    context.startActivity(intent);
                                                               }else {
                                                                   Intent intent = new Intent(context, BreedActivity.class);
                                                                   context.startActivity(intent);
                                                           }
                                                       }

                                               @Override
                                                   public void onFailure(Call<Result<ArrayList<BreedsResult>>> call, Throwable t) {
                                                       t.printStackTrace();

                                               }
                                          });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Result<HuKou>> call, Throwable t) {

                                    }
                                });

                            }else {
                                Intent intent = new Intent(context,BreedActivity.class);
                                context.startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<HuKou>>> call, Throwable t) {
                            t.printStackTrace();

                        }
                    });


                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 绑定控件变量
     */
    class HomeViewHolder extends RecyclerView.ViewHolder {    //holder是继承RecycleView的用法基本与gridview相同

        private ImageView icon;
        private TextView name;

        HomeViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            name = itemView.findViewById(R.id.item_name);
        }

    }



}
