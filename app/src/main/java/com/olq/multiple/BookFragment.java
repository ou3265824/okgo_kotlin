package com.olq.multiple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olq.multiple.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/21.
 */

public class BookFragment extends BaseFragment {


    @BindView(R.id.rv_recycler)
    RecyclerView rvRecycler;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;


    @Override
    public int getLayout() {
        return R.layout.fragment_book;
    }

    @Override
    public void onCreateView() {
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rvRecycler.setLayoutManager(manager);
        BookAdapter bookAdapter=new BookAdapter(getActivity());
        rvRecycler.setAdapter(bookAdapter);
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


}
