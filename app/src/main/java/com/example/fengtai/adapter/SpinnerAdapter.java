package com.example.fengtai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fengtai.entity.Item;

import java.util.List;


public class SpinnerAdapter extends BaseAdapter  {

    private List<Item> objects;
    private int resource;
    private Context context;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Item getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        if (convertView instanceof TextView)
            ((TextView) convertView).setText(getItem(position).getItem());

        return convertView;
    }
}
