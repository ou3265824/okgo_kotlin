package com.olq.multiple;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchActivity extends InitActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_launch;
    }

    @Override
    public void onCreate() {
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        };
    }

}
