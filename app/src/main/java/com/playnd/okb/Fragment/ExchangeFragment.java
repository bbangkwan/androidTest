package com.playnd.okb.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.playnd.okb.R;
import com.playnd.okb.Util.Adapter.Preference.SharedPreferenceUtil;

/**
 * Created by ByeongKwan on 2016-07-18.
 *
 * 2016-07-31 activity에서 탭 생성 -> fragment에서 탭 생성. 결국 얘가 main 역활을 하는거고, 두 탭으로 할 애들을 따로 만들어 줘야 한다.
 */
public class ExchangeFragment extends Fragment {
    private static String TAG = ExchangeFragment.class.getName();

    View view;

    RadioButton HtoK_option;
    RadioButton KtoH_option;

    EditText input_fee;
    Button change_currency_btn;
    TextView change_currency_result;

    EditText now_currency_input;
    Button store_now_currency;
    TextView save_exchange_rate;

    SharedPreferenceUtil pref;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pref = new SharedPreferenceUtil(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exchange, container, false);

        HtoK_option = (RadioButton) view.findViewById(R.id.hkdtokrw);
        KtoH_option = (RadioButton) view.findViewById(R.id.krwtohkd);

        HtoK_option.setChecked(true);

        input_fee = (EditText) view.findViewById(R.id.input_fee);
        change_currency_btn = (Button) view.findViewById(R.id.change_currency);
        change_currency_result = (TextView) view.findViewById(R.id.change_currency_result);

        now_currency_input = (EditText) view.findViewById(R.id.now_currrency_input);
        store_now_currency = (Button) view.findViewById(R.id.store_now_currency);
        save_exchange_rate = (TextView) view.findViewById(R.id.save_exchange_rate);

        if(pref.getFloatVal("nowCurrency") > 0){
            save_exchange_rate.setText("현재 저장된 홍콩 환율 : 1 홍콩달러 당 "+pref.getFloatVal("nowCurrency")+" 원 입니다.");
        }else{
            Toast.makeText(getActivity(), "먼저 하단에 환율 정보를 입력하세요.", Toast.LENGTH_LONG).show();
        }

        /**
         * 환율정보구하기 버튼 클릭 시
         */
        change_currency_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pref.getFloatVal("nowCurrency") > 0) {
                    if(Float.valueOf(input_fee.getText().toString()) > 0){
                        String currency_convert_result = currencyConvert(Float.valueOf(input_fee.getText().toString()));

                        change_currency_result.setText("계산 결과 : " + currency_convert_result);
                    }else{
                        Toast.makeText(getActivity(), "값 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "먼저 하단에 환율 정보를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * 홍콩달러 환율 정보 저장.
         */
        store_now_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now_currency_input.getText().toString().isEmpty()){
                    Toast.makeText (getActivity(), "기준되는 값을 입력하셔야 합니다.", Toast.LENGTH_LONG).show();
                }else{
                    float now_currency = Float.valueOf(now_currency_input.getText().toString());

                    pref.putFloatVal("nowCurrency", now_currency);

                    Toast.makeText (getActivity(), "입력한 "+now_currency+" 이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public String currencyConvert(float input_fee){
        /**
         * 환율 변환하기.
         */
        String result;
        float tmp_fee;
        Log.d(TAG, input_fee+"");
        if(HtoK_option.isChecked()){
            Log.d(TAG, "홍콩달러를 원화로");

            tmp_fee = input_fee * pref.getFloatVal("nowCurrency");

            result = tmp_fee+"원";
        }else{
            Log.d(TAG, "원화를 홍콩달러로");
            result = "0홍콩달러";
        }

        return result;
    }
}