package com.example.fengtai.activity.breeddoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.fengtai.R;

public class BreedDetailActivity extends AppCompatActivity {

    private TextView titelname, bytime, breedname, breednumber, breedpriess;
    private String sTitelname, sBytime, sBreedname, sBreednumber, sBreedpriess,breedId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_detail);
        BindView();
        //传送门：获取数据
        Bundle bundle = this.getIntent().getExtras();
        breedId = bundle.getString("id");
        sTitelname = bundle.getString("title");
        sBytime = bundle.getString("become_time");
        sBreedname = bundle.getString("title");
        sBreednumber = bundle.getString("number");
        sBreedpriess = bundle.getString("become_price");
        setTextView();


    }

    private void BindView() {
        titelname = findViewById(R.id.titelName);
        bytime = findViewById(R.id.breed_by_time);
        breedname = findViewById(R.id.breed_name);
        breednumber = findViewById(R.id.breed_number);
        breedpriess = findViewById(R.id.breed_priess);

        TextView btn1 = findViewById(R.id.base_msg);
        TextView btn2 = findViewById(R.id.doc_msg);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPage(0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPage(1);
            }
        });

    }

    private void gotoPage(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("selected", id);
        bundle.putString("id",breedId);
        startActivity(new Intent(this, BreedsAllMessagesActivity.class).putExtras(bundle));
    }

    private void setTextView() {
        titelname.setText(sTitelname);
        bytime.setText(sBytime);
        breedname.setText(sTitelname);
        breednumber.setText(sBreednumber);
        breedpriess.setText(sBreedpriess);
    }

}
