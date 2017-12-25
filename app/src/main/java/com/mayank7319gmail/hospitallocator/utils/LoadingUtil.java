package com.mayank7319gmail.hospitallocator.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mayank Gupta on 25-12-2017.
 */

public class LoadingUtil {
    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
}
