package com.example.fengtai.activity.breeddoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.fengtai.R;
import com.example.fengtai.adapter.MyPagerAdapter;
import com.example.fengtai.fragment.Fragment_pasture;
import com.example.fengtai.fragment.breeddoc.Fragment_baseMsg;
import com.example.fengtai.fragment.breeddoc.Fragment_breedDocMsg;

public class BreedsAllMessagesActivity extends AppCompatActivity {
    private String breedId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_allmsg);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        Fragment_breedDocMsg fragment = new Fragment_breedDocMsg();
        // todo 指定养殖ID

        Bundle bundle = this.getIntent().getExtras();
        breedId = bundle.getString("id");
        System.out.print("breedId" + breedId);
        bundle.putString("breedId", breedId);
        fragment.setArguments(bundle);

        Fragment_baseMsg baseMsg = new Fragment_baseMsg();
        baseMsg.setArguments(bundle);

        adapter.addFragment(baseMsg, "基础信息");
        adapter.addFragment(fragment, "档案信息");

        adapter.addFragment(new Fragment_pasture(), "基础信息");
        adapter.addFragment(new Fragment_pasture(), "基础信息");
        adapter.addFragment(new Fragment_pasture(), "基础信息");
        adapter.addFragment(new Fragment_pasture(), "基础信息");
        adapter.addFragment(new Fragment_pasture(), "基础信息");
        adapter.addFragment(new Fragment_pasture(), "基础信息");

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tab);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("selected", 0));
        tabLayout.setupWithViewPager(viewPager);
    }


}
