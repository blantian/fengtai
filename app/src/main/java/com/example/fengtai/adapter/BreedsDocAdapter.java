package com.example.fengtai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.activity.breeddoc.BreedDetailActivity;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.breed.BreedIndexResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsDocAdapter extends RecyclerView.Adapter<BreedsDocAdapter.BreedsDocHolder> {
    private List<BreedIndexResult> breedIndexResults;
    private  Context context;
    private String breedId;
    public BreedsDocAdapter(List<BreedIndexResult> breedIndexResults,Context context,String breedId){
        this.breedIndexResults = breedIndexResults;
        this.context=context;
        this.breedId = breedId;

    }

    public String getBreedId() {
        return breedId;
    }

    @NonNull
    @Override
    public BreedsDocHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_breeddos,viewGroup,false);
        return new BreedsDocHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedsDocHolder breedsDocHolder, int i) {
        final BreedIndexResult item = breedIndexResults.get(i);
        breedsDocHolder.showName.setText(item.getTitle());
        breedsDocHolder.showNumber.setText(item.getNumber());
        breedsDocHolder.showTime.setText(item.getAcquisition_time());
        breedsDocHolder.showPriess.setText(item.getBecome_price());


        //给Lyout设置点击事件
        breedsDocHolder.nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.API.getBreedIndex(MyApplication.userId,getBreedId()).enqueue(new Callback<Result<ArrayList<BreedIndexResult>>>() {
                    @Override
                    public void onResponse(Call<Result<ArrayList<BreedIndexResult>>> call, Response<Result<ArrayList<BreedIndexResult>>> response) {
                            List<BreedIndexResult> breedIndexResultList = new ArrayList<>();
                             breedIndexResultList = response.body().getData();
                             for (int i =0;i<breedIndexResultList.size();i++){
                                 if (item.getId().equals(breedIndexResultList.get(i).getId())){
                                     //传送门：API获取的数据传到跳转的页面
                                     Bundle bundle = new Bundle();
                                     bundle.putString("title",breedIndexResultList.get(i).getTitle());
                                     bundle.putString("become_time",breedIndexResultList.get(i).getBecome_time());
                                     bundle.putString("become_price",breedIndexResultList.get(i).getBecome_price());
                                     bundle.putString("number",breedIndexResultList.get(i).getNumber());
                                     bundle.putString("id",breedIndexResultList.get(i).getId());
                                     Intent intent = new Intent(context, BreedDetailActivity.class);
                                     intent.putExtras(bundle);
                                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                     context.startActivity(intent);

                                 }else {
                                     continue;
                                 }
                             }
                    }

                    @Override
                    public void onFailure(Call<Result<ArrayList<BreedIndexResult>>> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return breedIndexResults.size();
    }


    class BreedsDocHolder extends RecyclerView.ViewHolder {
        public TextView showName;
        public TextView showNumber;
        public TextView showTime;
        public TextView showPriess;
        public RelativeLayout nextPage;

          BreedsDocHolder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.breed_name);
            showNumber = itemView.findViewById(R.id.breed_number);
            showTime =itemView.findViewById(R.id.breeds_by_time);
            showPriess =itemView.findViewById(R.id.breed_priess);
            nextPage = itemView.findViewById(R.id.next_page);

        }
    }


}
