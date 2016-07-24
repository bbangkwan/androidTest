package com.playnd.okb.Util.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.playnd.okb.Fragment.ExchangeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ByeongKwan on 2016-07-23.
 */
public class FragmentTabAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "환율계산", "히스토리" };

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    private Context context;

    public FragmentTabAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title)
    {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position)
    {
        //return mFragments.get(position);
        return ExchangeFragment.newInstance(position+1);
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitles.get(position);
    }

    /*final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "환율계산", "히스토리" };

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    private Context context;

    public FragmentTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }



    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("getItem", position+"");
        return ExchangeFragment.newInstance(position+1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }*/
}
