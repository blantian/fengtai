package com.example.fengtai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.fengtai.MainActivity;
import com.example.fengtai.R;

public class SplashActivity extends AppCompatActivity {
    private TextView splashView;
    //定义组合动画
    private AnimationSet set;
    private int anInt = 2000;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        splashView = findViewById(R.id.name);

        //公用一个动画部件
        set = new AnimationSet(true);
        set .setDuration(anInt);
        //动画执行完之后保持状态
        set.setFillAfter(true);

        //缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1);
        scaleAnimation.setDuration(anInt);
        set.addAnimation(scaleAnimation);

        //平移
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,-200);
        translateAnimation.setDuration(anInt);
        set.addAnimation(translateAnimation);

        //执行动画
        splashView.startAnimation(set);
        //动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.sendEmptyMessageDelayed(100,500);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
