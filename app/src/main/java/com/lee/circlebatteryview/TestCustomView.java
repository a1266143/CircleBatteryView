package com.lee.circlebatteryview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 测试自定义View
 * created by xiaojun at 2020/5/11
 */
public class TestCustomView extends View {

    public TestCustomView(Context context) {
        this(context,null);
    }

    public TestCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Log.e("xiaojun","init");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("xiaojun","onSizeChanged");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("xiaojun","onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("xiaojun","onDraw");
    }
}
