package it.sofk.slurp.ui.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Circle extends View {

    private int strokeWidth = 25;
    private Paint foregroundPaint, backgroundPaint, outOfBoundsPaint;
    private float angle;
    private boolean isOutOfBounds;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        foregroundPaint = new Paint();
        foregroundPaint.setAntiAlias(true);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setColor(Color.RED);

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setColor(Color.BLACK);

        outOfBoundsPaint = new Paint();
        outOfBoundsPaint.setAntiAlias(true);
        outOfBoundsPaint.setStyle(Paint.Style.STROKE);
        outOfBoundsPaint.setStrokeWidth(strokeWidth);
        outOfBoundsPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isOutOfBounds) {
            canvas.drawArc(strokeWidth, strokeWidth,
                    getWidth()-strokeWidth, getHeight()-strokeWidth,
                    0, 360, false, outOfBoundsPaint);
        }
        else {
            canvas.drawArc(strokeWidth, strokeWidth,
                    getWidth()-strokeWidth, getHeight()-strokeWidth,
                    0, 360, false, backgroundPaint);

            canvas.drawArc(strokeWidth, strokeWidth,
                    getWidth()-strokeWidth, getHeight()-strokeWidth,
                    0, angle, false, foregroundPaint);
        }
    }

    public void setColors(int foregroundColor, int backgroundColor) {
        foregroundPaint = new Paint();
        foregroundPaint.setAntiAlias(true);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setColor(foregroundColor);

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setColor(backgroundColor);

        this.requestLayout();
    }

    protected void setAngle(float angle) {
        this.angle = angle;
    }

    protected float getAngle() {
        return angle;
    }

    private float validateBoundary(double progress) {
        final float minProgress = 0.0f;
        if (progress < minProgress) progress = minProgress;

        return (float)progress;
    }

    private float converToAngle(float progress) {
        return 360.0f / 100.0f * progress;
    }

    public void setProgress(double progress) {
        float validatedProgress = validateBoundary(progress);
        isOutOfBounds = progress > 360.0;

        ProgressAnimation animation =  new ProgressAnimation(this.angle, validatedProgress, 200);
        this.startAnimation(animation);
    }

    protected class ProgressAnimation extends Animation {

        private final int duration;
        private final float oldAngle;
        private final float newAngle;

        public ProgressAnimation(float oldAngle, float newAngle, int duration) {
            this.oldAngle = oldAngle;
            this.newAngle = newAngle;
            this.duration = duration;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
            this.setDuration(duration);
            Circle.this.setAngle(angle);
            Circle.this.requestLayout();
        }
    }
}