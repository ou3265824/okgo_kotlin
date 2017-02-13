package com.olq.multiple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.myolq.frame.callback.GsonCallBack
import com.myolq.frame.callback.StringCallBack
import com.myolq.frame.loader.OkgoLoader
import com.olq.multiple.base.BaseUrl
import com.olq.multiple.bean.BaseBean
import com.olq.multiple.bean.UsersBean
import okhttp3.Call
import okhttp3.Response
import java.net.Proxy

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        OkgoLoader.getInstance().sendByGet(BaseUrl.getLogin("admin","123456"), object : StringCallBack(){
            override fun onSuccess(t: String?, call: Call?, response: Response?) {
                Log.i("test",t);
            }

            override fun onError(call: Call?, response: Response?, e: Exception?) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        });
    }
}
