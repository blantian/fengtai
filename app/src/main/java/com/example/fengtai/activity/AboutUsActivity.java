package com.example.fengtai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.fengtai.R;

public class AboutUsActivity extends AppCompatActivity {

    private ImageButton aboutBack;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_as);
        initComponents();
        setListeners();
    }
    private void initComponents(){
        aboutBack = findViewById(R.id.back_about);
    }

    private  void setListeners(){
        aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}