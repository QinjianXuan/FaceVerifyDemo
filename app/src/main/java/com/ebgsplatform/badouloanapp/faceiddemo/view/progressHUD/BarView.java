/*--------------------------------------------------
 * Copyright (C) 2016 The Android SanDao Project
 *               http://www.sandaogroup.com
 * 创建时间：2016/6/24
 * 内容说明：
 *
 * 变更时间：
 * 变更说明：
 * -------------------------------------------------- */
package com.ebgsplatform.badouloanapp.faceiddemo.view.progressHUD;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ebgsplatform.badouloanapp.utils.DensityUtil;


class BarView extends View implements Determinate {

    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private RectF mBound;
    private RectF mInBound;
    private int mMax = 100;
    private int mProgress = 0;
    private float mBoundGap;

    public BarView(Context context) {
        super(context);
        init();
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(DensityUtil.dp2px(2));
        mOuterPaint.setColor(Color.WHITE);

        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setColor(Color.WHITE);

        mBoundGap = DensityUtil.dp2px(5);
        mInBound = new RectF(mBoundGap, mBoundGap,
                (getWidth() - mBoundGap) * mProgress / mMax, getHeight() - mBoundGap);

        mBound = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int padding = DensityUtil.dp2px(2);
        mBound.set(padding, padding, w - padding, h - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBound, mBound.height()/2, mBound.height()/2, mOuterPaint);
        canvas.drawRoundRect(mInBound, mInBound.height()/2, mInBound.height()/2, mInnerPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthDimension = DensityUtil.dp2px(100);
        int heightDimension = DensityUtil.dp2px(20);
        setMeasuredDimension(widthDimension, heightDimension);
    }

    @Override
    public void setMax(int max) {
        this.mMax = max;
    }

    @Override
    public void setProgress(int progress) {
        this.mProgress = progress;
        mInBound.set(mBoundGap, mBoundGap,
                (getWidth() - mBoundGap) * mProgress / mMax, getHeight() - mBoundGap);
        invalidate();
    }
}
