package com.truemind.swingpro.util;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.truemind.swingpro.R;

/**
 * Created by 현석 on 2017-06-19.
 */

public class CustomAdapter extends PagerAdapter {

    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;

        view = inflater.inflate(R.layout.imgpager_child, null);
        ImageView img = (ImageView) view.findViewById(R.id.imgPager_child);

        switch (position) {
            case 0:
                img.setImageResource(R.drawable.img_demo);
                img.setOnClickListener(mPagerListener);
                break;
            case 1:
                img.setImageResource(R.drawable.img_demo2);
                img.setOnClickListener(mPagerListener);
                break;
            case 2:
                img.setImageResource(R.drawable.img_demo3);
                img.setOnClickListener(mPagerListener);
                break;
        }


        container.addView(view);
        return view;

    }

    private View.OnClickListener mPagerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v == obj;

    }


}
