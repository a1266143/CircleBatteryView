package com.lee.circlebatteryview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 圆圈电池View
 * created by xiaojun
 */
public class CircleBatteryView extends View {

    private Paint mPaintBack = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintFore = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mWidth, mHeight, mHalfOfWidth, mHalfOfHeight;
    private float mDistance_center_baseline;
    private int mSweepAngle;//电池电量扫过的角度

    //--------------------自定义属性-----------------------
    //底层圈color
    int mBackColor;
    //上层圈color
    int mForeColor;
    //底层圈线宽
    float mBackStrokeWidth;
    //上层圈线宽
    float mForeStrokeWidth;
    //是否显示电池电量文本
    boolean mShowBatteryText;
    //电量文本大小
    float mBatteryTextSize;
    //电量文本颜色
    int mBatteryTextColor;
    //电池电量
    int mBatteryLevel;
    //--------------------------------------------------------

    public CircleBatteryView(Context context) {
        this(context, null);
    }

    public CircleBatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getCustomAttr(context, attrs);

        mPaintBack.setColor(mBackColor);
        mPaintBack.setStyle(Paint.Style.STROKE);
        mPaintBack.setStrokeWidth(mBackStrokeWidth);

        mPaintFore.setColor(mForeColor);
        mPaintFore.setStyle(Paint.Style.STROKE);
        mPaintFore.setStrokeWidth(mForeStrokeWidth);

        mPaintText.setColor(mBatteryTextColor);
        mPaintText.setTextSize(mBatteryTextSize);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setFakeBoldText(true);

        //计算初始电池电量百分比所扫描过的角度
        calcForeSweepAngle();

        Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
        //文字Y轴居中应该加上的距离
        mDistance_center_baseline = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    }

    /**
     * 获取自定义属性
     */
    private void getCustomAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleBatteryView);
        mBackColor = typedArray.getColor(R.styleable.CircleBatteryView__backColor, Color.parseColor("#777777"));
        mForeColor = typedArray.getColor(R.styleable.CircleBatteryView__foreColor, Color.GREEN);
        mBackStrokeWidth = typedArray.getDimension(R.styleable.CircleBatteryView__backStrokeWidth, (float) Utils.dp2px(context, 3));
        mForeStrokeWidth = typedArray.getDimension(R.styleable.CircleBatteryView__foreStrokeWidth, Utils.dp2px(context, 3));
        mShowBatteryText = typedArray.getBoolean(R.styleable.CircleBatteryView__showBatteryText, true);
        mBatteryTextSize = typedArray.getDimension(R.styleable.CircleBatteryView__batteryTextSize, Utils.dp2px(context, 7));
        mBatteryTextColor = typedArray.getColor(R.styleable.CircleBatteryView__batteryTextColor, Color.parseColor("#777777"));
        mBatteryLevel = typedArray.getInteger(R.styleable.CircleBatteryView__batteryLevel, 0);
        typedArray.recycle();
        /*Log.e("xiaojun", "获取到的参数," +
                "mBackStrokeWidth=" + mBackStrokeWidth +
                ",mForeStrokeWidth=" + mForeStrokeWidth +
                ",mShowBatteryText=" + mShowBatteryText +
                ",mBatteryTextSize=" + mBatteryTextSize +
                ",mBattery" + mBatteryLevel);*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        this.mHalfOfWidth = mWidth / 2;
        this.mHalfOfHeight = mHeight / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getSize(widthMeasureSpec);
        mHeight = getSize(heightMeasureSpec);
        //如果输入的宽度和高度不一致，将改成宽高一样(选择尺寸大的那个)
        if (mWidth != mHeight) {
            if (mWidth > mHeight)
                mHeight = mWidth;
            else
                mWidth = mHeight;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    //封装
    private int getSize(int measureSpec) {
        int DEFAULT_SIZE = Utils.dp2px(getContext(), 24);
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result;
        //fixable size and MATCH_PARENT
        if (specMode == MeasureSpec.EXACTLY)
            result = specSize;
            //wrap_content : 使用我们给定的size:24dp
        else if (specMode == MeasureSpec.AT_MOST)
            result = DEFAULT_SIZE;
            //UNSPECIAL SIZE
        else
            result = DEFAULT_SIZE;
        return result;
    }

    /**
     * 设置电池电量
     *
     * @param batteryLevel
     */
    public void setBattery(int batteryLevel) {
        if (batteryLevel > 100 || batteryLevel < 0)
            throw new RuntimeException("电池电量必须在0~100");
        mBatteryLevel = batteryLevel;
        calcForeSweepAngle();
        invalidate();
    }

    /**
     * 计算上层电量的显示百分比
     */
    private void calcForeSweepAngle() {
        float rato = mBatteryLevel * 1.f / 100;
        mSweepAngle = (int) (rato * 360);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mHalfOfWidth, mHalfOfHeight);
        //drawback
        canvas.drawArc(mBackStrokeWidth / 2 - mHalfOfWidth, -mHalfOfHeight + mBackStrokeWidth / 2, mHalfOfWidth - mBackStrokeWidth / 2, mHalfOfHeight - mBackStrokeWidth / 2, mSweepAngle-90, 360-mSweepAngle, false, mPaintBack);
        //drawForeground
        if (mBatteryLevel < 30 && mBatteryLevel > 15)
            mPaintFore.setColor(Color.YELLOW);
        else if (mBatteryLevel >= 0 && mBatteryLevel <= 15)
            mPaintFore.setColor(Color.RED);
        else
            mPaintFore.setColor(mForeColor);
        canvas.drawArc(mForeStrokeWidth / 2 - mHalfOfWidth, -mHalfOfHeight + mForeStrokeWidth / 2, mHalfOfWidth - mForeStrokeWidth / 2, mHalfOfHeight - mForeStrokeWidth / 2, -90, mSweepAngle, false, mPaintFore);
        //drawText
        canvas.drawText(mBatteryLevel + "", 0, mDistance_center_baseline, mPaintText);
        canvas.restore();
    }
}
