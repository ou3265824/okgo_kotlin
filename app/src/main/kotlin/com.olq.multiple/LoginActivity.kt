package com.olq.multiple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.myolq.frame.Utils.CharacterUtils
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

            val account=et_account.text.toString()
            val password=et_password.text.toString()
            if (CharacterUtils.isEmpty(account)){
                
                Toast.makeText(this,"账号不能为空",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (CharacterUtils.isEmpty(password)){
                Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show()

                return@setOnClickListener
            }
            OkgoLoader.getInstance().sendByGet(BaseUrl.getLogin(account, password), object : StringCallBack() {
                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    Log.i("test", t);
                }

                override fun onError(call: Call?, response: Response?, e: Exception?) {
//                    Log.i("test", response!!.body().string())
                    Toast.makeText(applicationContext, response!!.body().string(),Toast.LENGTH_LONG).show()
                }

            })
        }
    }

}
