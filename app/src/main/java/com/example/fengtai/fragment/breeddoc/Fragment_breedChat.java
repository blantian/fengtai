package com.example.fengtai.fragment.breeddoc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fengtai.R;
import com.example.fengtai.fragment.base.BaseFragment;
import com.github.mikephil.charting.charts.PieChart;

public class Fragment_breedChat extends BaseFragment {

    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_breed_chat,container,false);
        return view;
    }


}
