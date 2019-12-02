package com.example.fengtai.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fengtai.R;
import com.example.fengtai.adapter.BottomNavigationAdapter;
import com.example.fengtai.fragment.Fragment_home;
import com.example.fengtai.fragment.Fragment_pasture;
import com.example.fengtai.fragment.Fragment_personal_center;
import com.example.fengtai.util.BottomNavigationAdapterHelper;
import com.example.fengtai.util.Util;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BottomNavigationActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, ViewPager.OnTouchListener, ViewPager.OnClickListener {

    FloatingActionButton floatingActionButton;
    BottomNavigationView bnve;

    @BindView(R.id.btn_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.ma_iv_index)
    ImageView imageView;

    Fragment_home fragment_home;
    Fragment_personal_center fragment_personal_center;
    Fragment_pasture fragment_pasture;
    List<Fragment> fragmentList;
    Unbinder unbinder;
    BottomNavigationAdapter bottomNavigationAdapter;
    BottomNavigationAdapterHelper bottomNavigationAdapterHelper;
    private long mExitTime;
    private Fragment fragment_now = null;
    private int pos = -1;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        this.init();
        imageView = this.findViewById(R.id.ma_iv_index);
        imageView.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    private void init() {
        fragmentList = new ArrayList<>();
        fragment_home = new Fragment_home();
        fragment_personal_center = new Fragment_personal_center();
        fragment_pasture = new Fragment_pasture();

        if (!fragmentList.contains(fragment_home)) {
            fragmentList.add(fragment_home);
        }

        if (!fragmentList.contains(fragment_pasture)) {
            fragmentList.add(fragment_pasture);
        }

        if (!fragmentList.contains(fragment_personal_center)) {
            fragmentList.add(fragment_personal_center);
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationAdapterHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationAdapter = new BottomNavigationAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(bottomNavigationAdapter);
        viewPager.addOnPageChangeListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                pos = 0;
                break;
            case R.id.ma_iv_index:
                // case R.id.navigation_pasture:
                viewPager.setCurrentItem(1);
                pos = 1;
                break;
            case R.id.navigation_notifications:
                viewPager.setCurrentItem(2);

                break;
        }
        return true;
    }


    public void setBackground(List<Fragment> fragments, int position) {


    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.i("onPageSelected", String.valueOf(position));
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

    @Override
    public void onClick(View v) {
        Toast.makeText(BottomNavigationActivity.this, "centent", Toast.LENGTH_LONG);

        viewPager.setCurrentItem(1);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        Log.d("click", "centent");
        Log.d("pos", String.valueOf(pos));


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 按两次返回键退出应用程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 判断间隔时间 大于2秒就退出应用
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                // 应用名
                String applicationName = getResources().getString(
                        R.string.app_name);
                String msg = "再按一次返回键退出" + applicationName;
                //String msg1 = "再按一次返回键回到桌面";
                Util.makeToast(this,msg);
                // 计算两次返回键按下的时间差
                mExitTime = System.currentTimeMillis();
            } else {
                // 关闭应用程序
                finish();
                // 返回桌面操作
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    //@Override
    //public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    //   return false;
    //}
}


