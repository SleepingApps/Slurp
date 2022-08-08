package it.sofk.slurp.ui.controls;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import it.sofk.slurp.R;

public class Ellipse extends View {

    private Paint paint;

    public Ellipse(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    public void setPaint(int paint) {
        this.paint.setColor(paint);
        this.requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        int radius = (int)(getWidth()/2.7);
        radius = getWidth()/2;
        canvas.drawCircle(radius, getHeight()-radius, radius, paint);
    }
}