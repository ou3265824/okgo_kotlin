package com.myolq.frame.Utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CharacterUtils {

    public static boolean isEmpty(CharSequence s){
        if (TextUtils.isEmpty(s)){
            return true;
        }
        return false;
    }

}
