package com.wqlin.android.sample.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wqlin.android.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 汪倾林 on 2018/2/3.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.VH>{
    private List<Object> datas;

    public TextAdapter() {
        datas = new ArrayList<>();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,parent,false));
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        vh.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<Object> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(List<Object> datas) {
        int start = this.datas.size();
        this.datas.addAll(datas);
        notifyItemRangeChanged(start,this.datas.size());
    }
    class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            TextView tv = (TextView) itemView.findViewById(R.id.text);
            tv.setText("hello: " + position);
        }
    }
}
