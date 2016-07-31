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

import com.playnd.okb.R;
import com.playnd.okb.Util.Adapter.FragmentTabAdapter;

/**
 * Created by ByeongKwan on 2016-07-18.
 *
 * 2016-07-31 activity에서 탭 생성 -> fragment에서 탭 생성. 결국 얘가 main 역활을 하는거고, 두 탭으로 할 애들을 따로 만들어 줘야 한다.
 */
public class ExchangeFragment extends Fragment {
    private static String TAG = "ExchangeFragment";
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    public static int int_items = 3 ;

    FragmentTabAdapter tabAdapter = null;
    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "ExchangeFragment onCreate");
        super.onCreate(savedInstanceState);
        if(getArguments() == null){
            Log.d(TAG, "getArguments is empty");
            mPage = 0;
        }else {
            Log.d(TAG, "getArguments is not empty");
            mPage = getArguments().getInt(ARG_PAGE, 0);
            Log.d(TAG, mPage+"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView : "+mPage+"");
        View view = inflater.inflate(R.layout.fragment_exchange_main, container, false);
        TextView textView = (TextView) view;
        textView.setText("Fragment #" + mPage);
        Log.d(TAG, textView.getText()+"");
        return view;
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange_main, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        /*tabAdapter = new FragmentTabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(new ExchangeFragment(), "환율 계산");
        //tabAdapter.addFragment(new ExchangeFragment(), "page2");
        tabAdapter.addFragment(new ExchangeHistoryFragment(), "가계부");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(tabAdapter);

        // Give the TabLayout the ViewPager
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);*/

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new ExchangeHistoryFragment();
                case 1 : return new SocialFragment();
                case 2 : return new UpdatesFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Primary";
                case 1 :
                    return "Social";
                case 2 :
                    return "Updates";
            }
            return null;
        }
    }
}