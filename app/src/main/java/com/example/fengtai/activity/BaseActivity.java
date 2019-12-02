package com.example.fengtai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.fengtai.util.ActivityController;

public class BaseActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

        void autoStartActivity(Class T){
        Intent intent = new Intent(this,T);
        startActivity(intent);
    }
}
