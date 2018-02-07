package com.wqlin.android.sample.strongtablayout;

import android.support.design.widget.StrongTabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wqlin.android.sample.R;

import java.util.ArrayList;
import java.util.List;

public class StrongTabLaoutActivity extends AppCompatActivity {
    private StrongTabLayout mTablayout;
    private ViewPager mViewPager;
    private TabAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strong_tab_laout);
        initView();
    }

    private void initView() {
        mTablayout = (StrongTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new TabAdapter(getSupportFragmentManager());
        mAdapter.setItems(getData());
        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.setAnimIndicatorWhenClickTab(true);
    }

    private List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("jdkbas");
        list.add("DG");
        list.add("FHdg");
        list.add("JGdfhFH");
        list.add("发你赶快来");
        list.add("放开你");
        list.add("KNFLKF");
        list.add("打开估计快了");
        list.add("的个数");
        list.add("发电量你看了");
        list.add("开门打开");
        list.add("wba");
        list.add("lkddmm");
        list.add("付款管理科");
        return list;
    }
}
