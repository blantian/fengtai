package com.example.fengtai

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.fengtai.activity.LoginActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, LoginActivity::class.java)
//        val intent = Intent(this, BreedsAllMessagesActivity::class.java)
//        val intent = Intent(this, AddBreedActivity::class.java)

        startActivity(intent)
        finish()

    }


}