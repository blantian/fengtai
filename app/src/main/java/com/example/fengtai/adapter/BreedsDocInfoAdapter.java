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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fengtai.R;
import com.example.fengtai.activity.breeddoc.GetBigPicActivity;
import com.example.fengtai.entity.breed.BreedDoc;

import java.util.ArrayList;

public class BreedsDocInfoAdapter extends RecyclerView.Adapter<BreedsDocInfoAdapter.BreedHolder> {
    private Context context;
    private ArrayList<BreedDoc> list;

    public BreedsDocInfoAdapter(Context context, ArrayList<BreedDoc> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BreedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BreedHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_breeds_msg, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BreedHolder breedHolder, int i) {
        final BreedDoc doc = list.get(i);
        if (doc != null) {
            breedHolder.earMark.setText((doc.getEartag() != null) ? doc.getEartag().getNumber() : "--");
            Glide.with(context).load(doc.getImg()).into(breedHolder.head);
            breedHolder.name.setText((doc.getBreed() != null) ? doc.getBreed().getTitle() : "--");
            breedHolder.age.setText(doc.getAge());
            breedHolder.breedingDay.setText(doc.getPid());
            breedHolder.wight.setText(doc.getWeight());
        }
        breedHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("head",doc.getImg());
                context.startActivity(new Intent(context, GetBigPicActivity.class).putExtras(bundle));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BreedHolder extends RecyclerView.ViewHolder {
        private TextView earMark;
        private ImageView head;
        private TextView name;
        private TextView age;
        private TextView breedingDay;
        private TextView wight;

        public BreedHolder(@NonNull View itemView) {
            super(itemView);
            earMark = itemView.findViewById(R.id.breeds_ear);
            head = itemView.findViewById(R.id.breed_head);
            name = itemView.findViewById(R.id.breed_name);
            age = itemView.findViewById(R.id.breed_age);
            breedingDay = itemView.findViewById(R.id.breed_breeding);
            wight = itemView.findViewById(R.id.breed_wight);
        }
    }
}
