package com.truemind.swingpro.ui.main;

import android.content.DialogInterface;
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
import com.truemind.swingpro.util.CommonDialog;
import com.truemind.swingpro.util.Save;

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

    private boolean isLocal = false;

    SpinnerAdapter spinnerAdapter;

    public SettingFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.frag_setting, container, false);
        initView();
        initListener();
        return layout;
    }

    public void initView() {

        List<String> keyValues = new ArrayList<>();
        keyValues.add("1");
        keyValues.add("2");
        keyValues.add("3");
        keyValues.add("4");

        TextView normalSetting = (TextView) layout.findViewById(R.id.normalSetting);
        TextView txtLogin = (TextView) layout.findViewById(R.id.txtLogin);
        TextView txtMessage = (TextView) layout.findViewById(R.id.txtMessage);
        TextView titleSwingAir = (TextView) layout.findViewById(R.id.titleSwingAir);
        TextView testTempoSetting = (TextView) layout.findViewById(R.id.testTempoSetting);
        TextView titlePrivacy = (TextView) layout.findViewById(R.id.titlePrivacy);
        TextView txtDeleteAll = (TextView) layout.findViewById(R.id.txtDeleteAll);

        checkLogin = (CheckBox) layout.findViewById(R.id.checkLogin);
        checkMessage = (CheckBox) layout.findViewById(R.id.checkMessage);

        spinnerKey = (Spinner) layout.findViewById(R.id.spinnerTempoBtn);
        btnDelete = (Button) layout.findViewById(R.id.btnDelete);

        setFontToViewBold(getActivity(), normalSetting, txtDeleteAll, txtLogin, txtMessage, titlePrivacy, titleSwingAir,
                testTempoSetting, btnDelete);

        if (!isLocal) {
            setTxtColor(txtMessage, R.color.colorLightGrey);
            checkMessage.setChecked(true);
            checkMessage.setClickable(false);

            setTxtColor(txtLogin, R.color.colorLightGrey);
            checkLogin.setChecked(false);
            checkLogin.setClickable(false);
        }

        spinnerAdapter = new AdapterSpinner(getActivity(), keyValues);

        spinnerKey.setAdapter(spinnerAdapter);
        spinnerKey.setSelection(Constants.TEST_TEMPO_KEY - 1);

    }

    public void initListener() {

        spinnerKey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.TEST_TEMPO_KEY = (position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.setOnCloseListener(new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClose(DialogInterface dialog, int which, Object data) {
                        if (which == 1) {
                            if (Save.dataSetRecord(getActivity().getApplicationContext()).length() < 1) {
                                CommonDialog dialogDone = new CommonDialog();
                                dialogDone.showDialog(getActivity(), "삭제할 데이터가 존재하지 않습니다.");
                            } else {
                                Save.dataSetRecord(getActivity().getApplicationContext(), "");
                                Save.bestScore(getActivity().getApplicationContext(), 999999999);
                                Save.avgScore(getActivity().getApplicationContext(), 999999999);
                                Save.firstDate(getActivity().getApplicationContext(), "");
                                Save.countTime(getActivity().getApplicationContext(), 0);
                                if (Save.dataSetRecord(getActivity().getApplicationContext()).length() < 1) {
                                    CommonDialog dialogDone = new CommonDialog();
                                    dialogDone.showDialog(getActivity(), "데이터가 전부 삭제되었습니다.");
                                }
                            }
                        }
                    }
                });
                dialog.showDialog(getActivity(), "데이터를 전부 삭제하시겠습니까? 삭제된 데이터는 복구되지 않습니다.");
            }
        });

    }


}
