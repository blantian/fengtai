package com.example.fengtai.activity.breeddoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fengtai.R;

public class GetBigPicActivity extends AppCompatActivity {
    private ImageView bigpics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_big_pic);
        bigpics = findViewById(R.id.bigpic);
        Bundle bundle = getIntent().getExtras();
        Glide.with(GetBigPicActivity.this).load(bundle.get("head")).into(bigpics);

        bigpics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
