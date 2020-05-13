package com.lee.circlebatteryview;

import android.content.Context;

class Utils {

    static int dp2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
