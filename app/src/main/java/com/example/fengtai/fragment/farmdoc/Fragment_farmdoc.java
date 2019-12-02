package com.example.fengtai.fragment.farmdoc;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fengtai.R;

public class Fragment_farmdoc extends Fragment {

    int viewStub;

    public static Fragment_farmdoc newInstance(@LayoutRes int LayoutRes) {
        Fragment_farmdoc fragment = new Fragment_farmdoc();
        Bundle args = new Bundle();
        args.putInt("LayoutRes", LayoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewStub = getArguments().getInt("LayoutRes");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        android.view.ViewStub Stub = view.findViewById(R.id.ViewStub);
        Stub.setLayoutResource(viewStub);
        Stub.inflate();
        return view;
    }

}
