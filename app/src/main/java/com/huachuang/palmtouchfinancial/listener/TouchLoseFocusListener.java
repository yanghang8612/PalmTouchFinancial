package com.huachuang.palmtouchfinancial.listener;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Asuka on 2017/3/22.
 */

public class TouchLoseFocusListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.getRootView().setFocusable(true);
        v.getRootView().setFocusableInTouchMode(true);
        v.getRootView().requestFocus();
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        return false;
    }
}
