package com.huachuang.palmtouchfinancial.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Asuka on 2/6/16
 */
public class FragmentFactory {
    private static BaseFragment[] fragments =
            {new HomepageFragment(), new SettingFragment()};

    public static Fragment getInstanceByIndex(int index) {
        if (index > fragments.length - 1  || index < 0){
            return null;
        }else{
            return fragments[index];
        }
    }
}
