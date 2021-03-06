package com.wqlin.android.sample.strongtablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wqlin.android.sample.R;

/**
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2018/1/19 9:51
 */
public class TabFragment extends Fragment {
    public static TabFragment newInstance(String text) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String text = bundle.getString("text");
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(text);
        return view;
    }
}
