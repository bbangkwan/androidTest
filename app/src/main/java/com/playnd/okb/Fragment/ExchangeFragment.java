package com.playnd.okb.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playnd.okb.R;
import com.playnd.okb.Util.Adapter.FragmentTabAdapter;

/**
 * Created by ByeongKwan on 2016-07-18.
 *
 * 2016-07-31 activity에서 탭 생성 -> fragment에서 탭 생성. 결국 얘가 main 역활을 하는거고, 두 탭으로 할 애들을 따로 만들어 줘야 한다.
 */
public class ExchangeFragment extends Fragment {
    private static String TAG = "ExchangeFragment";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);

        return view;
    }
}