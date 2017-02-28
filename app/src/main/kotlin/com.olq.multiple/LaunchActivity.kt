package com.olq.multiple

import android.content.Intent
import android.os.Handler
import com.olq.multiple.base.BaseActivity

/**
 * Created by Administrator on 2017/2/27.
 */
class LaunchActivity :BaseActivity(){


    override fun getLayout(): Int {
        return R.layout.activity_launch
    }

    override fun onCreate() {
        var handle=Handler();
        var run= Runnable { startActivity(Intent(application,HomeActivity::class.java)) }
//        handle.postDelayed(run,3000)
        handle.post(run)
    }

}