package com.playnd.okb.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playnd.okb.R;

/**
 * Created by ByeongKwan on 2016-07-18.
 */
public class ExchangeFragment extends Fragment {
    private static String TAG = "ExchangeFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static ExchangeFragment newInstance(int page){
        Log.d(TAG, "newInstance"+page+"");
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ExchangeFragment fragment = new ExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
    }
}