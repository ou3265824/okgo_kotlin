package com.olq.multiple;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.myolq.frame.ErrorBean;
import com.myolq.frame.Utils.GsonUtils;
import com.myolq.frame.callback.GsonCallBack;
import com.myolq.frame.callback.StringCallBack;
import com.myolq.frame.loader.OkgoLoader;
import com.olq.multiple.base.BaseActivity;
import com.olq.multiple.base.BaseUrl;
import com.olq.multiple.bean.BaseBean;
import com.olq.multiple.bean.UsersBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import static android.R.attr.password;

public class RegisterActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onCreate() {
//        UsersBean("admin",123456,"123456@qq.com");
//        UsersBean usersBean=new UsersBean("admin","1234562","1234561@qq.com");
        UsersBean users=new UsersBean();
        users.setUsername("d1");
        users.setPassword("123456");
        users.setEmail("d1234561@qq.com");
//        OkGo.post(BaseUrl.USERS)//
//                .tag(this)//
//                .upJson(GsonUtils.getBeanToJson(users))//
////                .upJson(jsonObject.toString())//
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        Log.i("test","onSuccess:"+s);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        Log.i("test","onError");
//                        try {
//                            Toast.makeText(getApplicationContext(),response.body().string(),Toast.LENGTH_LONG).show();
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//
////                        try {
////                            Log.i("test",response.body().string());
////                            Toast.makeText(getApplicationContext(),response.body().string(),Toast.LENGTH_LONG).show();
////                        } catch (IOException e1) {
////                            e1.printStackTrace();
////                        }
//                    }
//
//                    @Override
//                    public void onAfter(String s, Exception e) {
//                        super.onAfter(s, e);
//                        Log.i("test","onAfter");
//                    }
//
//                    @Override
//                    public void onBefore(BaseRequest request) {
//                        super.onBefore(request);
//                        Log.i("test","onBefore");
//                    }
//
//                    @Override
//                    public void onCacheError(Call call, Exception e) {
//                        super.onCacheError(call, e);
//                        Log.i("test","onCacheError");
//                    }
//
//                    @Override
//                    public void onCacheSuccess(String s, Call call) {
//                        super.onCacheSuccess(s, call);
//                        Log.i("test","onCacheSuccess");
//                    }
//                });
//        com.olq.multiple.base.BaseBean baseBean=new com.olq.multiple.base.BaseBean("13022177558","DBDsI5DGf6s=");
//        OkgoLoader.getInstance().sendByPostUploadingJson("http://192.168.1.203:9090/sctd/operate/scuser/checklogin", baseBean, new StringCallBack() {
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                Log.i("test","onSuccess:"+s);
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//                Log.i("test","---"+e.getMessage().toString());
//            }
//        });

        OkgoLoader.getInstance().sendByPostUploadingJson(BaseUrl.USERS, users, new GsonCallBack<BaseBean>(new TypeToken<BaseBean>(){}.getType()) {
            @Override
            public void onSuccess(BaseBean usersBeanBaseBean, Call call, Response response) {
                    Log.i("test",usersBeanBaseBean.toString());

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                try {
//                    Log.i("test","----"+response.body().string());
                    BaseBean errorBean=GsonUtils.getBeanFromJson(response.body().string(),BaseBean.class);
                    Toast.makeText(getApplicationContext(),errorBean.getCode()+"--"+errorBean.getError(),Toast.LENGTH_LONG).show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }



}
