package com.wqlin.android.sample.refresh;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.WRecyclerView;
import android.view.View;

import com.wqlin.android.sample.R;
import com.wqlin.android.uikit.refresh.ISwipeRefreshLayout;
import com.wqlin.android.uikit.widget.BaseItemDecoration;
import com.wqlin.android.uikit.widget.ItemDecorationConfig;
import com.wqlin.widget.irecyclerview.OnLoadMoreListener;

import java.util.List;
import java.util.Random;

import me.drakeet.multitype.Items;

/**
 * Created by wqlin on 2017/6/15.
 */

public class TestRefreshViewActivity extends Activity {

    ISwipeRefreshLayout refreshLayout;
    TextAdapter adapter;
    private WRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        refreshLayout = (ISwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new ISwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do refresh ...
                handler.sendEmptyMessageDelayed(1, 2000);
            }
        });

//        refreshLayout.setRefreshHeaderView(new AvLoadingRefreshView(this));
        refreshLayout.setRefreshHeaderView(new MaterialRefreshView(this));
//
//        refreshLayout.setRefreshHeaderView(new MedlinkerRefreshHeaderView(this));
//        refreshLayout.setEnabled(false);
        refreshLayout.setRefreshEnabled(true);
        handler.sendEmptyMessageDelayed(0, 2000);

        mRecyclerView = (WRecyclerView) findViewById(R.id.recyclerView);
        /*IDividerItemDecoration divierDecoration = new IDividerItemDecoration(this, IDividerItemDecoration.VERTICAL);
        divierDecoration.setVerticalDividerHeight(3);
        divierDecoration.setDividerColor(Color.BLUE);
        divierDecoration.setDividerPadding(30);
        mRecyclerView.addItemDecoration(divierDecoration);*/
        mRecyclerView.addItemDecoration(new BaseItemDecoration() {
            @Override
            public ItemDecorationConfig getItemDecorationConfig(View view, RecyclerView rv) {
                if (getItemViewType(view,rv)==0) return new ItemDecorationConfig().setBottom(20, Color.BLUE);
                return null;
            }
        });
//        recyclerView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLoadMoreFooterView(new LoadMoreFooterLayout(this));
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.sendEmptyMessageDelayed(2, 3000);
            }
        });

        adapter = new TextAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setData(createItems());
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerView.destory();
    }

    boolean isLoading;
    int index = 1;
    private List<Object> createItems() {
        Items items = new Items();
        for (int i = index; i < index + 15; i++) {
            TextItem textItem = new TextItem("world no."+i);
            items.add(textItem);
        }
        index += items.size();
        return items;
    }

    Random random = new Random();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:// refreshing ture
                    refreshLayout.setRefreshing(true);
                    break;

                case 1:// refreshing false
                    refreshLayout.setRefreshing(false);
                    break;

                case 2://加载完成
                    adapter.addData(createItems());
                    /*boolean succeed = random.nextBoolean();
                    if(succeed){
                        adapter.addData(createItems());
                    }else{
                    }*/
                    break;
            }
            return false;
        }
    });

}
