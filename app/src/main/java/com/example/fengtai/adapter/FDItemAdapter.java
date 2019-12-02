package com.example.fengtai.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fengtai.R;
import com.example.fengtai.entity.Item;

import java.util.ArrayList;

public class FDItemAdapter extends RecyclerView.Adapter<FDItemAdapter.ItemViewHolder> {
    private ArrayList<Item> items;
    private Fragment context;
    private Fragment fragment;

    public FDItemAdapter(ArrayList<Item> items, Fragment context, Fragment fragment) {
        this.items = items;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_fdmember, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int i) {
        final Item item = items.get(i);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragment != null) {
                    //为Fragment指定当前选定项
                    Bundle bundle = new Bundle();
                    bundle.putString("id", item.getId());
                    fragment.setArguments(bundle);

                    context.getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        holder.name.setText(item.getItem());
        holder.info.setText(item.getValue());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView name;
        private TextView info;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.tTab);
            info = itemView.findViewById(R.id.tInfo);

        }
    }
}
