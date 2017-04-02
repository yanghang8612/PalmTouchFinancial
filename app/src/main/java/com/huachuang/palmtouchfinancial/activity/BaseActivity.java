package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.huachuang.palmtouchfinancial.util.ActivityCollector;

import org.xutils.x;

/**
 * Created by Asuka on 2017/2/27.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        x.view().inject(this);
        setTitle("");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public View getContentView(){
        ViewGroup views = (ViewGroup) this.getWindow().getDecorView();
        FrameLayout content = (FrameLayout) views.findViewById(android.R.id.content);
        return content.getChildAt(0);
    }

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
