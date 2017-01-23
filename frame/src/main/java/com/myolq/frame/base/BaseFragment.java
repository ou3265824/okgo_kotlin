package com.myolq.frame.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/1/23.
 */

public abstract class BaseFragment extends Fragment{

    private View view;

    public abstract int getLayout();
    public abstract void  onCreateView();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(getLayout(),container);
        }
        onCreateView();
        return view;
    }




}
