package com.olq.multiple;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.myolq.frame.callback.GsonCallBack;
import com.myolq.frame.callback.StringCallBack;
import com.myolq.frame.loader.OkgoLoader;
import com.olq.multiple.base.BaseUrl;
import com.olq.multiple.base.InitActivity;
import com.olq.multiple.bean.BaseBean;
import com.olq.multiple.bean.UsersBean;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends InitActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onCreate() {
//        UsersBean("admin",123456,"123456@qq.com");
//        UsersBean usersBean=new UsersBean("admin","1234562","1234561@qq.com");
        UsersBean users=new UsersBean();
        users.setUsername("a1");
        users.setPassword("123456");
        users.setEmail("w1234561@qq.com");
        OkgoLoader.getInstance().sendByPostUploadingJson(BaseUrl.USERS, users, new StringCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.i("test",s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
//        OkgoLoader.getInstance().sendByPostUploadingJson(BaseUrl.USERS, users, new GsonCallBack<BaseBean<UsersBean>>(new TypeToken<BaseBean>() {}.getType()) {
//            @Override
//            public void onSuccess(BaseBean<UsersBean> usersBeanBaseBean, Call call, Response response) {
//                Log.i("test",usersBeanBaseBean.toString());
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//
//            }
//        });
    }



}
