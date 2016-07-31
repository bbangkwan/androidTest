package com.playnd.okb.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.playnd.okb.R;

/**
 * Created by kgrid-dev-bbk on 2016-07-29.
 */
public class ExchangeHistoryFragment extends Fragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange_history_main, container, false);
        TextView textView = (TextView) view;
        textView.setText("ExchangeHistoryFragment");
        return view;
    }
}
