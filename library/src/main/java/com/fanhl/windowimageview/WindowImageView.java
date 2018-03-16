package com.fanhl.windowimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * desc: 橱窗ImageView
 * date: 2018/3/16
 *
 * @author fanhl
 */
public class WindowImageView extends android.support.v7.widget.AppCompatImageView {
    public WindowImageView(Context context) {
        super(context);
    }

    public WindowImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WindowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
