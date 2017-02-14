package com.olq.multiple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.myolq.frame.callback.GsonCallBack
import com.myolq.frame.callback.StringCallBack
import com.myolq.frame.loader.OkgoLoader
import com.olq.multiple.base.BaseUrl
import com.olq.multiple.base.InitActivity
import com.olq.multiple.bean.BaseBean
import com.olq.multiple.bean.UsersBean
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Response
import java.net.Proxy

class LoginActivity : InitActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun onCreate() {
        bt_login.setOnClickListener {

            var account=et_account.text.toString()
            var password=et_password.text.toString()
            OkgoLoader.getInstance().sendByGet(BaseUrl.getLogin(account, password), object : StringCallBack() {
                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    Log.i("test", t);
                }

                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    Log.i("test", response!!.body().string())
                }

            });
        }
    }

}
