package com.truemind.swingpro.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-15.
 */

public class BaseFragment extends Fragment {

    /**
     * Typeface로 폰트 적용
     * (배민 주아체로 적용)
     *
     * @param views 적용을 원하는 모든 TextView
     *              ,로 구분하여 무제한 개수의 동시 적용 가능
     * */
    public void setFontToViewBold(Context context, TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMJUA_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    public void initFooter(Context context, LinearLayout layout){
        TextView txtFooter = (TextView)layout.findViewById(R.id.txtFooter);
        TextView txtFooter2 = (TextView)layout.findViewById(R.id.txtFooter2);
        setFontToViewBold2(context, txtFooter, txtFooter2);
    }


    public void setFontToViewBold2(Context context, TextView... views) {
        Typeface NanumNormal = Typeface.createFromAsset(context.getAssets(), "BMDOHYEON_ttf.ttf");

        for (TextView view : views)
            view.setTypeface(NanumNormal);
    }

    public void setColor(View v, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setBackgroundColor(getResources().getColor(color, null));
        } else {
            v.setBackgroundColor(getResources().getColor(color));
        }
    }

    public void setTxtColor(TextView v, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setTextColor(getResources().getColor(color, null));
        } else {
            v.setTextColor(getResources().getColor(color));
        }
    }

    public int getMyPreferences(int seq){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        return Integer.parseInt(pref.getString(Integer.toString(seq), "0"));
    }

    public int getMyPreferencesSize(){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        return Integer.parseInt(pref.getString("Max", "0"));
    }

    public void saveMyPreferences(int seq, int value, int max){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Integer.toString(seq), Integer.toString(value));
        editor.putString("Max", Integer.toString(max));
        editor.apply();
    }

    public void removeMyPreferences(int seq){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Integer.toString(seq));
        editor.apply();
    }

    public void removeMyAllPreferences(){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }


}
