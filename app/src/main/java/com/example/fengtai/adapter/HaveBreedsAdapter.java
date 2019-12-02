package com.example.fengtai.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.fengtai.R;
import com.example.fengtai.activity.breeddoc.BreedsDocActivity;
import com.example.fengtai.entity.breed.BreedsResult;

import java.util.ArrayList;
import java.util.List;

public class HaveBreedsAdapter extends RecyclerView.Adapter<HaveBreedsAdapter.HaveBreedsHoder> {

    private List<BreedsResult> breedsResults;
    private Context context;

    public HaveBreedsAdapter(List<BreedsResult> breedsResults,Context context){
        this.breedsResults = breedsResults;
        this.context=context;
    }
    @NonNull
    @Override
    public HaveBreedsHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_breeds,viewGroup,false);
        return new  HaveBreedsHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HaveBreedsHoder haveBreedsHoder, final int i) {
        final BreedsResult item = breedsResults.get(i);

        haveBreedsHoder.ShowNumber.setText((String)item.getCount());
        haveBreedsHoder.ShowName.setText(item.getName());



        //对获取的Breeds设置点击事件
        haveBreedsHoder.Breeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 可在此处跳转具体页面
               if (item.getId().equals("11")){
                   Bundle bundle = new Bundle();
                   bundle.putString("id",item.getId());
                   Intent intent = new Intent(context,BreedsDocActivity.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                   context.startActivity(intent);
               }else if (item.getId().equals("10")){
                   Bundle bundle = new Bundle();
                   bundle.putString("id",item.getId());
                   Intent intent = new Intent(context,BreedsDocActivity.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                   context.startActivity(intent);
               }else if (item.getId().equals("9")){
                   Bundle bundle = new Bundle();
                   bundle.putString("id",item.getId());
                   Intent intent = new Intent(context,BreedsDocActivity.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                   context.startActivity(intent);
               }else if (item.getId().equals("4")){
                   Bundle bundle = new Bundle();
                   bundle.putString("id",item.getId());
                   Intent intent = new Intent(context,BreedsDocActivity.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                   context.startActivity(intent);
               }else if (item.getId().equals("3")){
                   Bundle bundle = new Bundle();
                   bundle.putString("id",item.getId());
                   Intent intent = new Intent(context,BreedsDocActivity.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                   context.startActivity(intent);
               }else if (item.getId().equals("1")){
                   Bundle bundle = new Bundle();
                   bundle.putString("id",item.getId());
                   Intent intent = new Intent(context,BreedsDocActivity.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                   context.startActivity(intent);
               }else if(item.getId().equals("14")){
                   Bundle bundle = new Bundle();
                   bundle.putString("id",item.getId());
                   Intent intent = new Intent(context,BreedsDocActivity.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                   context.startActivity(intent);
               }
            }
        });



    }

    @Override
    public int getItemCount() {
        return breedsResults.size();
    }

    /**
     * 绑定控件item
     */
     class HaveBreedsHoder extends RecyclerView.ViewHolder {
        private TextView ShowName;
        private TextView ShowNumber;
        private LinearLayout Breeds;

        HaveBreedsHoder(@NonNull View itemView) {

            super(itemView);

            Breeds= itemView.findViewById(R.id.breeds);
            ShowName=itemView.findViewById(R.id.showbreedsname);
            ShowNumber=itemView.findViewById(R.id.showbreedsnumber);
        }

    }
    //如果想到动态获取监听就封装点击事件
    public void jump(List<ArrayList> lists){

    }


}
