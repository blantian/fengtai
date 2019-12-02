package com.example.fengtai.fragment.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BaseFragment extends Fragment {
    private int layoutId;

    public BaseFragment() {

    }

    @SuppressLint("ValidFragment")
    public BaseFragment(int layoutId) {
        this.layoutId = layoutId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutId, container, false);
        rootView.setClickable(true);     //把View的click属性设为true，截断点击时间段扩散 解决点击穿透
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    backPrev();
                    return true;
                }
                return false;
            }
        });
    }

    public void backPrev() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();

    }

}

