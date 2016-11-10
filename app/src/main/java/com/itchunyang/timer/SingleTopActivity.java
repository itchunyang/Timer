package com.itchunyang.timer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);

        System.out.println(System.currentTimeMillis());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        System.out.println("----->onNewIntent");
        super.onNewIntent(intent);
    }
}
