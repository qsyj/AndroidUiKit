package com.wqlin.android.uikit.refresh;

/**
 * 下拉刷新各个状态触发接口
 * Created by wqlin on 17/6/15.
 */
public interface IRefreshTrigger {

    /**
     * 下拉状态
     * @param progress  下拉距离相对触发点的百分比
     */
    void onPullDownState(float progress);

    /**
     * 刷新中
     */
    void onRefreshing();

    /**
     * 释放即可触发刷新状态
     */
    void onReleaseToRefresh();

    /**
     * 完成
     */
    void onComplete();

    /**
     * 位置移动变化
     */
    void onTransYChange(float tranY);

    void init();


}
