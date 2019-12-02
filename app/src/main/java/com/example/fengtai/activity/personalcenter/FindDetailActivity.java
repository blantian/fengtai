package com.example.fengtai.activity.personalcenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.fengtai.R;

public class FindDetailActivity extends AppCompatActivity {

    private TextView titleFindDetail;
    private TextView addtimeFindDetail;
    private TextView contentFindDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_detail);
        bindView();
        Bundle bundle = getIntent().getExtras();
        titleFindDetail.setText(bundle.getString("title"));
        addtimeFindDetail.setText(bundle.getString("addtime"));
        contentFindDetail.setText(bundle.getString("content"));

    }
    private void bindView(){
        titleFindDetail = findViewById(R.id.title_find_detail);
        addtimeFindDetail = findViewById(R.id.addtime_find_detail);
        contentFindDetail = findViewById(R.id.content_find_detail);
    }
}
