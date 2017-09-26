/*--------------------------------------------------
 * Copyright (C) 2016 The Android SanDao Project
 *               http://www.sandaogroup.com
 * 创建时间：2016/5/6
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

import com.ebgsplatform.badouloanapp.BDApplication;
import com.ebgsplatform.badouloanapp.R;
import com.ebgsplatform.badouloanapp.utils.DensityUtil;


class AnnularView extends View implements Determinate {

    private Paint mWhitePaint;
    private Paint mGreyPaint;
    private RectF mBound;
    private int mMax = 100;
    private int mProgress = 0;

    public AnnularView(Context context) {
        super(context);
        init(context);
    }

    public AnnularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnnularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWhitePaint.setStyle(Paint.Style.STROKE);
        mWhitePaint.setStrokeWidth(DensityUtil.dp2px(3));
        mWhitePaint.setColor(Color.WHITE);

        mGreyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGreyPaint.setStyle(Paint.Style.STROKE);
        mGreyPaint.setStrokeWidth(DensityUtil.dp2px(3));
        mGreyPaint.setColor(BDApplication.getResColor(R.color.kprogresshud_grey_color));

        mBound = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int padding = DensityUtil.dp2px(4);
        mBound.set(padding, padding, w - padding, h - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mAngle = mProgress * 360f / mMax;
        canvas.drawArc(mBound, 270, mAngle, false, mWhitePaint);
        canvas.drawArc(mBound, 270 + mAngle, 360 - mAngle, false, mGreyPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dimension = DensityUtil.dp2px(40);
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    public void setMax(int max) {
        this.mMax = max;
    }

    @Override
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }
}
