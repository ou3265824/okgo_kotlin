package com.olq.multiple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/28.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Holder>{

    private Context mContext;

    public BookAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_book,null);

        return new Holder(view);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tvTitle.setText("A"+position);
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView tvTitle;

        public Holder(View itemView) {
            super(itemView);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
