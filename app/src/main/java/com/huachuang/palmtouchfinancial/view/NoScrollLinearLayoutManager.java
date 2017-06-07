package com.huachuang.palmtouchfinancial.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Asuka on 2017/6/7.
 */

public class NoScrollLinearLayoutManager extends LinearLayoutManager {

    public NoScrollLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return false;
    }
}
