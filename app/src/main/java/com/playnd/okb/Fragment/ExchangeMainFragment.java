package com.playnd.okb.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playnd.okb.R;
import com.playnd.okb.Util.Adapter.FragmentTabAdapter;

/**
 * Created by kgrid-dev-bbk on 2016-08-01.
 */
public class ExchangeMainFragment extends Fragment{
    private static String TAG = "ExchangeMainFragment";

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    FragmentTabAdapter tabAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange_main, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        tabAdapter = new FragmentTabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(new ExchangeFragment(), "환율 계산");
        tabAdapter.addFragment(new ExchangeHistoryFragment(), "가계부");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.addTab(tabLayout.newTab().setText("환율 계산"));
        //tabLayout.addTab(tabLayout.newTab().setText("가계부"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Log.d(TAG, tab.getPosition()+"");
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
