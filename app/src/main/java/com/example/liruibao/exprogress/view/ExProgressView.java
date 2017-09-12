package com.example.liruibao.exprogress.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by liruibao on 2017/8/23.
 */

public class ExProgressView extends FrameLayout {
    private static float mCurrentValue = 0;
    private static long mSecond = 5;
    private static Paint mPaint = null;
    private static float mRx = 10;
    private static float mRy = 10;
    private static Path.Direction mDir = Path.Direction.CW;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            invalidate();
            return true;
        }
    });

    public ExProgressView(@NonNull Context context) {
        super(context);
    }

    public ExProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExProgressView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExProgressView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public static void init(Paint paint,float rx,float ry,Path.Direction direction,long second){
        mPaint = paint;
        mRx = rx;
        mRy = ry;
        mDir = direction;
        mSecond = second;
    }

    @Override
    public void draw(Canvas canvas) {
        drawAnim(canvas);

        super.draw(canvas);
    }

    public void drawAnim(Canvas canvas) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
        }
        int childNum = getChildCount();
        for (int i = 0; i < childNum; i++) {
            View child = getChildAt(i);
            RectF f = new RectF(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            mCurrentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
            if (mCurrentValue >= 1) {
                mCurrentValue = 0;
//                return;
            }
            Path path = new Path();
            path.addRoundRect(f, mRx, mRy, mDir);
            PathMeasure measure1 = new PathMeasure(path, false);
            Path dst = new Path();
            measure1.getSegment(measure1.getLength() * mCurrentValue, measure1.getLength(), dst, true);
            canvas.drawPath(dst, mPaint);
        }
        handler.sendEmptyMessageDelayed(0, mSecond*5);


    }
}
