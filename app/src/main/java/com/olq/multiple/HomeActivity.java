package com.olq.multiple;

import android.content.Intent;
import android.widget.TextView;

import com.myolq.frame.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.text)
    TextView text;

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreate() {

    }


    @OnClick(R.id.text)
    public void onClick() {
        startActivity(new Intent(this,RegisterActivity.class));
    }
}
