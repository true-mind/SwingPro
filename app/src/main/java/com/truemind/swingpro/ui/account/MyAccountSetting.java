package com.truemind.swingpro.ui.account;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.truemind.swingpro.R;
import com.truemind.swingpro.base.BaseActivity;
import com.truemind.swingpro.util.CommonDialog;

public class MyAccountSetting extends BaseActivity {

    private Button btnLogout;
    private Button btnDropout;
    private Button btnChangePwd;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView tv_id;
    private TextView tv_name;
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_setting);
        initView();
        initSlideMenu("내 계정 설정");
        initListener();
    }

    private void initListener() {
        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(MyAccountSetting.this, "로컬 버전에서는 사용할 수 없습니다.");
            }
        });

        btnDropout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog();
                dialog.showDialog(MyAccountSetting.this, "로컬 버전에서는 사용할 수 없습니다.");
            }
        });
    }

    private void initView() {
        btnLogout = (Button) findViewById(R.id.account_btn_logout);
        btnDropout = (Button) findViewById(R.id.account_btn_dropout);
        btnChangePwd = (Button) findViewById(R.id.account_btn_change_pwd);
        txt1 = (TextView) findViewById(R.id.txt22);
        txt2 = (TextView) findViewById(R.id.txt23);
        txt3 = (TextView) findViewById(R.id.txt24);
        tv_id = (TextView) findViewById(R.id.account_tv_id);
        tv_name = (TextView) findViewById(R.id.account_tv_name);
        tv_phone = (TextView) findViewById(R.id.account_tv_phone);

        setFontToViewBold(txt1, txt2, txt3, tv_id, tv_name, tv_phone, btnChangePwd, btnDropout, btnLogout);

        tv_phone.setText(getPhoneNumber());
    }

    private void readPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_SMS, 1);
            } else {
                requestPermission(Manifest.permission.READ_SMS, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tv_phone.setText(getPhoneNumber());
                } else {
                    Toast.makeText(MyAccountSetting.this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }
    private String getPhoneNumber() {
        readPermission();
        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber ="";

        try {
            if (telephony.getLine1Number() != null) {
                phoneNumber = telephony.getLine1Number();
            }
            else {
                if (telephony.getSimSerialNumber() != null) {
                    phoneNumber = telephony.getSimSerialNumber();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

}
