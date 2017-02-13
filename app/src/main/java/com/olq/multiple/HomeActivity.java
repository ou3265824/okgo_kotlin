package com.olq.multiple;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.myolq.frame.base.BaseActivity;
import com.olq.multiple.base.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends InitActivity {


//    @BindView(R.id.text1)
//    TextView text1;

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreate() {
//        ButterKnife.bind(this);
        TextView textView= (TextView) findViewById(R.id.text1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
//        text1.setText("点击");
    }


//    @OnClick(R.id.text1)
//    public void onClick() {
//        startActivity(new Intent(this, RegisterActivity.class));
//    }
}
