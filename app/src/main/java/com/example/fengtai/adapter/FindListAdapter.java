package com.example.fengtai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.activity.personalcenter.FindDetailActivity;
import com.example.fengtai.entity.personalcenter.FindDetailResult;
import com.example.fengtai.entity.personalcenter.FindResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindListAdapter extends RecyclerView.Adapter<FindListAdapter.FindViewHolder>{

    private ArrayList<FindResult> list;
    private List<FindDetailResult> findDetailResults = new ArrayList<>();
    private Context context;

    public FindListAdapter(ArrayList<FindResult> list,Context context){
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public FindListAdapter.FindViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find,viewGroup,false);
        return new FindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindViewHolder findViewHolder, int i) {
        final FindResult itemBeans = list.get(i);
        Glide.with(context).load(itemBeans.getPicname()).into(findViewHolder.icon);
        findViewHolder.titel.setText(itemBeans.getTitle());
        findViewHolder.addTime.setText(itemBeans.getAddtime());
        //判断对应的ID
        findViewHolder.nextPages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MyApplication.API.getFindDetail(itemBeans.getId()).enqueue(new Callback<Result<ArrayList<FindDetailResult>>>() {
                 @Override
                 public void onResponse(Call<Result<ArrayList<FindDetailResult>>> call, Response<Result<ArrayList<FindDetailResult>>> response) {
                     final List<FindDetailResult> findDetailResults = response.body().getData();
                       for (int i = 0;i<findDetailResults.size();i++){
                        if (itemBeans.getId().equals(findDetailResults.get(i).getId())){
                            Bundle bundle = new Bundle();
                            bundle.putString("title",findDetailResults.get(i).getTitle());
                            bundle.putString("content",findDetailResults.get(i).getContent());
                            bundle.putString("addtime",findDetailResults.get(i).getAddtime());
                            context.startActivity(new Intent(context,FindDetailActivity.class).putExtras(bundle));
                        }else {
                            Util.makeToast(context,"出错");
                        }
                    }
                 }

                 @Override
                 public void onFailure(Call<Result<ArrayList<FindDetailResult>>> call, Throwable t) {
                     t.printStackTrace();

                 }
             });
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FindViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView titel;
        private TextView addTime;
        private LinearLayout nextPages;

        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imag_find);
            titel = itemView.findViewById(R.id.title_find);
            addTime = itemView.findViewById(R.id.addTime_find);
            nextPages = itemView.findViewById(R.id.nextpages);
        }
    }

}
