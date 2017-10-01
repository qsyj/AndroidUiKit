package com.wqlin.android.sample.refresh;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wqlin.android.sample.R;
import com.wqlin.android.uikit.adapter.LoadMoreDelegate;
import com.wqlin.android.uikit.adapter.LoadMoreItem;
import com.wqlin.android.uikit.adapter.MultiTypeLoadMoreAdapter;
import com.wqlin.android.uikit.adapter.OnLoadMoreRetryListener;
import com.wqlin.android.uikit.refresh.ISwipeRefreshLayout;
import com.wqlin.android.uikit.widget.IDividerItemDecoration;

import java.util.List;
import java.util.Random;

import me.drakeet.multitype.Items;

/**
 * Created by wqlin on 2017/6/15.
 */

public class TestRefreshViewActivity extends Activity {

    ISwipeRefreshLayout refreshLayout;
    MultiTypeLoadMoreAdapter adapter;
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        IDividerItemDecoration divierDecoration = new IDividerItemDecoration(this, IDividerItemDecoration.VERTICAL);
        divierDecoration.setVerticalDividerHeight(3);
        divierDecoration.setDividerColor(Color.BLUE);
        divierDecoration.setDividerPadding(30);
        recyclerView.addItemDecoration(divierDecoration);
//        recyclerView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MultiTypeLoadMoreAdapter();
        adapter.register(TextItem.class, new TextItemViewBinder());
        recyclerView.setAdapter(adapter);
        adapter.setLoadMoreItemRetryListener(new OnLoadMoreRetryListener() {
            @Override
            public void onRetry() {
                isLoading = true;
                handler.sendEmptyMessageDelayed(2, 3000);
            }
        });

        adapter.setLoadMoreSubject(new LoadMoreDelegate.LoadMoreSubject() {
            @Override
            public boolean isLoading() {

                return isLoading;
            }

            @Override
            public void onLoadMore() {
                System.out.println(" onloacmore called ");
                isLoading = true;
                handler.sendEmptyMessageDelayed(2, 3000);
            }
        });

        /* Mock the data */
//        TextItem textItem = new TextItem("world");


        adapter.setItems(createItems());
        adapter.notifyDataSetChanged();

    }

    boolean isLoading;
    int index = 1;
    private List<?> createItems() {
        Items items = new Items();
        for (int i = index; i < index + 20; i++) {
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
                    boolean succeed = random.nextBoolean();
                    if(succeed){
                        adapter.appendItems(createItems());
                    }else{
                        adapter.setLoadMoreItemState(LoadMoreItem.STATE_FAILED);
                    }

                    if(index < 101){
                        isLoading = false;
                    }else{
                        adapter.setLoadMoreItemState(LoadMoreItem.STATE_COMPLETED);
                    }
                    break;
            }
            return false;
        }
    });

}
