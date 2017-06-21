package com.truemind.swingpro.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.truemind.swingpro.base.BaseFragment;
import com.truemind.swingpro.Constants;
import com.truemind.swingpro.R;
import com.truemind.swingpro.util.AdapterSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 현석 on 2017-06-15.
 */

public class SettingFragment extends BaseFragment {

    CheckBox checkLogin;
    CheckBox checkMessage;

    Spinner spinnerKey;
    Button btnDelete;

    LinearLayout layout;

    SpinnerAdapter spinnerAdapter;
    public SettingFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.frag_setting, container, false);
        initView();
        initListener();
        return layout;
    }

    public void initView(){

        List<String> keyValues = new ArrayList<>();
        keyValues.add("1");
        keyValues.add("2");
        keyValues.add("3");
        keyValues.add("4");
        keyValues.add("5");

        TextView normalSetting = (TextView)layout.findViewById(R.id.normalSetting);
        TextView txtLogin = (TextView)layout.findViewById(R.id.txtLogin);
        TextView txtMessage = (TextView)layout.findViewById(R.id.txtMessage);
        TextView titleSwingAir = (TextView)layout.findViewById(R.id.titleSwingAir);
        TextView testTempoSetting = (TextView)layout.findViewById(R.id.testTempoSetting);
        TextView titlePrivacy = (TextView)layout.findViewById(R.id.titlePrivacy);
        TextView txtDeleteAll = (TextView)layout.findViewById(R.id.txtDeleteAll);

        checkLogin = (CheckBox)layout.findViewById(R.id.checkLogin);
        checkMessage = (CheckBox)layout.findViewById(R.id.checkMessage);

        spinnerKey = (Spinner)layout.findViewById(R.id.spinnerTempoBtn);
        btnDelete = (Button)layout.findViewById(R.id.btnDelete);

        setFontToViewBold(getActivity(), normalSetting, txtDeleteAll, txtLogin, txtMessage, titlePrivacy, titleSwingAir,
                testTempoSetting, btnDelete);


        spinnerAdapter = new AdapterSpinner(getActivity(), keyValues);

        spinnerKey.setAdapter(spinnerAdapter);
        spinnerKey.setSelection(Constants.TEST_TEMPO_KEY-1);

    }

    public void initListener(){

        spinnerKey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.TEST_TEMPO_KEY = (position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
