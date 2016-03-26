package com.example.flame.flame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.jar.Attributes;

/**
 * Created by GEEK on 16/3/26.
 */
public class MyView extends View {

    float preX;
    float preY;
    private Path path;
    public Paint paint = null;
    final int VIEW_WIDTH = 480;
    final int VIEW_HEIGHT = 800;

    Bitmap cacheBitmap = null;
    Canvas cacheCanvas = null;



    public MyView(Context context) {
        super(context);

        cacheBitmap = Bitmap.createBitmap(VIEW_WIDTH, VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        path = new Path();

        cacheCanvas.setBitmap(cacheBitmap);

        paint = new Paint (Paint.DITHER_FLAG);
        paint.setColor(Color.RED);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        paint.setAntiAlias(true);
        paint.setDither(true);




    }
    public MyView(Context context, AttributeSet set) {
        super(context, set);


    }
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(preX, preY, x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint bmpPaint = new Paint();
        canvas.drawBitmap(cacheBitmap, 0, 0, bmpPaint);
        canvas.drawPath(path, paint);

    }
}
